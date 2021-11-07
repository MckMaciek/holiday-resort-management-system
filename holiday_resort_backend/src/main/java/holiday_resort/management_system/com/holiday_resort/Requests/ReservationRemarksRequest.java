package holiday_resort.management_system.com.holiday_resort.Requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReservationRemarksRequest {

    @NotBlank
    private String topic;

    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime creationDate = LocalDateTime.now();
}
