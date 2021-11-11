package holiday_resort.management_system.com.holiday_resort.Services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import holiday_resort.management_system.com.holiday_resort.Context.CustomContext;
import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.Event;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.EventRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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

    private final CustomContext<Event, EventRepository> eventContext;

    @Autowired
    public EventService(EventRepository eventRepo,
                        UserRepository userRepo,
                        ObjectMapper objectMapper,
                        CustomContext<Event, EventRepository> eventContext
    ){
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.objectMapper = objectMapper;
        this.eventContext = eventContext;
    }

    public List<EventDTO> findEventsForUser(LoginDetails loginDetails){

        if(Objects.isNull(loginDetails.getUser())) throw new NullPointerException("User cannot be null!");
        User user = loginDetails.getUser();

        if(Objects.isNull(user.getId())) throw new NullPointerException("User Id cannot be null!");

        List<Event> listOfEvents = eventRepo.getEventsByUserId(user.getId());
        List<EventDTO> listOfEventsDto = listOfEvents
                                            .stream()
                                            .map(EventDTO::new)
                                            .collect(Collectors.toList());
        return listOfEventsDto;
    }

    public boolean patchEventUserAction(LoginDetails loginDetails, Long eventId, JsonMergePatch eventPatch){

        if(Objects.isNull(eventId)) throw new NullPointerException("Cannot patch event with null id!");

        Pair<LoginDetails, Event> eventAssociation = eventContext.getAssociatedUser(eventRepo, eventId);
        if(eventContext.checkIfOwnerAndUserRequestAreSame(eventAssociation.getFirst(), loginDetails)){
            try {
            Event eventPatched = applyPatchToEvent(eventPatch, eventAssociation.getSecond());

            eventRepo.save(eventPatched);

            } catch (JsonPatchException e) {
                e.printStackTrace();
                return false;

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public EventDTO findEventForUser(LoginDetails loginDetails, Long eventId){

        Pair<LoginDetails, Event> eventAssociation = eventContext.getAssociatedUser(eventRepo, eventId);
            if (!eventContext.checkIfOwnerAndUserRequestAreSame(eventAssociation.getFirst(), loginDetails)){
                throw new IllegalArgumentException(
                            String.format("Event owner - %s and username in request - %s do not match!",
                                    eventAssociation.getFirst().getUsername(), loginDetails.getUsername()));
            }

        return new EventDTO(eventAssociation.getSecond());
    }

    public boolean deleteEventUserAction(LoginDetails loginDetails, Long eventId){

        Pair<LoginDetails, Event> eventAssociation = eventContext.getAssociatedUser(eventRepo, eventId);

        if(eventContext.checkIfOwnerAndUserRequestAreSame(eventAssociation.getFirst(), loginDetails)){
            eventRepo.deleteById(eventId);
        }
        else throw new IllegalArgumentException(
                String.format("Event owner - %s and username in request - %s do not match!",
                        eventAssociation.getFirst().getUsername(), loginDetails.getUsername()));

        return true;
    }

    public boolean addEventUserAction(LoginDetails loginDetails, EventDTO eventDTO){

        if(Objects.nonNull(eventDTO.getId())) throw new IllegalArgumentException("Cannot insert event with Id");
        if(!validate(eventDTO)) throw new IllegalArgumentException("Cannot insert illegal event entity");

        Event eventToBeInserted = new Event(eventDTO);

        eventToBeInserted.setUser(loginDetails.getUser());
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

    private Event applyPatchToEvent(
            JsonMergePatch patch, Event targetEvent) throws JsonPatchException, JsonProcessingException {

        JsonNode patched = patch.apply(objectMapper.convertValue(targetEvent, JsonNode.class));
        return objectMapper.treeToValue(patched, Event.class);
    }

}
