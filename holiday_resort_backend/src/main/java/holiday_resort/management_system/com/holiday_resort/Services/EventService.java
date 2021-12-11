package holiday_resort.management_system.com.holiday_resort.Services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.Event;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.EventRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService implements CrudOperations<EventDTO, Long>, Validate<EventDTO> {

    private final EventRepository eventRepo;
    private final ObjectMapper objectMapper;

    private final GenericAction<Event, EventRepository> eventContext;

    @Autowired
    public EventService(EventRepository eventRepo,
                        ObjectMapper objectMapper,
                        GenericAction<Event, EventRepository> eventContext
    ){
        this.eventRepo = eventRepo;
        this.objectMapper = objectMapper;
        this.eventContext = eventContext;
    }

    public List<Event> assignListOfEvents(List<EventRequest> eventRequests){

        return eventRequests.stream()
                .map(EventDTO::new)
                .map(eventRequest -> eventRepo.findById(eventRequest.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

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

    public void addEntity(Event event) {
        eventRepo.save(event);
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
