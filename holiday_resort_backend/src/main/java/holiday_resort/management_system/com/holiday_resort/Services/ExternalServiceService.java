package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Converters.ExternalServicesConverter;
import holiday_resort.management_system.com.holiday_resort.Dto.ExternalServiceDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.ExternalService;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.Reservation;
import holiday_resort.management_system.com.holiday_resort.Entities.ServiceRequest;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.ExternalServiceRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.ExternalServicesRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExternalServiceService implements Validate<ExternalServiceDTO> {

    private final ExternalServiceRepository externalServiceRepository;
    private final ReservationRepository reservationRepository;
    private final GenericAction<Reservation, ReservationRepository> reservationContext;
    private final ExternalServicesConverter externalServicesConverter;

    public ExternalServiceService(ExternalServiceRepository externalServiceRepository,
                                  GenericAction<Reservation, ReservationRepository> reservationContext,
                                  ExternalServicesConverter externalServicesConverter,
                                  ReservationRepository reservationRepository
                                  ){
        this.externalServiceRepository = externalServiceRepository;
        this.reservationContext = reservationContext;
        this.reservationRepository = reservationRepository;
        this.externalServicesConverter = externalServicesConverter;
    }

    public List<ExternalServiceDTO> getAll() {
        return externalServiceRepository.findAll()
                .stream()
                .map(ExternalServiceDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ExternalServiceDTO> findById(Long aLong) {

        Optional<ExternalService> externalServiceOpt = externalServiceRepository.findById(aLong);
        return externalServiceOpt.map(ExternalServiceDTO::new);
    }

    public void add(LoginDetails loginDetails, ExternalServiceDTO externalServiceDTO, Long reservationId) {

        Optional<Reservation> userReservationOpt = reservationRepository.findById(reservationId);

        if(userReservationOpt.isEmpty()) throw new IllegalArgumentException(String.format("Cannot find reservation with id of %s", reservationId));
        if(!loginDetails.getUser().getReservation().contains(userReservationOpt.get())) throw new IllegalArgumentException("Reservation not found");

        Reservation userReservation = userReservationOpt.get();

        ExternalService externalService = new ExternalService(externalServiceDTO);
        externalService.setReservation(userReservation);

        if(externalService.getId() != null) throw new IllegalArgumentException("Cannot insert externalService with already existing id");
        if(validate(externalServiceDTO)) throw new IllegalArgumentException("Validation of externalService failed");

        externalServiceRepository.save(externalService);
        reservationRepository.save(userReservation);
    }

    public ExternalServiceDTO convertRequestToDTO(ExternalServicesRequest externalServicesRequest){
        return externalServicesConverter.convert(externalServicesRequest);
    }

    public ExternalService transformToEntity(ExternalServiceDTO externalServiceDTO, Reservation reservation){

        ExternalService externalService = ExternalService.builder()
                .id(externalServiceDTO.getId())
                .reservation(reservation)
                .amountOfPeople(externalServiceDTO.getAmountOfPeople())
                .remarks(externalServiceDTO.getRemarks())
                .serviceRequest(new ServiceRequest(externalServiceDTO.getServiceRequestDTO()))
                .build();

        return externalService;
    }

    public Boolean delete(Long aLong) {

        Optional<ExternalService> externalService = externalServiceRepository.findById(aLong);
        if(externalService.isEmpty()) throw new IllegalArgumentException(String.format("Could not delete externalService with id of %s - id not found", aLong));
        if(externalService.get().getId() == null) throw new IllegalArgumentException("Could not delete externalService with null id");

        externalServiceRepository.delete(externalService.get());
        return true;
    }

    @Override
    public boolean validate(ExternalServiceDTO externalServiceDTO) {
        return externalServiceDTO.getAmountOfPeople() != null;
    }
}
