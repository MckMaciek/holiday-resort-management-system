package holiday_resort.management_system.com.holiday_resort.Entities;


import holiday_resort.management_system.com.holiday_resort.Dto.ReservationOwnerDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reservation_owner_tb")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="phone_number")
    private String phoneNumber;

    public ReservationOwner(ReservationOwnerDTO reservationOwnerDTO){
        this.id = reservationOwnerDTO.getId();
        this.lastName = reservationOwnerDTO.getLastName();
        this.firstName = reservationOwnerDTO.getFirstName();
        this.phoneNumber = reservationOwnerDTO.getPhoneNumber();
    }
}
