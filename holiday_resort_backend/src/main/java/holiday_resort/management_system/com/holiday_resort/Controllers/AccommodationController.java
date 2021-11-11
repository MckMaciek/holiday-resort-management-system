package holiday_resort.management_system.com.holiday_resort.Controllers;


import holiday_resort.management_system.com.holiday_resort.Context.UserContext;
import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Services.AccommodationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

import static holiday_resort.management_system.com.holiday_resort.Enums.Access.ROLE_USER;

@RestController
@Api(tags="[USER] - Manage accommodations")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final UserContext userContext;

    @Autowired
    public AccommodationController(AccommodationService accommodationService,
                                   UserContext userContext){

        this.accommodationService = accommodationService;
        this.userContext = userContext;
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/accommodation/{accommodationId}", method = RequestMethod.GET)
    public ResponseEntity<AccommodationDTO> getAccommodation(
            @PathVariable(name = "accommodationId", required = true) Long accommodationId)
            throws InvalidParameterException {

        LoginDetails contextUser = userContext.getAssociatedUser();
        AccommodationDTO accommodationDTO = null;

        try{
            accommodationDTO = accommodationService.getAccommodationForUser(accommodationId, contextUser);

        }catch(RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(accommodationDTO);
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/accommodation/all", method = RequestMethod.GET)
    public ResponseEntity<List<AccommodationDTO>> getAccommodations()
            throws InvalidParameterException {

        LoginDetails contextUser = userContext.getAssociatedUser();

        return ResponseEntity.ok(accommodationService.getAccommodationListForUser(contextUser));
    }






}
