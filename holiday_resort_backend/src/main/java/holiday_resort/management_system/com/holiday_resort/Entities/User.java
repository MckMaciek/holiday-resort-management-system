package holiday_resort.management_system.com.holiday_resort.Entities;


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
    private LoginUser loginUser;

    @OneToOne(fetch = FetchType.LAZY) // 1 DO WIELU ZMIENIC
    private Reservation reservation;

    @OneToMany(fetch = FetchType.LAZY)
    private List<UserRemarks> userRemarks;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id_fk", insertable = false, updatable = false)
    private List<Event> eventList;

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
        return modificationDate;
    }

    public void setUserCreationDate(LocalDateTime userCreationDate) {
        this.creationDate = userCreationDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public List<UserRemarks> getUserRemarks() {
        return userRemarks;
    }

    public void setUserRemarks(List<UserRemarks> userRemarks) {
        this.userRemarks = userRemarks;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
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
                ", loginUser=" + loginUser +
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
        private LoginUser loginUser;

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

        public UserBuilder setLoginUser(LoginUser loginUser) {
            this.loginUser = loginUser;
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

            return user;
        }


    }


}
