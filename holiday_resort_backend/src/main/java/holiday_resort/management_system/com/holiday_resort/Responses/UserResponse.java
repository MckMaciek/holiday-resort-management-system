package holiday_resort.management_system.com.holiday_resort.Responses;


import holiday_resort.management_system.com.holiday_resort.Dto.UserDTO;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private List<ReservationResponse> reservationResponses;

    public UserResponse(UserDTO userDTO){
        this.id = userDTO.getId();
        this.firstName = userDTO.getFirstname();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();

        this.reservationResponses = userDTO.getReservationDTO().stream().map(ReservationResponse::new).collect(Collectors.toList());
    }
}
