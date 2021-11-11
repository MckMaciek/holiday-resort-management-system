package holiday_resort.management_system.com.holiday_resort.Context;

import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.Reservation;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReservationContext {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationContext(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    public LoginDetails getAssociatedUser(Reservation reservation){

        Optional<Reservation> associatedRes = reservationRepository.findById(reservation.getId());

        if(associatedRes.isEmpty()) throw new IllegalArgumentException(
                String.format("Reservation with id %s has no owner!", reservation.getId()));

        LoginDetails resOwner = associatedRes.get().getUser().getLoginDetails();
        return resOwner;
    }

    public boolean checkIfOwnerAndUserRequestAreSame(LoginDetails eventOwner, LoginDetails requestUser){
        return eventOwner.getUsername().equals(requestUser.getUsername());
    }
}
