package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.Reservation;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Enums.ReservationStatus;
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
public class ReservationDTO {

    private Long id;
    private String reservationName;
    private String description;
    private BigDecimal finalPrice;
    private LocalDate creationDate;
    private LocalDate reservationDate;
    private LocalDate reservationEndingDate;
    private ReservationStatus reservationStatus;
    private List<ReservationRemarksDTO> reservationRemarks;
    private List<AccommodationDTO> accommodationListDTO;
    private List<ExternalServiceDTO> externalServiceDTOS;
    private ReservationOwnerDTO reservationOwnerDTO;
    private User user;

    public ReservationDTO(Reservation reservation){

        this.id = reservation.getId();
        this.finalPrice = reservation.getFinalPrice();
        this.reservationStatus = reservation.getReservationStatus();
        this.reservationDate = reservation.getReservationDate();
        this.creationDate = reservation.getCreationDate();
        this.description = reservation.getDescription();

        this.reservationRemarks = reservation.getReservationRemarks()
                .stream()
                .map(ReservationRemarksDTO::new)
                .collect(Collectors.toList());

        this.accommodationListDTO = reservation.getAccommodationList()
                .stream()
                .map(AccommodationDTO::new)
                .collect(Collectors.toList());

        this.externalServiceDTOS = reservation.getExternalServiceList()
                .stream()
                .map(ExternalServiceDTO::new)
                .collect(Collectors.toList());

        this.user = reservation.getUser();
        this.reservationEndingDate = reservation.getReservationEndingDate();
        this.reservationName = reservation.getReservationName();

        this.reservationOwnerDTO = new ReservationOwnerDTO(reservation.getReservationOwner());
    }
    public ReservationDTO(){}
}
