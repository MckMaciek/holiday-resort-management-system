package holiday_resort.management_system.com.holiday_resort.Entities;

import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Enums.EventEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_tb")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    public Event(EventDTO eventDTO){
        this.id = eventDTO.getId();
        this.eventType = eventDTO.getEventType();
        this.startingDate = eventDTO.getStartingDate();
        this.durationDate = eventDTO.getDurationDate();
        this.priority = eventDTO.getPriority();
        this.price = eventDTO.getPrice();
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

    @Column(name="duration_date")
    private LocalDateTime durationDate;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="priority")
    @NotBlank
    private Integer priority;

}
