package holiday_resort.management_system.com.holiday_resort.Entities;


import holiday_resort.management_system.com.holiday_resort.Interfaces.LoginDetailsLinked;
import lombok.*;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements LoginDetailsLinked {

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

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    @NotNull
    private LoginDetails loginDetails;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Reservation> reservation;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserRemarks> userRemarks;

    @OneToMany(mappedBy = "user", fetch=FetchType.LAZY)
    private List<Event> eventList;

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
                ", eventList=" + eventList +
                '}';
    }

    @Override
    public LoginDetails getLinkedLoginDetails() {
        return loginDetails;
    }
}
