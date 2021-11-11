package holiday_resort.management_system.com.holiday_resort.Controllers;

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginDetailsRepository;
import holiday_resort.management_system.com.holiday_resort.Services.EventService;
import holiday_resort.management_system.com.holiday_resort.Context.UserContext;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

import static holiday_resort.management_system.com.holiday_resort.Enums.Access.ROLE_USER;

@RestController
@Api(tags="[USER] - Get user events")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserEventController {

    private final LoginDetailsRepository loginDetailsRepository;
    private final EventService eventService;
    private final UserContext userContext;

    @Autowired
    public UserEventController(LoginDetailsRepository loginDetailsRepository,
                               EventService eventService,
                               UserContext userContext
    ){
        this.loginDetailsRepository = loginDetailsRepository;
        this.eventService = eventService;
        this.userContext = userContext;
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/event/all", method = RequestMethod.GET)
    public ResponseEntity<List<EventDTO>> getEvents()
            throws InvalidParameterException {

        LoginDetails contextUser = userContext.getAssociatedUser();

        return ResponseEntity.ok(eventService.findEventsForUser(contextUser));
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET)
    public ResponseEntity<EventDTO> getEvent(@PathVariable(name = "eventId", required = true) Long eventId)
            throws InvalidParameterException {

        LoginDetails contextUser = userContext.getAssociatedUser();
        EventDTO eventDTO = null;

        try{
            eventDTO = eventService.findEventForUser(contextUser, eventId);

        }catch(RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok(eventDTO);
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patchEvent(@PathVariable(name = "eventId", required = true) Long eventId,
            @RequestBody JsonMergePatch patch)
            throws InvalidParameterException {

        LoginDetails contextUser = userContext.getAssociatedUser();

        try{
            eventService.patchEventUserAction(contextUser, eventId, patch);

        }catch(RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/event/delete/{eventId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEvent(@PathVariable(name = "eventId", required = true) Long eventId){

        LoginDetails contextUser = userContext.getAssociatedUser();
        if(eventService.deleteEventUserAction(contextUser, eventId)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/event/add", method = RequestMethod.POST)
    public ResponseEntity<?> addEvent(@RequestBody(required = true) EventDTO eventDTO)
            throws InvalidParameterException {

        LoginDetails contextUser = userContext.getAssociatedUser();
        try{
            eventService.addEventUserAction(contextUser, eventDTO);

        }catch(RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
