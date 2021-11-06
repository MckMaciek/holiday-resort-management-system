package holiday_resort.management_system.com.holiday_resort.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationRequest {

    @NotNull
    private Long numberOfPeople;
    @NotNull
    private Long resortObjectId;
}
