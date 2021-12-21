package holiday_resort.management_system.com.holiday_resort.Responses;

import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.Event;
import holiday_resort.management_system.com.holiday_resort.Enums.EventEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EventResponse {

    private Long id;
    private EventEnum eventType;
    private Date startingDate;
    private Date durationDate;
    private Integer priority;
    private BigDecimal price;

    public EventResponse(Event event){
        this.id = event.getId();
        this.eventType = event.getEventType();
        this.startingDate = event.getStartingDate();
        this.durationDate = event.getDurationDate();
        this.priority = event.getPriority();
        this.price = event.getPrice();
    }

    public EventResponse(EventDTO eventDTO){
        this.id = eventDTO.getId();
        this.eventType = eventDTO.getEventType();
        this.startingDate = eventDTO.getStartingDate();
        this.durationDate = eventDTO.getDurationDate();
        this.priority = eventDTO.getPriority();
        this.price = eventDTO.getPrice();
    }
}
