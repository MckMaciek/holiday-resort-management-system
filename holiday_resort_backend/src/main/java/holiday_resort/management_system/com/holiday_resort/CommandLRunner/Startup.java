package holiday_resort.management_system.com.holiday_resort.CommandLRunner;


import holiday_resort.management_system.com.holiday_resort.Context.CustomContext;
import holiday_resort.management_system.com.holiday_resort.Controllers.AuthController;
import holiday_resort.management_system.com.holiday_resort.Entities.Accommodation;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Repositories.AccommodationRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginDetailsRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.JwtResponse;
import holiday_resort.management_system.com.holiday_resort.Requests.LoginRequest;
import holiday_resort.management_system.com.holiday_resort.Requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
public class Startup implements CommandLineRunner {

    private final LoginDetailsRepository loginDetailsRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final CustomContext<Accommodation, AccommodationRepository> customContext;
    private final AccommodationRepository accommodationRepository;

    private final AuthController authController;

    @Autowired
    public Startup(UserRepository _userRepository,
                   LoginDetailsRepository _loginDetailsRepository,
                   AuthController _authController,
                   AccommodationRepository accommodationRepository,
                   CustomContext<Accommodation, AccommodationRepository> customContext,
                   @Lazy PasswordEncoder _passwordEncoder){

        this.userRepository = _userRepository;
        this.loginDetailsRepository = _loginDetailsRepository;
        this.authController = _authController;
        this.passwordEncoder = _passwordEncoder;
        this.accommodationRepository = accommodationRepository;
        this.customContext = customContext;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("mckmus@gmail.com");
        registerRequest.setFirstName("Maciej");
        registerRequest.setLastName("Musial");
        registerRequest.setPhoneNumber("666666666");

        registerRequest.setUsername("123");
        registerRequest.setPassword("123");

        authController.registerUser(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerRequest.getUsername());
        loginRequest.setPassword(registerRequest.getPassword());

        ResponseEntity<JwtResponse> responseEntity = authController.authenticateUser(loginRequest);

        Long userId = responseEntity.getBody().getUserId();

        Optional<User> userOpt = userRepository.findById(userId);

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

        assert(userLoginDetails == userOpt.get().getLoginDetails());
        assert(accommodationForId == accommodation1);

        System.out.println(responseEntity.getBody());
    }
}
