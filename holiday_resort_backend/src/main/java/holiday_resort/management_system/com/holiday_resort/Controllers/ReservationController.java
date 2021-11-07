package holiday_resort.management_system.com.holiday_resort.Controllers;


import holiday_resort.management_system.com.holiday_resort.Controllers.Exceptions.UserControllerExceptions;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationRequest;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginDetailsRepository;
import holiday_resort.management_system.com.holiday_resort.Services.ReservationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags="[USER] - Manage resort objects")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservationController {

    private static final String ROLE_USER = "hasRole('ROLE_USER')";

    private final LoginDetailsRepository loginDetailsRepository;
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(LoginDetailsRepository loginDetailsRepository, ReservationService reservationService){
        this.loginDetailsRepository = loginDetailsRepository;
        this.reservationService = reservationService;
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/reservation/user", method = RequestMethod.POST)
    public ResponseEntity<?> postReservationForUser(@RequestBody(required = true) ReservationRequest reservationRequest) {

        LoginDetails contextUser = getAssociatedUser();

        reservationService.setReservation(contextUser, reservationRequest);

        return ResponseEntity.ok().build();
    }

    private LoginDetails getAssociatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return loginDetailsRepository.findByUsername(userName)
                .orElseThrow(UserControllerExceptions.UserNotFoundException::new);
    }
}
