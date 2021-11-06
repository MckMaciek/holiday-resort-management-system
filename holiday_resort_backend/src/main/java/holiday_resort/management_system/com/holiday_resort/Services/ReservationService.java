package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Dto.ReservationDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationRequest;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginUser;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService implements CrudOperations<ReservationDTO, Long>, Validate<ReservationDTO> {


    public void setReservation(LoginUser loginUser, ReservationRequest reservationReq){

        if(Objects.isNull(loginUser.getUser())) throw new NullPointerException("User cannot be null!");
        User user = loginUser.getUser();

        //TODO LATER


    }

    @Override
    public List<ReservationDTO> getAll() {
        return null;
    }

    @Override
    public Optional<ReservationDTO> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public void add(ReservationDTO reservationDTO) {

    }

    @Override
    public Boolean delete(Long aLong) {
        return null;
    }

    @Override
    public boolean validate(ReservationDTO reservationDTO) {
        return false;
    }
}
