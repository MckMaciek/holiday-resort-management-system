package holiday_resort.management_system.com.holiday_resort.Entities;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="users_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="email")
    @NotBlank
    private String email;

    @Column(name="first_name")
    @NotBlank
    private String firstName;

    @Column(name="last_name")
    @NotBlank
    private String lastName;

    @Column(name="user_creation_date")
    @NotNull
    private LocalDateTime userCreationDate = LocalDateTime.now();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getUserCreationDate() {
        return userCreationDate;
    }

    public void setUserCreationDate(LocalDateTime userCreationDate) {
        this.userCreationDate = userCreationDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userCreationDate=" + userCreationDate +
                '}';
    }

    public static UserBuilder getInstanceOfBuilder(){
        return new UserBuilder();
    }

    public static class UserBuilder{
        private String email;
        private String firstName;
        private String lastName;

        public UserBuilder setEmail(String email){
            this.email = email;
            return this;
        }
        public UserBuilder setFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }
        public UserBuilder setLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public User build(){
            User user = new User();

            user.setFirstName(this.firstName);
            user.setLastName(this.lastName);
            user.setEmail(this.email);

            return user;
        }


    }



}
