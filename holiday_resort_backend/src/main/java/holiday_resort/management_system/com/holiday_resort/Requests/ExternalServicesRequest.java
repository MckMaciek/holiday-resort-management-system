package holiday_resort.management_system.com.holiday_resort.Requests;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalServicesRequest {

    private Long serviceRequestId;
    private Long amountOfPeople;
    private String remarks;
}
