package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.ReservationRemarks;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRemarksDTO {

    private Long id;
    private String topic;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    public ReservationRemarksDTO(ReservationRemarks reservationRemarks){
        this.id = reservationRemarks.getId();
        this.topic = reservationRemarks.getTopic();
        this.description = reservationRemarks.getDescription();
        this.creationDate = reservationRemarks.getCreationDate();
        this.modificationDate = reservationRemarks.getModificationDate();
    }
}
