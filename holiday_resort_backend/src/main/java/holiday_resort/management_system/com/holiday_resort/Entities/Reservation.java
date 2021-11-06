package holiday_resort.management_system.com.holiday_resort.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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

    @Column(name="final_price")
    private BigDecimal finalPrice;

    @OneToMany(fetch=FetchType.LAZY)
    private List<ReservationRemarks> reservationRemarks;

    @OneToMany(fetch=FetchType.LAZY)
    private List<Accommodation> accommodationList;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @NotNull
    private User user;
}
