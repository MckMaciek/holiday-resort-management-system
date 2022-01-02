package holiday_resort.management_system.com.holiday_resort.Responses;

import holiday_resort.management_system.com.holiday_resort.Dto.ReservationDTO;
import holiday_resort.management_system.com.holiday_resort.Enums.ReservationStatus;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationOwnerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ReservationResponse {

    private Long id;
    private BigDecimal finalPrice;
    private String reservationName;
    private String description;
    private LocalDate reservationDate;
    private LocalDate reservationEndingDate;
    private LocalDate creationDate;
    private ReservationStatus reservationStatus;

    private List<ReservationRemarksResponse> reservationRemarksResponse;
    private List<AccommodationResponse> accommodationResponses;
    private List<ExternalServiceResponse> externalServiceResponses;

    private ReservationOwnerRequest reservationOwnerRequest;


    public ReservationResponse(ReservationDTO reservationDTO){

        this.id = reservationDTO.getId();
        this.finalPrice = reservationDTO.getFinalPrice();
        this.reservationDate = reservationDTO.getReservationDate();
        this.creationDate = reservationDTO.getCreationDate();
        this.reservationStatus = reservationDTO.getReservationStatus();
        this.accommodationResponses = reservationDTO.getAccommodationListDTO()
                                                    .stream()
                                                    .map(AccommodationResponse::new)
                                                    .collect(Collectors.toList());

        this.reservationRemarksResponse = reservationDTO.getReservationRemarks()
                                                    .stream()
                                                    .map(ReservationRemarksResponse::new)
                                                    .collect(Collectors.toList());

        this.externalServiceResponses = reservationDTO.getExternalServiceDTOS()
                                                    .stream()
                                                    .map(ExternalServiceResponse::new)
                                                    .collect(Collectors.toList());

        this.reservationEndingDate = reservationDTO.getReservationEndingDate();
        this.reservationName = reservationDTO.getReservationName();
        this.description = reservationDTO.getDescription();

        this.reservationOwnerRequest = new ReservationOwnerRequest(reservationDTO.getReservationOwnerDTO());
    }


}
