package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.ServiceRequest;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDTO {

    private Long id;
    private String serviceName;
    private BigDecimal cost;

    public ServiceRequestDTO(ServiceRequest serviceRequest){
        this.id = serviceRequest.getId();
        this.serviceName = serviceRequest.getServiceName();
        this.cost = serviceRequest.getCost();
    }
}
