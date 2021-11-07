package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Converters.AccommodationConverter;
import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.Accommodation;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.AccommodationRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.AccommodationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccommodationService implements CrudOperations<AccommodationDTO, Long>, Validate<AccommodationDTO> {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationConverter accommodationConverter;

    private final ResortObjectService resortObjectService;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository,
                                AccommodationConverter accommodationConverter,
                                ResortObjectService resortObjectService){
        this.accommodationRepository = accommodationRepository;
        this.accommodationConverter = accommodationConverter;
        this.resortObjectService = resortObjectService;
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
