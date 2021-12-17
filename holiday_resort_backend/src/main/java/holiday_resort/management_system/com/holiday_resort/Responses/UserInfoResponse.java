package holiday_resort.management_system.com.holiday_resort.Responses;


import holiday_resort.management_system.com.holiday_resort.Entities.User;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private String firstName;
    private String lastName;
    private String phoneNumber;


    public UserInfoResponse(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
    }
}
