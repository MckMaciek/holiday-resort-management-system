package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Converters.AccommodationConverter;
import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.Accommodation;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.AccommodationRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.AccommodationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccommodationService implements CrudOperations<AccommodationDTO, Long>, Validate<AccommodationDTO> {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationConverter accommodationConverter;

    private final GenericAction<Accommodation, AccommodationRepository> accommodationContext;

    private final ResortObjectService resortObjectService;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository,
                                AccommodationConverter accommodationConverter,
                                ResortObjectService resortObjectService,
                                GenericAction<Accommodation, AccommodationRepository> accommodationContext
                                ){
        this.accommodationRepository = accommodationRepository;
        this.accommodationConverter = accommodationConverter;
        this.resortObjectService = resortObjectService;
        this.accommodationContext = accommodationContext;
    }

    public AccommodationDTO getAccommodationForUser(Long accommodationId, LoginDetails loginDetails){

        Pair<LoginDetails, Accommodation> accommodationOwnerPair =
                accommodationContext.getAssociatedUser(accommodationRepository, accommodationId);

        if (!accommodationContext.checkIfOwnerAndUserRequestAreSame(accommodationOwnerPair.getFirst(), loginDetails)){
            throw new IllegalArgumentException(
                    String.format("Accommodation owner - %s and username in request - %s do not match!",
                            accommodationOwnerPair.getFirst().getUsername(), loginDetails.getUsername()));
        }

        return new AccommodationDTO(accommodationOwnerPair.getSecond());
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

    @Override
    public List<AccommodationDTO> getAll() {
        return accommodationRepository.findAll().stream().map(AccommodationDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<AccommodationDTO> findById(Long aLong) {
        Optional<Accommodation> reservationOptional = accommodationRepository.findById(aLong);
        return reservationOptional.map(AccommodationDTO::new);
    }

    @Override
    @Transactional
    public void add(AccommodationDTO accommodationDTO) {
        if(validate(accommodationDTO) && accommodationDTO.getId() != null){
            Accommodation accommodation = new Accommodation(accommodationDTO);
            resortObjectService.add(accommodationDTO.getResortObject());

            accommodationRepository.save(accommodation);
        }
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
        return accommodationDTO != null && accommodationDTO.getUser() != null
                && accommodationDTO.getResortObject() != null && accommodationDTO.getNumberOfPeople() != null;
    }

}
