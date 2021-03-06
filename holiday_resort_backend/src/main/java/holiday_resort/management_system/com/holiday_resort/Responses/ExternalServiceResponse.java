package holiday_resort.management_system.com.holiday_resort.Responses;


import holiday_resort.management_system.com.holiday_resort.Dto.ExternalServiceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ExternalServiceResponse {

    private Long serviceRequestId;
    private String serviceRequestName;
    private Long amountOfPeople;
    private BigDecimal cost;
    private String remarks;
    private LocalDate date;

    private Boolean isNumberOfPeopleIrrelevant;

    public ExternalServiceResponse(ExternalServiceDTO externalServiceDTO){
        this.serviceRequestId = externalServiceDTO.getId();
        this.serviceRequestName = externalServiceDTO.getServiceRequestDTO().getServiceName();
        this.amountOfPeople = externalServiceDTO.getAmountOfPeople();
        this.remarks = externalServiceDTO.getRemarks();
        this.cost = externalServiceDTO.getServiceRequestDTO().getCost();
        this.date = externalServiceDTO.getDate();

        this.isNumberOfPeopleIrrelevant = externalServiceDTO.getServiceRequestDTO().getIsNumberOfPeopleIrrelevant();
    }
}
