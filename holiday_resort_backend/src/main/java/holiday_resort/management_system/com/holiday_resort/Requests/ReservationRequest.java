package holiday_resort.management_system.com.holiday_resort.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    @NotNull
    private List<AccommodationRequest> accommodationRequestList;

    private List<ReservationRemarksRequest> reservationRemarksRequestList;

}
