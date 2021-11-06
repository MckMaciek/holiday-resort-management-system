package holiday_resort.management_system.com.holiday_resort.Controllers;


import holiday_resort.management_system.com.holiday_resort.Controllers.Exceptions.UserControllerExceptions;
import holiday_resort.management_system.com.holiday_resort.Dto.ResortObjectDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginUser;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginUserRepository;
import holiday_resort.management_system.com.holiday_resort.Services.ResortObjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags="[USER] - Manage resort objects")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ResortObjectController {

    private static final String ROLE_USER = "hasRole('ROLE_USER')";

    private final LoginUserRepository loginUserRepository;
    private final ResortObjectService resortObjectService;

    @Autowired
    public ResortObjectController(LoginUserRepository loginUserRepository, ResortObjectService resortObjectService){
        this.loginUserRepository = loginUserRepository;
        this.resortObjectService = resortObjectService;
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/resort/available", method = RequestMethod.GET)
    public ResponseEntity<List<ResortObjectDTO>> getAvailableResortObjects() {

        List<ResortObjectDTO> available = resortObjectService.getAvailableObjects();
        if(available.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(available);
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/resort/user/all", method = RequestMethod.GET)
    public ResponseEntity<List<ResortObjectDTO>> getUsersResortObjects(){

        LoginUser contextUser = getAssociatedUser();

        try{
            List<ResortObjectDTO> available = resortObjectService.getUserObjects(contextUser);
            if(available.isEmpty()) return ResponseEntity.noContent().build();

            return ResponseEntity.ok(available);

        }catch(RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }
    }


    private LoginUser getAssociatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return loginUserRepository.findByUsername(userName)
                .orElseThrow(UserControllerExceptions.UserNotFoundException::new);
    }
}
