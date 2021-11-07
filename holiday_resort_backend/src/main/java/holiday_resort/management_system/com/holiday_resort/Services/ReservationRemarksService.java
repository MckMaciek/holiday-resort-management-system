package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Converters.ReservationRemarksConverter;
import holiday_resort.management_system.com.holiday_resort.Dto.ReservationRemarksDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.ReservationRemarks;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationRemarksRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationRemarksRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationRemarksService implements CrudOperations<ReservationRemarksDTO, Long>, Validate<ReservationRemarksDTO> {

    private final ReservationRemarksRepository reservationRemarksRepository;

    private final ReservationRemarksConverter reservationRemarksConverter;

    @Autowired
    public ReservationRemarksService(ReservationRemarksConverter reservationRemarksConverter,
                                     ReservationRemarksRepository reservationRemarksRepository){
        this.reservationRemarksConverter = reservationRemarksConverter;
        this.reservationRemarksRepository = reservationRemarksRepository;
    }

    public ReservationRemarksDTO convertRequestToDTO(ReservationRemarksRequest reservationRemarksRequest){
        return reservationRemarksConverter.convert(reservationRemarksRequest);
    }

    @Override
    public List<ReservationRemarksDTO> getAll() {
        return null;
    }

    @Override
    public Optional<ReservationRemarksDTO> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public void add(ReservationRemarksDTO reservationRemarksDTO) {
        if(validate(reservationRemarksDTO) && reservationRemarksDTO.getId() != null){
            ReservationRemarks reservationRemarks = new ReservationRemarks(reservationRemarksDTO);
            reservationRemarksRepository.save(reservationRemarks);
        }
    }

    @Override
    public Boolean delete(Long aLong) {
        return null;
    }

    @Override
    public boolean validate(ReservationRemarksDTO reservationRemarksDTO) {
        return false;
    }
}
