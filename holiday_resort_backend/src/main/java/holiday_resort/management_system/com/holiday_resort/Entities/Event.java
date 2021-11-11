package holiday_resort.management_system.com.holiday_resort.Entities;

import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Enums.EventEnum;
import holiday_resort.management_system.com.holiday_resort.Interfaces.LoginDetailsLinked;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_tb")

@NamedQueries
    (
        {
@NamedQuery(name= Event.GET_EVENTS_BY_USER_ID, query = "select ev from Event ev where ev.user.id = :userId order by ev.startingDate")
        }
    )

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event implements LoginDetailsLinked {

    public final static String GET_EVENTS_BY_USER_ID  = "Event.getEventsByUserId";

    public Event(EventDTO eventDTO){
        this.id = eventDTO.getId();
        this.eventType = eventDTO.getEventType();
        this.startingDate = eventDTO.getStartingDate();
        this.endingDate = eventDTO.getEndingDate();
        this.durationDate = eventDTO.getDurationDate();
        this.priority = eventDTO.getPriority();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="event_type")
    @Enumerated(EnumType.STRING)
    @NotBlank
    private EventEnum eventType;

    @Column(name="starting_date")
    @NotBlank
    private LocalDateTime startingDate;

    @Column(name="ending_date")
    private LocalDateTime endingDate;

    @Column(name="duration_date")
    private LocalDateTime durationDate;

    @Column(name="priority")
    @NotBlank
    private Integer priority;

    @OneToOne
    @MapsId
    @ToString.Exclude
    private User user;

    @Override
    public LoginDetails getLinkedLoginDetails() {
        return user.getLoginDetails();
    }
}
