package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Context.AccommodationContext;
import holiday_resort.management_system.com.holiday_resort.Context.ReservationContext;
import holiday_resort.management_system.com.holiday_resort.Converters.AccommodationConverter;
import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.Accommodation;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.Reservation;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.AccommodationRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.AccommodationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccommodationService implements CrudOperations<AccommodationDTO, Long>, Validate<AccommodationDTO> {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationConverter accommodationConverter;
    private final AccommodationContext accommodationContext;

    private final ReservationRepository reservationRepository;
    private final ReservationContext reservationContext;

    private final ResortObjectService resortObjectService;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository,
                                AccommodationConverter accommodationConverter,
                                ResortObjectService resortObjectService,
                                AccommodationContext accommodationContext,
                                ReservationRepository reservationRepository,
                                ReservationContext reservationContext
                                ){
        this.accommodationRepository = accommodationRepository;
        this.accommodationConverter = accommodationConverter;
        this.resortObjectService = resortObjectService;
        this.accommodationContext = accommodationContext;
        this.reservationRepository = reservationRepository;
        this.reservationContext = reservationContext;
    }

    public AccommodationDTO getAccommodationForUser(Long accommodationId, LoginDetails loginDetails){

        Optional<Accommodation> accommodation = accommodationRepository.findById(accommodationId);

        if(accommodation.isEmpty()) throw new NullPointerException(
                String.format("Accommodation with id %s does not exist!", accommodationId));

        LoginDetails accommodationOwner = accommodationContext.getAssociatedUser(accommodation.get());
        if (!accommodationContext.checkIfOwnerAndUserRequestAreSame(accommodationOwner, loginDetails)){
            throw new IllegalArgumentException(
                    String.format("Accommodation owner - %s and username in request - %s do not match!",
                            accommodationOwner.getUsername(), loginDetails.getUsername()));
        }

        return new AccommodationDTO(accommodation.get());
    }

    public List<AccommodationDTO> getAccommodationListForUser(LoginDetails loginDetails){

        if(Objects.isNull(loginDetails.getUser())) throw new NullPointerException("User cannot be null!");
        User user = loginDetails.getUser();

        if(Objects.isNull(user.getId())) throw new NullPointerException("User Id cannot be null!");

        Reservation reservation = loginDetails.getUser().getReservation();
        if(Objects.isNull(reservation)) return Collections.emptyList();

        return reservation
                .getAccommodationList()
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
