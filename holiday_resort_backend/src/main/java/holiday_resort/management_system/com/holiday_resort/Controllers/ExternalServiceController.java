package holiday_resort.management_system.com.holiday_resort.Controllers;


import holiday_resort.management_system.com.holiday_resort.Context.UserContext;
import holiday_resort.management_system.com.holiday_resort.Dto.ExternalServiceDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.ServiceRequestDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Services.ExternalServiceService;
import holiday_resort.management_system.com.holiday_resort.Services.ServiceRequestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static holiday_resort.management_system.com.holiday_resort.Enums.Access.ROLE_USER;

@RestController
@Api(tags="[ADMIN] - ExternalService")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExternalServiceController {

    private final ExternalServiceService externalServiceService;
    private final ServiceRequestService serviceRequestService;
    private final UserContext userContext;

    @Autowired
    public ExternalServiceController(ExternalServiceService externalServiceService,
                                     ServiceRequestService serviceRequestService,
                                     UserContext userContext
    ){
        this.externalServiceService = externalServiceService;
        this.serviceRequestService = serviceRequestService;
        this.userContext = userContext;
    }


    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/externalService/add", method = RequestMethod.POST)
    public ResponseEntity<?> addExternalService(@RequestBody(required = true) ExternalServiceDTO externalServiceDTO,
                                                @PathVariable(name = "reservationId", required = true) Long reservationId)
            throws IllegalArgumentException{

        LoginDetails contextUser = userContext.getAssociatedUser();

        externalServiceService.add(contextUser, externalServiceDTO, reservationId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/externalService/all", method = RequestMethod.GET)
    public ResponseEntity<List<ServiceRequestDTO>> getAvailableServices()
            throws IllegalArgumentException{

        return ResponseEntity.ok(
                serviceRequestService.getAll()

        );
    }


}
