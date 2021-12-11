package holiday_resort.management_system.com.holiday_resort.Entities;

import holiday_resort.management_system.com.holiday_resort.Dto.ResortObjectDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "resort_object_tb")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResortObject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="object_name")
    @NotBlank
    private String objectName;

    @Column(name="object_type")
    @NotBlank
    private String objectType;

    @Column(name="people_count")
    private Long maxAmountOfPeople;

    @Column(name="person_price")
    private BigDecimal pricePerPerson;

    @Column(name="unused_space_price")
    private BigDecimal unusedSpacePrice;

    @Column(name="reserved")
    @NotNull
    private Boolean isReserved;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Event> eventList;

    public ResortObject(ResortObjectDTO resortObjectDTO){
        this.id = resortObjectDTO.getId();
        this.objectName = resortObjectDTO.getObjectName();
        this.objectType = resortObjectDTO.getObjectType();
        this.maxAmountOfPeople = resortObjectDTO.getMaxAmountOfPeople();
        this.pricePerPerson = resortObjectDTO.getPricePerPerson();
        this.unusedSpacePrice = resortObjectDTO.getUnusedSpacePrice();
        this.isReserved = resortObjectDTO.getIsReserved();

        this.eventList = resortObjectDTO.getEventList().stream().map(Event::new).collect(Collectors.toList());
    }

}
