package holiday_resort.management_system.com.holiday_resort.Responses;

import holiday_resort.management_system.com.holiday_resort.Dto.ReservationRemarksDTO;

import java.time.LocalDateTime;

public class ReservationRemarksResponse {

    private Long id;
    private String topic;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    public ReservationRemarksResponse(ReservationRemarksDTO reservationRemarksDTO){
        this.id = reservationRemarksDTO.getId();
        this.topic = reservationRemarksDTO.getTopic();
        this.description = reservationRemarksDTO.getDescription();
        this.creationDate = reservationRemarksDTO.getCreationDate();
        this.modificationDate = reservationRemarksDTO.getModificationDate();
    }
}
