package holiday_resort.management_system.com.holiday_resort.Entities;


import holiday_resort.management_system.com.holiday_resort.Dto.ReservationRemarksDTO;
import holiday_resort.management_system.com.holiday_resort.Interfaces.LoginDetailsLinked;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Date;

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

    @Column(name="author")
    private String author;

    @Column(name="creation_date")
    private Date creationDate = Date.from(Instant.now());

    @Column(name="modification_date")
    private Date modificationDate;

    public ReservationRemarks(ReservationRemarksDTO reservationRemarksDTO){
        this.id = reservationRemarksDTO.getId();
        this.creationDate = reservationRemarksDTO.getCreationDate();
        this.modificationDate = reservationRemarksDTO.getModificationDate();
        this.description = reservationRemarksDTO.getDescription();
        this.topic = reservationRemarksDTO.getTopic();
        this.author = reservationRemarksDTO.getAuthor();
    }

    @Override
    public LoginDetails getLinkedLoginDetails() {
        return reservation.getUser().getLoginDetails();
    }
}
