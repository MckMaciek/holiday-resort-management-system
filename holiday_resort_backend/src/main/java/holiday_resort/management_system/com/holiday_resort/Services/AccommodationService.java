package holiday_resort.management_system.com.holiday_resort.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import holiday_resort.management_system.com.holiday_resort.Converters.AccommodationConverter;
import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.*;
import holiday_resort.management_system.com.holiday_resort.Enums.ReservationStatus;
import holiday_resort.management_system.com.holiday_resort.Enums.RoleTypes;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.AccommodationRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.AccommodationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccommodationService implements CrudOperations<AccommodationDTO, Long>, Validate<AccommodationDTO> {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationConverter accommodationConverter;
    private final ObjectMapper objectMapper;

    private final GenericAction<Accommodation, AccommodationRepository> accommodationContext;

    private final ResortObjectService resortObjectService;
    private final EventService eventService;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository,
                                AccommodationConverter accommodationConverter,
                                ResortObjectService resortObjectService,
                                EventService eventService,
                                GenericAction<Accommodation, AccommodationRepository> accommodationContext,
                                ObjectMapper objectMapper
                                ){
        this.accommodationRepository = accommodationRepository;
        this.accommodationConverter = accommodationConverter;
        this.resortObjectService = resortObjectService;
        this.eventService = eventService;
        this.accommodationContext = accommodationContext;
        this.objectMapper = objectMapper;
    }

    public AccommodationDTO getAccommodationForUser(Long accommodationId, LoginDetails loginDetails){

        if(Objects.isNull(accommodationId)) throw new NullPointerException("Provided Id is invalid");

        Pair<LoginDetails, Accommodation> accommodationOwnerPair = getUserAssociatedWithAccommodation(accommodationId, loginDetails);

        return new AccommodationDTO(accommodationOwnerPair.getSecond());
    }

    @Transactional
    public void putAccommodationForUser(LoginDetails loginDetails, Long accommodationId, AccommodationRequest accommodationRequest){

        if(Objects.isNull(accommodationId)) throw new NullPointerException("Provided Id is invalid");

        Pair<LoginDetails, Accommodation> accommodationOwnerPair = getUserAssociatedWithAccommodation(accommodationId, loginDetails);

        Optional<ResortObject> chosenResortObjectOpt = resortObjectService.getEntityById(accommodationRequest.getResortObjectId());
        if(chosenResortObjectOpt.isEmpty()) throw new NullPointerException(String.format("Id of %s is not bound to any resort object", accommodationId));

        ResortObject resortObject = chosenResortObjectOpt.get();

        Accommodation userAccommodation = accommodationOwnerPair.getSecond();

        userAccommodation.setResortObject(resortObject);
        userAccommodation.setNumberOfPeople(accommodationRequest.getNumberOfPeople());

        if(accommodationRequest.getEventRequests().size() != 0){

            List<Event> userChosenEvents = eventService.assignListOfEvents(accommodationRequest.getEventRequests());
            List<Event> commonProduct = resortObject.getEventList().stream().filter(userChosenEvents::contains).collect(Collectors.toList());

            commonProduct.forEach(events -> {
                events.setStartingDate(Date.from(Instant.now()));
                events.setPriority(3);
            });
            userAccommodation.setUserEventList(commonProduct);

        } else userAccommodation.setUserEventList(null);

        this.add(userAccommodation);
    }

    public List<EventDTO> getResortObjectEvents (Long accommodationId, LoginDetails loginDetails){

        if(Objects.isNull(accommodationId)) throw new NullPointerException("Provided Id is invalid");
        Pair<LoginDetails, Accommodation> accommodationOwnerPair = getUserAssociatedWithAccommodation(accommodationId, loginDetails);

        return accommodationOwnerPair.getSecond()
                .getResortObject()
                .getEventList()
                .stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
    }

    public void patchAccommodation(LoginDetails loginDetails, Long accommodationId, JsonMergePatch accommodationPatch){

        if(Objects.isNull(accommodationId)) throw new NullPointerException("Provided Id is invalid");

        Pair<LoginDetails, Accommodation> accommodationOwnerPair = getUserAssociatedWithAccommodation(accommodationId, loginDetails);

        Accommodation accommodation = accommodationOwnerPair.getSecond();
        ReservationStatus targetStatus = accommodation.getReservation().getReservationStatus();

        if(targetStatus.equals(ReservationStatus.NEW) || targetStatus.equals(ReservationStatus.DRAFT) ){
            try {
                Accommodation accommodationPatched = applyPatchToAccommodation(accommodationPatch, accommodation);
                accommodationRepository.save(accommodationPatched);

            } catch (JsonPatchException | JsonProcessingException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Request could not be parsed");

            }
        } else throw new IllegalArgumentException(String.format("Accommodation with status %s cannot be modified by the user", targetStatus));
    }

    public void deleteAccommodation(LoginDetails loginDetails, Long accommodationId){

        if(Objects.isNull(accommodationId)) throw new NullPointerException("Provided Id is invalid");

        Pair<LoginDetails, Accommodation> accommodationOwnerPair = getUserAssociatedWithAccommodation(accommodationId, loginDetails);

        Reservation userReservation = accommodationOwnerPair.getSecond().getReservation();
        if(Objects.isNull(userReservation)) throw new NullPointerException(String.format("Accommodation with id of %s is not assigned to any reservation", accommodationId));

        List<RoleTypes> userRoles = accommodationOwnerPair.getFirst().getRoles().getRoleTypesList();

        if(userReservation.getReservationStatus() == ReservationStatus.DRAFT || userReservation.getReservationStatus() == ReservationStatus.NEW){

            accommodationOwnerPair.getSecond().setResortObject(null);
            accommodationOwnerPair.getSecond().setReservation(null);
            accommodationOwnerPair.getSecond().setUser(null);
            accommodationRepository.delete(accommodationOwnerPair.getSecond());
        }
        else if ((userReservation.getReservationStatus() != ReservationStatus.DRAFT || userReservation.getReservationStatus() != ReservationStatus.NEW)
                && (userRoles.contains(RoleTypes.ADMIN) || userRoles.contains(RoleTypes.MANAGER))
        ){

            accommodationOwnerPair.getSecond().setResortObject(null);
            accommodationOwnerPair.getSecond().setReservation(null);
            accommodationOwnerPair.getSecond().setUser(null);
            accommodationRepository.delete(accommodationOwnerPair.getSecond());
        }
        else throw new IllegalArgumentException("Only the privileged user is able to remove an accommodation with the status other than 'started'");
    }

    public List<AccommodationDTO> getAccommodationListForUser(LoginDetails loginDetails){

        if(Objects.isNull(loginDetails.getUser())) throw new NullPointerException("User cannot be null!");
        User user = loginDetails.getUser();

        if(Objects.isNull(user.getId())) throw new NullPointerException("User Id cannot be null!");
        List<Accommodation> accommodationList = accommodationRepository.getAccommodationByUser(user);

        return accommodationList
                .stream()
                .map(AccommodationDTO::new)
                .collect(Collectors.toList());
    }

    public AccommodationDTO convertRequestToDTO(AccommodationRequest accommodationRequest){
        return accommodationConverter.convert(accommodationRequest);
    }

    public Accommodation transformToEntity(AccommodationDTO accommodationDTO, Reservation reservation){

        ResortObject resortObject = resortObjectService.mapDtoToEntity(accommodationDTO.getResortObject()).get(0);
        resortObject.setIsReserved(true);

        List<Event> eventList = eventService.mapDtoToEntity(accommodationDTO.getEventDTOS());

        Accommodation accommodation = new Accommodation();
        accommodation.setId(null);
        accommodation.setUser(accommodationDTO.getUser());
        accommodation.setNumberOfPeople(accommodationDTO.getNumberOfPeople());
        accommodation.setReservation(reservation);
        accommodation.setResortObject(resortObject);
        accommodation.setUserEventList(eventList);

        return accommodation;
    }

    @Override
    public List<AccommodationDTO> getAll() {
        return accommodationRepository.findAll().stream().map(AccommodationDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<AccommodationDTO> findById(Long aLong) {
        Optional<Accommodation> reservationOptional = accommodationRepository.findById(aLong);
        return reservationOptional.map(AccommodationDTO::new);
    }


    public void add(Accommodation accommodation) { // BETTER TO USE ADD WITH RESERVATION
        accommodationRepository.save(accommodation);
    }

    @Override
    public void add(AccommodationDTO accommodationDTO) { // BETTER TO USE ADD WITH RESERVATION
        Accommodation accommodation = new Accommodation(accommodationDTO);
        accommodationRepository.save(accommodation);
    }


    @Override
    public Boolean delete(Long aLong) {
        Optional<Accommodation> reservationOptional = accommodationRepository.findById(aLong);
        if(reservationOptional.isPresent()){
            accommodationRepository.delete(reservationOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean validate(AccommodationDTO accommodationDTO) {
        return accommodationDTO != null  && accommodationDTO.getResortObject() != null && accommodationDTO.getNumberOfPeople() != null;
    }

    private Accommodation applyPatchToAccommodation(
            JsonMergePatch patch, Accommodation targetAccommodation) throws JsonPatchException, JsonProcessingException {

        JsonNode patched = patch.apply(objectMapper.convertValue(targetAccommodation, JsonNode.class));
        return objectMapper.treeToValue(patched, Accommodation.class);
    }

    private Pair<LoginDetails, Accommodation> getUserAssociatedWithAccommodation(Long accommodationId, LoginDetails loginDetails){

        Pair<LoginDetails, Accommodation> accommodationOwnerPair =
                accommodationContext.getAssociatedUser(accommodationRepository, accommodationId);

        if (!accommodationContext.checkIfOwnerAndUserRequestAreSame(accommodationOwnerPair.getFirst(), loginDetails)){
            throw new IllegalArgumentException(
                    String.format("Accommodation owner - %s and username in request - %s do not match!",
                            accommodationOwnerPair.getFirst().getUsername(), loginDetails.getUsername()));
        }

        return accommodationOwnerPair;
    }

}
