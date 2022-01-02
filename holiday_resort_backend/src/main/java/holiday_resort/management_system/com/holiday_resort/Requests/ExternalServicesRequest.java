package holiday_resort.management_system.com.holiday_resort.Requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalServicesRequest {

    private Long serviceRequestId;
    private Long amountOfPeople;
    private String remarks;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
}
