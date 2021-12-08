package holiday_resort.management_system.com.holiday_resort.Controllers;


import holiday_resort.management_system.com.holiday_resort.Dto.ResortObjectDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Requests.ResortObjectResponse;
import holiday_resort.management_system.com.holiday_resort.Services.ResortObjectService;
import holiday_resort.management_system.com.holiday_resort.Context.UserContext;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static holiday_resort.management_system.com.holiday_resort.Enums.Access.ROLE_USER;

@RestController
@Api(tags="[USER] - Manage resort objects")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ResortObjectController {

    private final ResortObjectService resortObjectService;
    private final UserContext userContext;

    @Autowired
    public ResortObjectController(ResortObjectService resortObjectService,
                                  UserContext userContext
    ){
        this.resortObjectService = resortObjectService;
        this.userContext = userContext;
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/resort/available", method = RequestMethod.GET)
    public ResponseEntity<List<ResortObjectResponse>> getAvailableResortObjects() {

        List<ResortObjectDTO> available = resortObjectService.getAvailableObjects();
        if(available.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(
                available.stream()
                .map(ResortObjectResponse::new)
                .collect(Collectors.toList())
        );
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/resort/user/all", method = RequestMethod.GET)
    public ResponseEntity<List<ResortObjectDTO>> getUsersResortObjects(){

        LoginDetails contextUser = userContext.getAssociatedUser();

        try{
            List<ResortObjectDTO> available = resortObjectService.getUserObjects(contextUser);
            if(available.isEmpty()) return ResponseEntity.noContent().build();

            return ResponseEntity.ok(available);

        }catch(RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }
    }
}
