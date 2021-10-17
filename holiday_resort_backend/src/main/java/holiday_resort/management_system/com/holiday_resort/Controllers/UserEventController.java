package holiday_resort.management_system.com.holiday_resort.Controllers;

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import holiday_resort.management_system.com.holiday_resort.Controllers.Exceptions.UserControllerExceptions;
import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginUser;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginUserRepository;
import holiday_resort.management_system.com.holiday_resort.Services.EventService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@Api(tags="[USER] - Get user events")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserEventController {

    private static final String ROLE_ADMIN_OR_USER = "hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')";

    private final LoginUserRepository loginUserRepository;
    private final EventService eventService;

    @Autowired
    public UserEventController(LoginUserRepository loginUserRepository, EventService eventService){
        this.loginUserRepository = loginUserRepository;
        this.eventService = eventService;
    }

    @PreAuthorize(ROLE_ADMIN_OR_USER)
    @RequestMapping(value = "/event/all", method = RequestMethod.GET)
    public ResponseEntity<List<EventDTO>> getEvents()
            throws InvalidParameterException {

        LoginUser contextUser = getAssociatedUser();

        return ResponseEntity.ok(eventService.findEventsForUser(contextUser));
    }

    @PreAuthorize(ROLE_ADMIN_OR_USER)
    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET)
    public ResponseEntity<EventDTO> getEvent(@PathVariable(name = "eventId", required = true) Long eventId)
            throws InvalidParameterException {

        LoginUser contextUser = getAssociatedUser();
        EventDTO eventDTO = null;

        try{
            eventDTO = eventService.findEventForUser(contextUser, eventId);

        }catch(RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok(eventDTO);
    }

    @PreAuthorize(ROLE_ADMIN_OR_USER)
    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patchEvent(@PathVariable(name = "eventId", required = true) Long eventId,
            @RequestBody JsonMergePatch patch)
            throws InvalidParameterException {

        LoginUser contextUser = getAssociatedUser();

        try{
            eventService.patchEventUserAction(contextUser, eventId, patch);

        }catch(RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PreAuthorize(ROLE_ADMIN_OR_USER)
    @RequestMapping(value = "/event/delete/{eventId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEvent(@PathVariable(name = "eventId", required = true) Long eventId){

        LoginUser contextUser = getAssociatedUser();
        if(eventService.deleteEventUserAction(contextUser, eventId)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize(ROLE_ADMIN_OR_USER)
    @RequestMapping(value = "/event/add", method = RequestMethod.POST)
    public ResponseEntity<?> addEvent(@RequestBody(required = true) EventDTO eventDTO)
            throws InvalidParameterException {

        LoginUser contextUser = getAssociatedUser();
        try{
            eventService.addEventUserAction(contextUser, eventDTO);

        }catch(RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }


    private LoginUser getAssociatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return loginUserRepository.findByUsername(userName)
                    .orElseThrow(UserControllerExceptions.UserNotFoundException::new);
    }

}
