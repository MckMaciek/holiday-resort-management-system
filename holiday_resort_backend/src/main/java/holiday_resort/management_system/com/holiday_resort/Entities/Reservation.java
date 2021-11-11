package holiday_resort.management_system.com.holiday_resort.Entities;

import holiday_resort.management_system.com.holiday_resort.Dto.ReservationDTO;
import holiday_resort.management_system.com.holiday_resort.Interfaces.LoginDetailsLinked;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "reservation_tb")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation implements LoginDetailsLinked {

    @Id
    private Long id;

    @Column(name="final_price")
    private BigDecimal finalPrice;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "reservation")
    private List<ReservationRemarks> reservationRemarks;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "reservation")
    private List<Accommodation> accommodationList;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @NotNull
    private User user;

    public Reservation(ReservationDTO reservationDTO){
        this.id = reservationDTO.getId();
        this.finalPrice = reservationDTO.getFinalPrice();
        this.reservationRemarks = reservationDTO.getReservationRemarks()
                .stream()
                .map(ReservationRemarks::new)
                .collect(Collectors.toList());
        this.accommodationList = reservationDTO.getAccommodationListDTO()
                .stream()
                .map(Accommodation::new)
                .collect(Collectors.toList());

        this.user = reservationDTO.getUser();
    }

    @Override
    public LoginDetails getLinkedLoginDetails() {
        return user.getLoginDetails();
    }
}
