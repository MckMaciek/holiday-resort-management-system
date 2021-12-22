package holiday_resort.management_system.com.holiday_resort.Dto;


import holiday_resort.management_system.com.holiday_resort.Entities.ReservationOwner;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationOwnerRequest;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationOwnerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public ReservationOwnerDTO(ReservationOwner reservationOwner){
        this.id = reservationOwner.getId();
        this.firstName = reservationOwner.getFirstName();
        this.lastName = reservationOwner.getLastName();
        this.phoneNumber = reservationOwner.getPhoneNumber();
    }

    public ReservationOwnerDTO(ReservationOwnerRequest reservationOwnerRequest){
        this.firstName = reservationOwnerRequest.getFirstName();
        this.lastName = reservationOwnerRequest.getLastName();
        this.phoneNumber = reservationOwnerRequest.getPhoneNumber();
    }
}
