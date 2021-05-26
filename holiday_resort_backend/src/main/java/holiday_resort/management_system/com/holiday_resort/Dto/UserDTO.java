package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.User;

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

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
