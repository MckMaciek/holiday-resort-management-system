package holiday_resort.management_system.com.holiday_resort.Requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    @NotNull
    private List<AccommodationRequest> accommodationRequestList;

    private List<ReservationRemarksRequest> reservationRemarksRequestList;
    private List<ExternalServicesRequest> externalServicesRequests;

    private String reservationName;
    private String description;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate reservationEndingDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate reservationStartingDate;

    private ReservationOwnerRequest reservationOwnerRequest;

}
