package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.ExternalService;
import holiday_resort.management_system.com.holiday_resort.Requests.ExternalServicesRequest;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalServiceDTO {

    private Long id;
    private Long amountOfPeople;
    private ServiceRequestDTO serviceRequestDTO;
    private LocalDate date;
    private String remarks;

    public ExternalServiceDTO(ExternalService externalService){
        this.id = externalService.getId();
        this.serviceRequestDTO = new ServiceRequestDTO(externalService.getServiceRequest());
        this.amountOfPeople = externalService.getAmountOfPeople();
        this.remarks = externalService.getRemarks();
        this.date = externalService.getDate();
    }

    public ExternalServiceDTO(ExternalServicesRequest externalServicesRequest){
        this.amountOfPeople = externalServicesRequest.getAmountOfPeople();
        this.remarks = externalServicesRequest.getRemarks();
        this.date = externalServicesRequest.getDate();
    }


}
