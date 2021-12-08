package holiday_resort.management_system.com.holiday_resort.Controllers;


import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import holiday_resort.management_system.com.holiday_resort.Context.UserContext;
import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Requests.AccommodationResponse;
import holiday_resort.management_system.com.holiday_resort.Services.AccommodationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<AccommodationResponse> getAccommodation(
            @PathVariable(name = "accommodationId", required = true) Long accommodationId)
            throws IllegalArgumentException, NullPointerException {

        LoginDetails contextUser = userContext.getAssociatedUser();
        AccommodationDTO accommodationDTO = accommodationService.getAccommodationForUser(accommodationId, contextUser);

        return ResponseEntity.ok(new AccommodationResponse(accommodationDTO));
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/accommodation/all", method = RequestMethod.GET)
    public ResponseEntity<List<AccommodationResponse>> getAccommodations() {

        LoginDetails contextUser = userContext.getAssociatedUser();

        return ResponseEntity.ok(
                accommodationService.getAccommodationListForUser(contextUser)
                .stream()
                .map(AccommodationResponse::new)
                .collect(Collectors.toList())
        );
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/accommodation/user/{accommodationId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patchAccommodation(@NotNull @PathVariable(name = "accommodationId", required = true) Long accommodationId,
                                                             @RequestBody JsonMergePatch patch)
            throws IllegalArgumentException, NullPointerException{

        LoginDetails contextUser = userContext.getAssociatedUser();
        accommodationService.patchAccommodation(contextUser, accommodationId, patch);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/accommodation/user/{accommodationId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAccommodation(@NotNull @PathVariable(name = "accommodationId", required = true) Long accommodationId)
            throws IllegalArgumentException, NullPointerException{

        LoginDetails contextUser = userContext.getAssociatedUser();
        accommodationService.deleteAccommodation(contextUser, accommodationId);

        return ResponseEntity.ok().build();
    }


}
