package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.ResortObject;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResortObjectDTO {

    private Long id;
    private String objectName;
    private String objectType;
    private Long maxAmountOfPeople;
    private BigDecimal pricePerPerson;
    private BigDecimal unusedSpacePrice;
    private List<EventDTO> eventList;
    private Boolean isReserved;
    private byte[] photo;

    public ResortObjectDTO (ResortObject resortObject){
        this.id = resortObject.getId();
        this.objectName = resortObject.getObjectName();
        this.objectType = resortObject.getObjectType();
        this.maxAmountOfPeople = resortObject.getMaxAmountOfPeople();
        this.pricePerPerson = resortObject.getPricePerPerson();
        this.unusedSpacePrice = resortObject.getUnusedSpacePrice();
        this.isReserved = resortObject.getIsReserved();
        this.photo = resortObject.getPhoto();

        this.eventList = resortObject.getEventList().stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
    }
}
