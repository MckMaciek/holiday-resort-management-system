package holiday_resort.management_system.com.holiday_resort.Entities;


import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import holiday_resort.management_system.com.holiday_resort.Interfaces.LoginDetailsLinked;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accommodation_tb")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation implements LoginDetailsLinked {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="number_of_people")
    private Long numberOfPeople;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Reservation reservation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ResortObject resortObject;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Event> userEventList;

    @OneToOne
    private User user;

    public Accommodation(AccommodationDTO accommodationDTO){
        this.numberOfPeople = accommodationDTO.getNumberOfPeople();
        this.user = accommodationDTO.getUser();
    }

    @Override
    public LoginDetails getLinkedLoginDetails() {
        return user.getLoginDetails();
    }
}
