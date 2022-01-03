package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.ResortObjectDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.*;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.ResortObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ResortObjectService implements CrudOperations<ResortObjectDTO, Long>, Validate<ResortObjectDTO> {

    private final ResortObjectRepository resortObjectRepository;
    private final ReservationRepository reservationRepository;

    private final GenericAction<ResortObject, ResortObjectRepository> resortObjectContext;

    private final EventService eventService;

    @Autowired
    public ResortObjectService(ResortObjectRepository resortObjectRepository,
                               ReservationRepository reservationRepository,
                               EventService eventService,
                               GenericAction<ResortObject, ResortObjectRepository> resortObjectContext
                               ){
        this.resortObjectRepository = resortObjectRepository;
        this.reservationRepository = reservationRepository;
        this.eventService = eventService;
        this.resortObjectContext = resortObjectContext;
    }

    private final Predicate<ResortObject> objectNotReservedFilter = obj -> !obj.getIsReserved();
    private final Predicate<ResortObject> objectTypeNotNull = obj -> Objects.nonNull(obj.getObjectType());
    private final Predicate<ResortObject> objectNameNotNull = obj -> Objects.nonNull(obj.getObjectName());

    public List<ResortObjectDTO> getAvailableObjects (){

        return resortObjectRepository.findAll().stream()
                .filter(objectNotReservedFilter.and
                        (objectTypeNotNull.and
                                (objectNameNotNull)))
                .map(ResortObjectDTO::new)
                .collect(Collectors.toList());
    }

    public List<ResortObjectDTO> getUserObjects(LoginDetails loginDetails){

        if(Objects.isNull(loginDetails.getUser())) throw new NullPointerException("User cannot be null!");
        User user = loginDetails.getUser();

        List<Reservation> reservationList = reservationRepository.findBriefByUser(user);
        if(Objects.isNull(reservationList)) throw new NullPointerException("User reservation list cannot be null!");

        List<ResortObjectDTO> userObjectList = reservationList.stream()
                .flatMap(reservation -> reservation.getAccommodationList().stream())
                .map(Accommodation::getResortObject)
                .map(ResortObjectDTO::new)
                .collect(Collectors.toList());

        return userObjectList;
    }

    public List<EventDTO> getEvents(Long resortObjectId){

        Optional<ResortObject> resortObject = resortObjectRepository.findById(resortObjectId);
        if(resortObject.isEmpty()) throw new InvalidParameterException(String.format("Resort object with if of %s has not been found", resortObjectId));

        return resortObject.get()
                .getEventList()
                .stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void postObject(ResortObjectDTO resortObjectDTO, List<EventDTO> eventRequests){

        if(Objects.nonNull(resortObjectDTO.getId())) throw new IllegalArgumentException("Id of object has to be null in order to add it");

        List<Event> eventsForRO = null;
        if(Objects.nonNull(eventRequests) && eventRequests.size() != 0){
            eventsForRO = eventRequests.stream().map(Event::new).collect(Collectors.toList());
            eventsForRO.forEach(event -> {

                if(Objects.isNull(event.getId())){
                    eventService.addEntity(event);
                }

            });
        }

        ResortObject resortObject = ResortObject.builder()
                .unusedSpacePrice(resortObjectDTO.getUnusedSpacePrice())
                .pricePerPerson(resortObjectDTO.getPricePerPerson())
                .maxAmountOfPeople(resortObjectDTO.getMaxAmountOfPeople())
                .photo(resortObjectDTO.getPhoto())
                .objectType(resortObjectDTO.getObjectType())
                .objectName(resortObjectDTO.getObjectName())
                .isReserved(resortObjectDTO.getIsReserved())
                .eventList(eventsForRO)
                .build();

        this.add(resortObject);
    }

    @Transactional
    public List<ResortObject> mapDtoToEntity(ResortObjectDTO resortObjectDTOS){

        List<ResortObject> resortObjectList = List.of(resortObjectDTOS).stream()
                .map(DTO -> resortObjectRepository.findById(DTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return resortObjectList;
    }

    @Override
    public List<ResortObjectDTO> getAll() {
        return resortObjectRepository.findAll().stream()
                .map(ResortObjectDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ResortObjectDTO> findById(Long aLong) {
        Optional<ResortObject> resortObjectOpt =  resortObjectRepository.findById(aLong);
        return resortObjectOpt.map(ResortObjectDTO::new);
    }

    public Optional<ResortObject> getEntityById(Long aLong){
        return resortObjectRepository.findById(aLong);
    }

    @Override
    public void add(ResortObjectDTO resortObjectDTO) {
        ResortObject resortObject = new ResortObject(resortObjectDTO);
        resortObjectRepository.save(resortObject);
    }

    public void add(ResortObject resortObject){
        resortObjectRepository.save(resortObject);
    }

    @Override
    public Boolean delete(Long aLong) {
        Optional<ResortObject> resortObjectOpt =  resortObjectRepository.findById(aLong);
        if(resortObjectOpt.isPresent()){
            resortObjectRepository.delete(resortObjectOpt.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean validate(ResortObjectDTO resortObjectDTO) {
        return resortObjectDTO.getIsReserved() != null &&
                resortObjectDTO.getObjectName() != null;
    }
}


