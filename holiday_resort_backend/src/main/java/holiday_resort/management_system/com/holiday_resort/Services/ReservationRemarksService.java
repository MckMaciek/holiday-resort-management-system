package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Converters.ReservationRemarksConverter;
import holiday_resort.management_system.com.holiday_resort.Dto.ReservationRemarksDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.Reservation;
import holiday_resort.management_system.com.holiday_resort.Entities.ReservationRemarks;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationRemarksRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationRemarksRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationRemarksService implements  Validate<ReservationRemarksDTO> {

    private final ReservationRemarksRepository reservationRemarksRepository;
    private final ReservationRemarksConverter reservationRemarksConverter;


    private final ReservationRepository reservationRepository;
    private final GenericAction<Reservation, ReservationRepository> reservationContext;

    @Autowired
    public ReservationRemarksService(ReservationRemarksConverter reservationRemarksConverter,
                                     ReservationRemarksRepository reservationRemarksRepository,
                                     GenericAction<Reservation, ReservationRepository> reservationContext,
                                     ReservationRepository reservationRepository

    ){
        this.reservationRemarksConverter = reservationRemarksConverter;
        this.reservationRemarksRepository = reservationRemarksRepository;
        this.reservationContext = reservationContext;
        this.reservationRepository = reservationRepository;
    }

    public ReservationRemarksDTO convertRequestToDTO(ReservationRemarksRequest reservationRemarksRequest){
        return reservationRemarksConverter.convert(reservationRemarksRequest);
    }

    public ReservationRemarks transformToEntity(ReservationRemarksDTO reservationRemarksDTO, Reservation reservation){

        ReservationRemarks reservationRemarks = new ReservationRemarks();
            reservationRemarks.setId(reservationRemarksDTO.getId());
            reservationRemarks.setCreationDate(reservationRemarksDTO.getCreationDate());
            reservationRemarks.setDescription(reservationRemarksDTO.getDescription());
            reservationRemarks.setTopic(reservationRemarksDTO.getTopic());
            reservationRemarks.setModificationDate(reservationRemarksDTO.getModificationDate());
            reservationRemarks.setReservation(reservation);
            reservationRemarks.setAuthor(reservationRemarksDTO.getAuthor());

        return reservationRemarks;
    }

    public void updateReservationRemarks(List<ReservationRemarks> reservationRemarksToInsert, LoginDetails loginDetails, Long reservationId){

        Pair<LoginDetails, Reservation> reservationOwnerPair =
                reservationContext.getAssociatedUser(reservationRepository, reservationId);

        if (!reservationContext.checkIfOwnerAndUserRequestAreSame(reservationOwnerPair.getFirst(), loginDetails)){
            throw new IllegalArgumentException(
                    String.format("Reservation owner - %s and username in request - %s do not match!",
                            reservationOwnerPair.getFirst().getUsername(), loginDetails.getUsername()));
        }

        Reservation reservation = reservationOwnerPair.getSecond();
        List<ReservationRemarks> reservationRemarksList = reservation.getReservationRemarks();
        reservationRemarksList.addAll(reservationRemarksToInsert);

        reservationRepository.save(reservation);
    }

    public void add(ReservationRemarksDTO reservationRemarksDTO) {
        if(validate(reservationRemarksDTO) && reservationRemarksDTO.getId() != null){
            ReservationRemarks reservationRemarks = new ReservationRemarks(reservationRemarksDTO);
            reservationRemarksRepository.save(reservationRemarks);
        }
    }

    public void add(ReservationRemarks reservationRemarks) {
        reservationRemarksRepository.save(reservationRemarks);
    }

    @Override
    public boolean validate(ReservationRemarksDTO reservationRemarksDTO) {
        return false;
    }
}
