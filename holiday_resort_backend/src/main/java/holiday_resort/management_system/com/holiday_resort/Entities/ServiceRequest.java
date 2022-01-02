package holiday_resort.management_system.com.holiday_resort.Entities;


import holiday_resort.management_system.com.holiday_resort.Dto.ServiceRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "external_services_request_tb")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="service_name")
    private String serviceName;

    @Column(name="is_number_of_people_irrelevant")
    private Boolean isNumberOfPeopleIrrelevant;

    private BigDecimal cost;

    public ServiceRequest(ServiceRequestDTO serviceRequestDTO){
        this.id = serviceRequestDTO.getId();
        this.serviceName = serviceRequestDTO.getServiceName();
        this.cost = serviceRequestDTO.getCost();
        this.isNumberOfPeopleIrrelevant = serviceRequestDTO.getIsNumberOfPeopleIrrelevant();
    }
}
