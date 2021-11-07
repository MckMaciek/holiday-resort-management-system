package holiday_resort.management_system.com.holiday_resort.Entities;


import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "accommodation_tb")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {

    @Id
    private Long id;

    @Column(name="number_of_people")
    private Long numberOfPeople;

    @OneToOne
    private ResortObject resortObject;

    @OneToOne
    @MapsId
    private User user;

    public Accommodation(AccommodationDTO accommodationDTO){
        this.id = accommodationDTO.getId();
        this.numberOfPeople = accommodationDTO.getNumberOfPeople();
        this.resortObject = new ResortObject (accommodationDTO.getResortObject());
        this.user = accommodationDTO.getUser();
    }
}
