package holiday_resort.management_system.com.holiday_resort.Requests;


import holiday_resort.management_system.com.holiday_resort.Dto.ReservationOwnerDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationOwnerRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;

    public ReservationOwnerRequest(ReservationOwnerDTO reservationOwnerDTO){
        this.firstName = reservationOwnerDTO.getFirstName();
        this.lastName = reservationOwnerDTO.getLastName();
        this.phoneNumber = reservationOwnerDTO.getPhoneNumber();
    }
}
