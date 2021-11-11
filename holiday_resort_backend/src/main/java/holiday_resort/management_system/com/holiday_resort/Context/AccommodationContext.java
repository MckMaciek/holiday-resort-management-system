package holiday_resort.management_system.com.holiday_resort.Context;

import holiday_resort.management_system.com.holiday_resort.Entities.Accommodation;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Repositories.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccommodationContext {

    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationContext(AccommodationRepository accommodationRepository){
        this.accommodationRepository = accommodationRepository;
    }

    public LoginDetails getAssociatedUser(Accommodation accommodation){

        Optional<Accommodation> associatedAcc = accommodationRepository.findById(accommodation.getId());

        if(associatedAcc.isEmpty()) throw new IllegalArgumentException(
                String.format("Accommodation with id %s has no owner!", accommodation.getId()));

        LoginDetails accOwner = associatedAcc.get().getUser().getLoginDetails();
        return accOwner;
    }

    public boolean checkIfOwnerAndUserRequestAreSame(LoginDetails eventOwner, LoginDetails requestUser){
        return eventOwner.getUsername().equals(requestUser.getUsername());
    }
}
