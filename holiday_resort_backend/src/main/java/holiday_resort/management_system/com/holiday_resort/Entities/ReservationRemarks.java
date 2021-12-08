package holiday_resort.management_system.com.holiday_resort.Entities;


import holiday_resort.management_system.com.holiday_resort.Dto.ReservationRemarksDTO;
import holiday_resort.management_system.com.holiday_resort.Interfaces.LoginDetailsLinked;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_remarks_tb")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRemarks implements LoginDetailsLinked {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    private Reservation reservation;

    @Column(name="topic")
    @NotBlank
    private String topic;

    @Column(name="description")
    private String description;

    @Column(name="creation_date")
    @NotNull
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name="modification_date")
    private LocalDateTime modificationDate;

    public ReservationRemarks(ReservationRemarksDTO reservationRemarksDTO){
        this.id = reservationRemarksDTO.getId();
        this.creationDate = reservationRemarksDTO.getCreationDate();
        this.modificationDate = reservationRemarksDTO.getModificationDate();
        this.description = reservationRemarksDTO.getDescription();
        this.topic = reservationRemarksDTO.getTopic();

        this.reservation = new Reservation(reservationRemarksDTO.getReservation());
    }

    @Override
    public LoginDetails getLinkedLoginDetails() {
        return reservation.getUser().getLoginDetails();
    }
}
