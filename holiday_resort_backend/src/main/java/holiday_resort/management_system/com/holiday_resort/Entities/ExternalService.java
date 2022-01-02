package holiday_resort.management_system.com.holiday_resort.Entities;


import holiday_resort.management_system.com.holiday_resort.Dto.ExternalServiceDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "external_services_tb")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalService {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private ServiceRequest serviceRequest;

    @Column(name="people_amount")
    private Long amountOfPeople;

    private LocalDate date;

    @OneToOne(fetch = FetchType.EAGER)
    @NotNull
    private Reservation reservation;

    private String remarks;

    public ExternalService(ExternalServiceDTO externalServiceDTO){
        this.id = externalServiceDTO.getId();
        this.amountOfPeople = externalServiceDTO.getAmountOfPeople();
        this.remarks = externalServiceDTO.getRemarks();
    }
}
