package holiday_resort.management_system.com.holiday_resort.Controllers;


import holiday_resort.management_system.com.holiday_resort.Context.UserContext;
import holiday_resort.management_system.com.holiday_resort.Dto.ReservationDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationRequest;
import holiday_resort.management_system.com.holiday_resort.Services.ReservationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import java.util.Optional;

import static holiday_resort.management_system.com.holiday_resort.Enums.Access.ROLE_USER;

@RestController
@Api(tags="[USER] - Manage resort objects")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserContext userContext;

    @Autowired
    public ReservationController(ReservationService reservationService,
                                 UserContext userContext
    ){
        this.reservationService = reservationService;
        this.userContext = userContext;
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/reservation/user", method = RequestMethod.POST)
    public ResponseEntity<?> postReservationForUser(@RequestBody(required = true) ReservationRequest reservationRequest) {

        LoginDetails contextUser = userContext.getAssociatedUser();

        reservationService.setReservation(contextUser, reservationRequest);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/reservation/user", method = RequestMethod.GET)
    public ResponseEntity<?> getReservationForUser(@NotNull Long reservationId){

        LoginDetails contextUser = userContext.getAssociatedUser();
        Optional<ReservationDTO> reservationDTO;

        try{
            reservationDTO = reservationService.findById(reservationId); // pozmieniac na optionale

        }catch(RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(reservationDTO.get());
    }

    //TODO REZERWACJA MUSI BYC ZATWIERDZONA PRZEZ MANAGERA
    // JAK UZYTKOWNIK WPLACI DO BEDZIE OPLACONA
    //TODO MANAGER MOZE ZMIENIC status NA jakikolwiek
    // JAK CHCESZ ANULOWAC REZERWACJE TO DZWON DO MAN I ANULUJ REZERWACJE LUB AUTOMATYCZNIE

}
