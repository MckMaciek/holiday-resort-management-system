package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.Event;
import holiday_resort.management_system.com.holiday_resort.Enums.EventEnum;

import java.time.LocalDateTime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventEnum eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    public LocalDateTime getDurationDate() {
        return durationDate;
    }

    public void setDurationDate(LocalDateTime durationDate) {
        this.durationDate = durationDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
