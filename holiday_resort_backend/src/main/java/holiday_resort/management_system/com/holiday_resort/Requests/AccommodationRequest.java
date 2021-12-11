package holiday_resort.management_system.com.holiday_resort.Requests;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class AccommodationRequest {

    @NotNull
    private Long numberOfPeople;
    @NotNull
    private Long resortObjectId;

    private List<EventRequest> eventRequests;
}
