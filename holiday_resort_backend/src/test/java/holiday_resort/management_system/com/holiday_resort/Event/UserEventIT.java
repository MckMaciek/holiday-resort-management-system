package holiday_resort.management_system.com.holiday_resort.Event;

import holiday_resort.management_system.com.holiday_resort.Entities.Event;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Enums.EventEnum;
import holiday_resort.management_system.com.holiday_resort.Repositories.EventRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class UserEventIT {

    private final static String FIRST_NAME = "TEST";
    private final static String LAST_NAME = "TEST";
    private final static String EMAIL = "TEST@GMAIL.COM";

    private final static String DATE_VALUE = "1986-04-08 12:30";
    private static LocalDateTime TIME_NOW;


    private User user;
    private Event eventForUser;
    private Event eventForUser2;

    private Event notUserEvent;

    @Autowired
    private EventRepository eventRepo;
    @Autowired
    private UserRepository userRepo;

    @BeforeAll()
    public void init(){
        prepareData();
    }

    @Test
    public void checkIntegrityWithProperUser(){

        //WHEN
        userRepo.save(user);

        //THEN
        List<User> userList = userRepo.findAll();

        assertThat(userList).hasSize(1);
        User downloadedUser = userList.get(0);
        Long extractedId = downloadedUser.getId();

        eventForUser.setUser(user);
        eventForUser2.setUser(user);

        eventRepo.save(eventForUser);
        eventRepo.save(eventForUser2);
        eventRepo.save(notUserEvent);

        assertThat(downloadedUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(downloadedUser.getLastName()).isEqualTo(user.getLastName());
        assertThat(downloadedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(downloadedUser.getCreationDate()).isEqualTo(user.getCreationDate());
        assertThat(downloadedUser.getModificationDate()).isEqualTo(user.getModificationDate());

        //WHEN
        List<Event> listOfUserEvents = eventRepo.getEventsByUserId(extractedId);

        //THEN
        assertThat(listOfUserEvents).hasSize(2);
        Event userEventExtracted = listOfUserEvents.get(0);

        assertThat(userEventExtracted.getUser().getId()).isEqualTo(extractedId);
        assertThat(userEventExtracted.getEventType()).isEqualTo(eventForUser.getEventType());
        assertThat(userEventExtracted.getPriority()).isEqualTo(eventForUser.getPriority());
        assertThat(userEventExtracted.getStartingDate()).isEqualTo(eventForUser.getStartingDate());
        assertThat(userEventExtracted.getEndingDate()).isEqualTo(eventForUser.getEndingDate());
        assertThat(userEventExtracted.getDurationDate()).isEqualTo(eventForUser.getDurationDate());

        List<Event> listOfAllEvents = eventRepo.findAll();

        assertThat(listOfAllEvents).hasSize(3);
    }

    @Test
    public void checkIfUserEventsAreDeleted(){

        userRepo.save(user);

        List<User> userList = userRepo.findAll();

        assertThat(userList).hasSize(1);
        User downloadedUser = userList.get(0);
        Long extractedId = downloadedUser.getId();

        eventForUser.setUser(user);
        eventForUser2.setUser(user);

        eventRepo.save(eventForUser);
        eventRepo.save(eventForUser2);
        eventRepo.save(notUserEvent);

        List<Event> listOfUserEvents = eventRepo.getEventsByUserId(extractedId);

        //THEN
        assertThat(listOfUserEvents).hasSize(2);

        eventRepo.delete(eventForUser);
        eventRepo.delete(eventForUser2);

        List<Event> eventsShouldBeEmpty = eventRepo.getEventsByUserId(extractedId);

        //THEN
        assertThat(eventsShouldBeEmpty).hasSize(0);

        List<Event> listOfAllEvents = eventRepo.findAll();
        assertThat(listOfAllEvents).hasSize(1);
    }

    private void prepareData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        TIME_NOW = LocalDateTime.parse(DATE_VALUE, formatter);

        user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .creationDate(TIME_NOW)
                .modificationDate(TIME_NOW)
                .build();

        eventForUser = Event.builder()
                .eventType(EventEnum.CHECK_IN)
                .durationDate(TIME_NOW)
                .startingDate(TIME_NOW)
                .endingDate(TIME_NOW)
                .priority(3)
                .build();

        eventForUser2 = Event.builder()
                .eventType(EventEnum.CHECK_IN)
                .durationDate(TIME_NOW)
                .startingDate(TIME_NOW)
                .endingDate(TIME_NOW)
                .priority(3)
                .build();

        notUserEvent = Event.builder()
                .eventType(EventEnum.CHECK_IN)
                .durationDate(TIME_NOW)
                .startingDate(null)
                .endingDate(null)
                .priority(3)
                .build();
    }


}
