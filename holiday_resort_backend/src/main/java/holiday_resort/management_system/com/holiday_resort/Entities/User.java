package holiday_resort.management_system.com.holiday_resort.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="users_tb",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "email")
        },
        indexes = {
            @Index(columnList = "email", name = "email_idx")
        }
        )

@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="email")
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Column(name="first_name")
    @NotBlank
    @Size(max = 20)
    private String firstName;

    @Column(name="last_name")
    @NotBlank
    @Size(max = 20)
    private String lastName;

    @Column(name="phone_number")
    @NotBlank
    @Pattern(regexp="(^$|[0-9]{9})")
    private String phoneNumber;

    @Column(name="user_creation_date")
    @NotNull
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name="user_modification_date")
    private LocalDateTime modificationDate;

    @OneToOne(fetch = FetchType.EAGER)
    @NotNull
    private LoginDetails loginDetails;

    @OneToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    @OneToMany(fetch = FetchType.LAZY)
    private List<UserRemarks> userRemarks;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id_fk", insertable = false, updatable = false)
    private List<Event> eventList;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", loginUser=" + loginDetails +
                ", reservation=" + reservation +
                ", userRemarks=" + userRemarks +
                ", eventList=" + eventList +
                '}';
    }

    public static UserBuilder getInstanceOfBuilder(){
        return new UserBuilder();
    }

    public static class UserBuilder{
        private String email;
        private String firstName;
        private String lastName;
        private Long id;
        private String phoneNumber;
        private LocalDateTime creationDate;
        private LocalDateTime modificationDate;

        private List<Event> eventList;
        private List<UserRemarks> userRemarks;
        private Reservation reservation;
        private LoginDetails loginDetails;

        public UserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder setCreationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public UserBuilder setModificationDate(LocalDateTime modificationDate) {
            this.modificationDate = modificationDate;
            return this;
        }

        public UserBuilder setEventList(List<Event> eventList) {
            this.eventList = eventList;
            return this;
        }


        public UserBuilder setUserRemarks(List<UserRemarks> userRemarks) {
            this.userRemarks = userRemarks;
            return this;
        }

        public UserBuilder setReservation(Reservation reservation) {
            this.reservation = reservation;
            return this;
        }

        public UserBuilder setLoginDetails(LoginDetails loginDetails) {
            this.loginDetails = loginDetails;
            return this;
        }

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

            user.setId(this.id);
            user.setFirstName(this.firstName);
            user.setLastName(this.lastName);
            user.setEmail(this.email);
            user.setLoginDetails(this.loginDetails);
            user.setUserRemarks(this.userRemarks);
            user.setReservation(this.reservation);
            user.setCreationDate(this.creationDate);
            user.setEventList(this.eventList);
            user.setPhoneNumber(this.phoneNumber);
            user.setModificationDate(this.modificationDate);

            return user;
        }


    }


}
