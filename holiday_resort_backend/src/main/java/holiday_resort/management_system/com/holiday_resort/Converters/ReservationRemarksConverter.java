package holiday_resort.management_system.com.holiday_resort.Converters;

import holiday_resort.management_system.com.holiday_resort.Dto.ReservationRemarksDTO;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Converter;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationRemarksRequest;
import org.springframework.stereotype.Component;

@Component
public class ReservationRemarksConverter implements Converter<ReservationRemarksDTO, Object> {


    @Override
    public ReservationRemarksDTO convert(Object reservationRemarksRequestObj) {

        if(reservationRemarksRequestObj instanceof ReservationRemarksRequest){
            ReservationRemarksRequest reservationRemarksRequest = (ReservationRemarksRequest) reservationRemarksRequestObj;
            return convertReservationRequestToDto(reservationRemarksRequest);
        }
        else throw new ClassCastException("Object could not be casted to AccommodationDTO class");
    }


    private ReservationRemarksDTO convertReservationRequestToDto(ReservationRemarksRequest reservationRemarksRequest){
        ReservationRemarksDTO reservationRemarksDTO = ReservationRemarksDTO.builder()
                .creationDate(reservationRemarksRequest.getCreationDate())
                .description(reservationRemarksRequest.getDescription())
                .topic(reservationRemarksRequest.getTopic())
                .build();

        return reservationRemarksDTO;
    }
}
