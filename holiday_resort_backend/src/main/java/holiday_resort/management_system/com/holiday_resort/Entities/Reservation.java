package holiday_resort.management_system.com.holiday_resort.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "user_reservation_tb")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    private Long id;

    @Column(name="address")
    @NotBlank
    private String address;

    @OneToMany(fetch=FetchType.LAZY)
    private List<ReservationRemarks> reservationRemarks;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @NotNull
    private User user;
}
