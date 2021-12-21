package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.Event;
import holiday_resort.management_system.com.holiday_resort.Enums.EventEnum;
import holiday_resort.management_system.com.holiday_resort.Requests.EventRequest;
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
public class EventDTO {

    private Long id;
    private EventEnum eventType;
    private Date startingDate;
    private Date durationDate;
    private BigDecimal price;
    private Integer priority;

    public EventDTO(Event event){
        this.id = event.getId();
        this.eventType = event.getEventType();
        this.startingDate = event.getStartingDate();
        this.durationDate = event.getDurationDate();
        this.priority = event.getPriority();
        this.price = event.getPrice();
    }

    public EventDTO(EventRequest eventRequest){
        this.id = eventRequest.getId();
        this.price = eventRequest.getPrice();
    }
}
