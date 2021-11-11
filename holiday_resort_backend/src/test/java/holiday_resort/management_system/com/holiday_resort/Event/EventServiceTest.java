package holiday_resort.management_system.com.holiday_resort.Event;


import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.Event;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Repositories.EventRepository;
import holiday_resort.management_system.com.holiday_resort.Services.EventService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event userEvent;
    private User user;

    private final Long USER_ID = 1L;

    @BeforeAll
    private void init(){

        user = User.builder()
                        .id(USER_ID)
                        .firstName("TEST")
                        .lastName("TEST")
                        .email("TEST@GMAIL.COM")
                        .creationDate(LocalDateTime.now())
                        .modificationDate(LocalDateTime.now())
                        .build();

        userEvent = Event.builder()
                            .id(33L)
                            .user(user)
                            .startingDate(LocalDateTime.now())
                            .endingDate(LocalDateTime.now())
                            .durationDate(LocalDateTime.now())
                            .priority(3)
                            .build();
    }

    @Test
    public void findAllEventsForUser(){
        when(eventRepository.getEventsByUserId(USER_ID)).thenReturn(List.of(userEvent));

        List<EventDTO> eventDTOS = eventService.findEventsForUser(user.getLoginDetails());
        assertThat(eventDTOS).hasSize(1);

        EventDTO extractedEvent = eventDTOS.get(0);

        assertThat(extractedEvent.getEventType()).isEqualTo(userEvent.getEventType());
        assertThat(extractedEvent.getDurationDate()).isEqualTo(userEvent.getDurationDate());
        assertThat(extractedEvent.getEndingDate()).isEqualTo(userEvent.getEndingDate());
        assertThat(extractedEvent.getStartingDate()).isEqualTo(userEvent.getStartingDate());
        assertThat(extractedEvent.getPriority()).isEqualTo(userEvent.getPriority());
        assertThat(extractedEvent.getId()).isEqualTo(userEvent.getId());

        verify(eventRepository, times(1)).getEventsByUserId(USER_ID);
        verifyNoMoreInteractions(eventRepository);
    }



}
