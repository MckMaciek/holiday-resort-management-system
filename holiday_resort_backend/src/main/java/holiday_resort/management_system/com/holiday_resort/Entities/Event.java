package holiday_resort.management_system.com.holiday_resort.Entities;

import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Enums.EventEnum;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_event_tb")

@NamedQueries
        (
            {
    @NamedQuery(name= Event.GET_EVENTS_BY_USER_ID, query = "select ev from Event ev where ev.userId = :userId order by ev.startingDate")
            }
        )

public class Event {

    public final static String GET_EVENTS_BY_USER_ID  = "Event.getEventsByUserId";

    public Event(EventDTO eventDTO){
        this.id = eventDTO.getId();
        this.eventType = eventDTO.getEventType();
        this.startingDate = eventDTO.getStartingDate();
        this.endingDate = eventDTO.getEndingDate();
        this.durationDate = eventDTO.getDurationDate();
        this.priority = eventDTO.getPriority();
    }

    public Event(Long id, Long userId, @NotBlank EventEnum eventType,
                 @NotBlank LocalDateTime startingDate, LocalDateTime endingDate,
                 LocalDateTime durationDate, @NotBlank Integer priority) {
        this.id = id;
        this.userId = userId;
        this.eventType = eventType;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.durationDate = durationDate;
        this.priority = priority;
    }

    public Event(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="user_id_fk")
    private Long userId;

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

    public static String getGetEventsByUserId() {
        return GET_EVENTS_BY_USER_ID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public static EventBuilder getInstanceOfBuilder(){
        return new EventBuilder();
    }

    public static class EventBuilder{

        private Long id;
        private Long userId;
        private EventEnum eventType;
        private LocalDateTime startingDate;
        private LocalDateTime endingDate;
        private LocalDateTime durationDate;
        private Integer priority;

        public EventBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public EventBuilder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public EventBuilder setEventType(EventEnum eventType) {
            this.eventType = eventType;
            return this;
        }

        public EventBuilder setStartingDate(LocalDateTime startingDate) {
            this.startingDate = startingDate;
            return this;
        }

        public EventBuilder setEndingDate(LocalDateTime endingDate) {
            this.endingDate = endingDate;
            return this;
        }

        public EventBuilder setDurationDate(LocalDateTime durationDate) {
            this.durationDate = durationDate;
            return this;
        }

        public EventBuilder setPriority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public Event build(){
            Event event = new Event();

            event.setEventType(this.eventType);
            event.setDurationDate(this.durationDate);
            event.setPriority(this.priority);
            event.setUserId(this.userId);
            event.setId(this.id);
            event.setStartingDate(this.startingDate);
            event.setEndingDate(this.endingDate);


            return event;
        }


    }
}
