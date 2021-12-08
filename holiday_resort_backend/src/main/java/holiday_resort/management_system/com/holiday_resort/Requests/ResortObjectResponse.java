package holiday_resort.management_system.com.holiday_resort.Requests;

import holiday_resort.management_system.com.holiday_resort.Dto.ResortObjectDTO;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResortObjectResponse {

    private Long id;
    private String objectName;
    private String objectType;
    private Long maxAmountOfPeople;
    private BigDecimal pricePerPerson;
    private BigDecimal unusedSpacePrice;
    private Boolean isReserved;

    public ResortObjectResponse (ResortObjectDTO resortObjectDTO){
        this.id = resortObjectDTO.getId();
        this.objectName = resortObjectDTO.getObjectName();
        this.objectType = resortObjectDTO.getObjectType();
        this.maxAmountOfPeople = resortObjectDTO.getMaxAmountOfPeople();
        this.pricePerPerson = resortObjectDTO.getPricePerPerson();
        this.unusedSpacePrice = resortObjectDTO.getUnusedSpacePrice();
        this.isReserved = resortObjectDTO.getIsReserved();
    }
}
