package holiday_resort.management_system.com.holiday_resort.Entities;


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
}
