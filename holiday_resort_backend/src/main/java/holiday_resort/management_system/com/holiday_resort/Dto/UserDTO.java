package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String firstname;
    private String lastName;
    private String email;

    private List<ReservationDTO> reservationDTO;

    private UserDTO(){
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.firstname = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();

        this.reservationDTO = user.getReservation().stream().map(ReservationDTO::new).collect(Collectors.toList());
    }
}
