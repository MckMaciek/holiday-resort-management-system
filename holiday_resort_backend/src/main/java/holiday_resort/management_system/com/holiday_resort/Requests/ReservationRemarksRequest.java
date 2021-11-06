package holiday_resort.management_system.com.holiday_resort.Requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReservationRemarksRequest {

    @NotBlank
    private String topic;

    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime creationDate = LocalDateTime.now();
}
