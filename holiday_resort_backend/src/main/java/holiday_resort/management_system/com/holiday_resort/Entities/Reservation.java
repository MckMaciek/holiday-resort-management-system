package holiday_resort.management_system.com.holiday_resort.Entities;

import holiday_resort.management_system.com.holiday_resort.Dto.ReservationDTO;
import holiday_resort.management_system.com.holiday_resort.Enums.ReservationStatus;
import holiday_resort.management_system.com.holiday_resort.Interfaces.LoginDetailsLinked;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="reservation_name")
    private String reservatonName;

    @Column(name="reservation_status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Column(name="reservation_date")
    private LocalDateTime reservationDate;

    @Column(name="reservation_ending_date")
    private LocalDateTime reservationEndingDate;

    @Column(name="final_price")
    private BigDecimal finalPrice;

    @OneToMany(mappedBy = "reservation", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReservationRemarks> reservationRemarks;

    @OneToMany(mappedBy = "reservation", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Accommodation> accommodationList;

    @OneToOne(fetch = FetchType.EAGER)
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
        this.reservationEndingDate = reservationDTO.getReservationEndingDate();
        this.reservatonName = reservationDTO.getReservationName();
    }

    @Override
    public LoginDetails getLinkedLoginDetails() {
        return user.getLoginDetails();
    }
}
