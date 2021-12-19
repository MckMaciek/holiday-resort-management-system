package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Dto.ServiceRequestDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.ServiceRequest;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.ServiceRequestRepository;
import org.h2.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceRequestService implements CrudOperations<ServiceRequestDTO, Long>, Validate<ServiceRequestDTO> {

    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository){
        this.serviceRequestRepository = serviceRequestRepository;
    }

    @Override
    public List<ServiceRequestDTO> getAll() {
        return serviceRequestRepository.findAll().stream().map(ServiceRequestDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<ServiceRequestDTO> findById(Long aLong) {
        Optional<ServiceRequest> serviceRequestOpt = serviceRequestRepository.findById(aLong);
        return serviceRequestOpt.map(ServiceRequestDTO::new);
    }

    @Override
    public void add(ServiceRequestDTO serviceRequestDTO) {
        if(serviceRequestDTO.getId() != null) throw new IllegalArgumentException("Cannot insert entity with non null itd");
        if(!validate(serviceRequestDTO)) throw new IllegalArgumentException("Entity validation failed");

        ServiceRequest serviceRequest = new ServiceRequest(serviceRequestDTO);
        serviceRequestRepository.save(serviceRequest);
    }


    @Override
    public Boolean delete(Long aLong) {
        Optional<ServiceRequest> serviceRequestOpt = serviceRequestRepository.findById(aLong);
        if(serviceRequestOpt.isEmpty()) throw new IllegalArgumentException(String.format("Could not find entity with id of %s", aLong));

        serviceRequestRepository.delete(serviceRequestOpt.get());
        return true;
    }

    @Override
    public boolean validate(ServiceRequestDTO serviceRequestDTO) {
        return !StringUtils.isNullOrEmpty(serviceRequestDTO.getServiceName()) && serviceRequestDTO.getCost() != null;
    }
}
