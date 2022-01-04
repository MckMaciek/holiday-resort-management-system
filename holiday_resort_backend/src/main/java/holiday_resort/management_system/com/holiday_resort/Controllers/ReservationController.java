package holiday_resort.management_system.com.holiday_resort.Controllers;


import holiday_resort.management_system.com.holiday_resort.Context.UserContext;
import holiday_resort.management_system.com.holiday_resort.Dto.ReservationDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Enums.ReservationStatus;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationRequest;
import holiday_resort.management_system.com.holiday_resort.Responses.ReservationResponse;
import holiday_resort.management_system.com.holiday_resort.Services.ReservationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static holiday_resort.management_system.com.holiday_resort.Enums.Access.ROLE_ADMIN;
import static holiday_resort.management_system.com.holiday_resort.Enums.Access.ROLE_USER;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags="[USER] - Manage resort objects")
@RequestMapping("/api/v1")
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
    public ResponseEntity<?> postReservationForUser(@RequestBody(required = true) ReservationRequest reservationRequest)
    throws IllegalArgumentException, NullPointerException
    {

        LoginDetails contextUser = userContext.getAssociatedUser();
        reservationService.setReservation(contextUser, reservationRequest);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/reservation/user/{reservationId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patchReservationForUser(@NotNull @PathVariable(name = "reservationId", required = true) Long reservationId,
            @RequestBody(required = true) ReservationRequest reservationRequest)
            throws IllegalArgumentException, NullPointerException
    {

        LoginDetails contextUser = userContext.getAssociatedUser();
        reservationService.patchReservation(contextUser, reservationRequest, reservationId);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/reservation/user/{reservationId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteReservationForUser(@NotNull @PathVariable(name = "reservationId", required = true) Long reservationId)
            throws IllegalArgumentException, NullPointerException
    {

        LoginDetails contextUser = userContext.getAssociatedUser();
        reservationService.deleteReservation(contextUser, reservationId);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/reservation/user/{reservationId}", method = RequestMethod.GET)
    public ResponseEntity<?> getReservationForUser(@NotNull @PathVariable(name = "reservationId", required = true) Long reservationId){

        LoginDetails contextUser = userContext.getAssociatedUser();
        Optional<ReservationDTO> reservationDTO = reservationService.findById(reservationId);

        return ResponseEntity.ok(new ReservationResponse(reservationDTO.get()));
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/reservation/user/all", method = RequestMethod.GET)
    public ResponseEntity<?> getReservationsForUser(){

        LoginDetails contextUser = userContext.getAssociatedUser();
        List<ReservationDTO> reservationDTOList = reservationService.getUserReservations(contextUser);

        return ResponseEntity.ok(
                reservationDTOList.stream()
                                  .map(ReservationResponse::new)
                                  .collect(Collectors.toList())
        );
    }


    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/reservation/user/{reservationId}/change-status", method = RequestMethod.GET)
    public ResponseEntity<?> markReservationInProgress(@NotNull @PathVariable(name = "reservationId", required = true) Long reservationId)
    throws IllegalArgumentException, UnsupportedOperationException
    {

        LoginDetails contextUser = userContext.getAssociatedUser();
        reservationService.changeReservationStatus(ReservationStatus.NEW, contextUser, reservationId);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize(ROLE_ADMIN)
    @RequestMapping(value = "/reservation/{reservationId}/change-status-adm", method = RequestMethod.GET)
    public ResponseEntity<?> changeReservationStatus(@NotNull @PathVariable(name = "reservationId", required = true) Long reservationId,
                                                     @RequestParam(name="status", required = true) String status)
            throws IllegalArgumentException, UnsupportedOperationException
    {

        LoginDetails contextUser = userContext.getAssociatedUser();
        reservationService.changeReservationStatus(ReservationStatus.valueOf(status), contextUser, reservationId);

        return ResponseEntity.ok().build();
    }


}
