package holiday_resort.management_system.com.holiday_resort.Converters;

import holiday_resort.management_system.com.holiday_resort.Dto.ExternalServiceDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.ServiceRequestDTO;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Converter;
import holiday_resort.management_system.com.holiday_resort.Requests.ExternalServicesRequest;
import holiday_resort.management_system.com.holiday_resort.Services.ServiceRequestService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class ExternalServicesConverter implements Converter<ExternalServiceDTO, Object> {

    private final ServiceRequestService serviceRequestService;

    public ExternalServicesConverter(ServiceRequestService serviceRequestService){
        this.serviceRequestService = serviceRequestService;
    }

    @Override
    public ExternalServiceDTO convert(Object externalServiceObj) {

        if(externalServiceObj instanceof ExternalServicesRequest){
            ExternalServicesRequest externalServicesRequest = (ExternalServicesRequest) externalServiceObj;
            return convertExternalServiceRequestToDTO(externalServicesRequest);
        }

        else throw new ClassCastException("Object could not be casted to ExternalServiceDTO class");
    }

    private ExternalServiceDTO convertExternalServiceRequestToDTO(ExternalServicesRequest externalServicesRequest){

        ExternalServiceDTO externalServiceDTO = ExternalServiceDTO.builder()
                .amountOfPeople(externalServicesRequest.getAmountOfPeople())
                .remarks(externalServicesRequest.getRemarks())
                .serviceRequestDTO(findExternalService(externalServicesRequest.getServiceRequestId()))
                .date(externalServicesRequest.getDate().plus(1, ChronoUnit.DAYS))
                .build();

        return externalServiceDTO;
    }


    private ServiceRequestDTO findExternalService(Long id){
        Optional<ServiceRequestDTO> externalServiceDTOOpt = serviceRequestService.findById(id);
        if(externalServiceDTOOpt.isEmpty()) throw new IllegalArgumentException(String.format("Service request with id of %s has not been found", id));

        return externalServiceDTOOpt.get();
    }

}
