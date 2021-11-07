package holiday_resort.management_system.com.holiday_resort.Converters;

import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.ResortObjectDTO;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Converter;
import holiday_resort.management_system.com.holiday_resort.Requests.AccommodationRequest;
import holiday_resort.management_system.com.holiday_resort.Services.ResortObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccommodationConverter implements Converter<AccommodationDTO, Object> {

    private final ResortObjectService resortObjectService;

    @Autowired
    public AccommodationConverter(ResortObjectService resortObjectService){
        this.resortObjectService = resortObjectService;
    }

    @Override
    public AccommodationDTO convert(Object accommodationRequestObj) {

        if(accommodationRequestObj instanceof AccommodationRequest){
            AccommodationRequest accommodationRequest = (AccommodationRequest) accommodationRequestObj;
            return convertAccommodationRequestToDTO(accommodationRequest);
        }
        else throw new ClassCastException("Object could not be casted to AccommodationDTO class");
    }

    private AccommodationDTO convertAccommodationRequestToDTO(AccommodationRequest accommodationRequest){
        AccommodationDTO accommodationDTO = new AccommodationDTO();
        accommodationDTO.setNumberOfPeople(accommodationRequest.getNumberOfPeople());

        Optional<ResortObjectDTO> resortObject = getResortObject(accommodationRequest.getResortObjectId());
        resortObject.ifPresent(accommodationDTO::setResortObject);

        if(resortObject.isEmpty()){
            throw new NullPointerException(
                    String.format("Resort Object not found with id of %s",accommodationRequest.getResortObjectId()));
        }

        return accommodationDTO;
    }

    private Optional<ResortObjectDTO> getResortObject(Long resortObjectId) {
        if(resortObjectId == null) throw new NullPointerException("Resort Object Id cannot be null!");

        return resortObjectService.findById(resortObjectId);
    }
}
