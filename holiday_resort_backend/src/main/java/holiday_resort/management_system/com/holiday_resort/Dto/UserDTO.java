package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String firstname;
    private String lastName;
    private String email;

    private UserDTO(){
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.firstname = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}
