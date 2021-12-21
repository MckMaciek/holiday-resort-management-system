package holiday_resort.management_system.com.holiday_resort.Requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Builder
public class ReservationRemarksRequest {

    @NotBlank
    private String topic;

    @NotBlank
    private String description;

    private Date creationDate = Date.from(Instant.now());
}
