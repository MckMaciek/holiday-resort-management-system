package holiday_resort.management_system.com.holiday_resort;

import holiday_resort.management_system.com.holiday_resort.Context.CustomContext;
import holiday_resort.management_system.com.holiday_resort.Entities.Accommodation;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Repositories.AccommodationRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import holiday_resort.management_system.com.holiday_resort.Services.LoginDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class CustomContextTest {

    private final CustomContext<Accommodation, AccommodationRepository> customContext;
    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;
    private final LoginDetailsService loginDetailsService;

    private final static String FIRST_NAME = "TEST";
    private final static String LAST_NAME = "TEST";
    private final static String EMAIL = "TEST@GMAIL.COM";

    private final static String DATE_VALUE = "1986-04-08 12:30";
    private static LocalDateTime TIME_NOW;

    @Autowired
    public CustomContextTest(AccommodationRepository accommodationRepository,
                             CustomContext<Accommodation, AccommodationRepository> customContext,
                             UserRepository userRepository,
                             LoginDetailsService loginDetailsService
                             ){
        this.accommodationRepository = accommodationRepository;
        this.userRepository = userRepository;
        this.customContext = customContext;
        this.loginDetailsService = loginDetailsService;
    }

    @Test
    public void testCasting(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        TIME_NOW = LocalDateTime.parse(DATE_VALUE, formatter);

        User user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .creationDate(TIME_NOW)
                .modificationDate(TIME_NOW)
                .build();

        LoginDetails loginDetails = LoginDetails.builder()
                .user(user)
                .username(FIRST_NAME)
                .password(FIRST_NAME)
                .build();

        user.setLoginDetails(loginDetails);

        loginDetailsService.saveUserAndUserLoginObject(loginDetails);

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(1);

        User userAddedOpt = userList.get(0);

        Optional<User> userOpt = userRepository.findById(userAddedOpt.getId());

        Accommodation accommodation = Accommodation.builder()
                .user(userOpt.get())
                .reservation(null)
                .resortObject(null)
                .build();

        accommodationRepository.save(accommodation);

        List<Accommodation> accommodationList = accommodationRepository.findAll();

        Accommodation accommodation1 = accommodationList.get(0);

        Pair<LoginDetails, Accommodation> accommodationPair =
                customContext.getAssociatedUser(accommodationRepository, accommodation1.getId());

        LoginDetails userLoginDetails = accommodationPair.getFirst();
        Accommodation accommodationForId = accommodationPair.getSecond();

        assertThat(userLoginDetails == userOpt.get().getLoginDetails());
        assertThat(accommodationForId == accommodation1);
    }

}
