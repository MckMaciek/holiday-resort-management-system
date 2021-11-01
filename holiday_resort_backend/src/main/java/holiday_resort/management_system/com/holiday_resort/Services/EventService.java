package holiday_resort.management_system.com.holiday_resort.Services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.Event;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginUser;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.EventRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService implements CrudOperations<EventDTO, Long>, Validate<EventDTO> {

    private final EventRepository eventRepo;
    private final UserRepository userRepo;
    private final ObjectMapper objectMapper;

    @Autowired
    public EventService(EventRepository eventRepo, UserRepository userRepo, ObjectMapper objectMapper){
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.objectMapper = objectMapper;
    }

    public List<EventDTO> findEventsForUser(LoginUser loginUser){

        if(Objects.isNull(loginUser.getUser())) throw new NullPointerException("User cannot be null!");
        User user = loginUser.getUser();

        if(Objects.isNull(user.getId())) throw new NullPointerException("User Id cannot be null!");

        List<Event> listOfEvents = eventRepo.getEventsByUserId(user.getId());
        List<EventDTO> listOfEventsDto = listOfEvents
                                            .stream()
                                            .map(EventDTO::new)
                                            .collect(Collectors.toList());
        return listOfEventsDto;
    }

    public boolean patchEventUserAction(LoginUser loginUser, Long eventId, JsonMergePatch eventPatch){

        if(Objects.isNull(eventId)) throw new NullPointerException("Cannot patch event with null id!");

        Optional<Event> event = eventRepo.findById(eventId);
        if(event.isEmpty()) throw new NullPointerException(
                String.format("Event with id %s does not exist!", eventId));

        event.ifPresent(ev -> {
                LoginUser eventOwner = getEventAssociatedUser(ev);
                if(checkIfEventOwnerAndUserRequestAreSame(eventOwner, loginUser)){
                    try {
                    Event eventPatched = applyPatchToEvent(eventPatch, ev);

                    eventRepo.save(eventPatched);

                    } catch (JsonPatchException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }

        });
        return true;
    }

    public EventDTO findEventForUser(LoginUser loginUser, Long eventId){
        Optional<Event> userEventOpt = eventRepo.getEventById(eventId);

        if(userEventOpt.isEmpty()) throw new NullPointerException(
                String.format("Event with id %s does not exist!", eventId));

        LoginUser eventOwner = getEventAssociatedUser(userEventOpt.get());
            if (!checkIfEventOwnerAndUserRequestAreSame(eventOwner, loginUser)){
                throw new IllegalArgumentException(
                            String.format("Event owner - %s and username in request - %s do not match!",
                                eventOwner.getUsername(), loginUser.getUsername()));
            }

        return new EventDTO(userEventOpt.get());
    }

    public boolean deleteEventUserAction(LoginUser loginUser, Long eventId){

        Optional<Event> userEventOpt = eventRepo.getEventById(eventId);
        if(userEventOpt.isEmpty()) throw new NullPointerException(
                String.format("Event with id %s does not exist!", eventId));

        userEventOpt.ifPresent(event ->{
            LoginUser eventOwner = getEventAssociatedUser(event);

            if(checkIfEventOwnerAndUserRequestAreSame(eventOwner, loginUser)){
                eventRepo.deleteById(eventId);
            }
            else throw new IllegalArgumentException(
                    String.format("Event owner - %s and username in request - %s do not match!",
                            eventOwner.getUsername(), loginUser.getUsername()));
        });
        return true; //wont reach if exception was thrown
    }

    public boolean addEventUserAction(LoginUser loginUser, EventDTO eventDTO){

        if(Objects.nonNull(eventDTO.getId())) throw new IllegalArgumentException("Cannot insert event with Id");
        if(!validate(eventDTO)) throw new IllegalArgumentException("Cannot insert illegal event entity");

        Event eventToBeInserted = new Event(eventDTO);

        eventToBeInserted.setUserId(loginUser.getUser().getId());
        eventRepo.save(eventToBeInserted);

        return true; //wont reach if exception was thrown
    }

    @Override
    public List<EventDTO> getAll() {
       return eventRepo.findAll().stream().map(EventDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<EventDTO> findById(Long aLong) {
        EventDTO eventDto = new EventDTO(eventRepo.findById(aLong).orElseThrow());
        return Optional.of(eventDto);
    }

    @Override
    public void add(EventDTO eventDTO) {
        if(validate(eventDTO)){
            Event event = new Event(eventDTO);
            eventRepo.save(event);
        }
    }

    @Override
    public Boolean delete(Long aLong) {
        Optional<Event> eventToDelete = eventRepo.getEventById(aLong);
        if(eventToDelete.isPresent()){
            eventRepo.delete(eventToDelete.get());
            return true;
        }

        return false;
    }

    @Override
    public boolean validate(EventDTO eventDTO) {
        boolean validated = true;
        if(Objects.isNull(eventDTO.getEventType())) validated = false;
        if(Objects.isNull(eventDTO.getPriority())) validated = false;
        if(Objects.isNull(eventDTO.getStartingDate())) validated = false;

        return validated;
    }

    private LoginUser getEventAssociatedUser(Event event){
        Optional<User> associatedUser = userRepo.findById(event.getUserId());

        if(associatedUser.isEmpty()) throw new IllegalArgumentException(
                String.format("Event with id %s has no owner!", event.getId()));

        LoginUser eventOwner = associatedUser.get().getLoginUser();

        return eventOwner;
    }

    private boolean checkIfEventOwnerAndUserRequestAreSame(LoginUser eventOwner, LoginUser requestUser){
        return eventOwner.getUsername().equals(requestUser.getUsername());
    }

    private Event applyPatchToEvent(
            JsonMergePatch patch, Event targetEvent) throws JsonPatchException, JsonProcessingException {

        JsonNode patched = patch.apply(objectMapper.convertValue(targetEvent, JsonNode.class));
        return objectMapper.treeToValue(patched, Event.class);
    }

}
