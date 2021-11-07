package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.Event;
import holiday_resort.management_system.com.holiday_resort.Enums.EventEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EventDTO {

    private Long id;
    private EventEnum eventType;
    private LocalDateTime startingDate;
    private LocalDateTime endingDate;
    private LocalDateTime durationDate;
    private Integer priority;

    public EventDTO(){
    }

    public EventDTO(Event event){
        this.id = event.getId();
        this.eventType = event.getEventType();
        this.startingDate = event.getStartingDate();
        this.endingDate = event.getEndingDate();
        this.durationDate = event.getDurationDate();
        this.priority = event.getPriority();

    }
}
