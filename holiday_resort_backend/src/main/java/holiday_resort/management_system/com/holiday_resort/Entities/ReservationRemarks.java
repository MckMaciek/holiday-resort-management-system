package holiday_resort.management_system.com.holiday_resort.Entities;


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
public class ReservationRemarks {

    @Id
    private Long id;

    @OneToOne
    @MapsId
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
}
