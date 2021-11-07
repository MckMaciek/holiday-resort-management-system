package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Dto.ResortObjectDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.*;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.ResortObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ResortObjectService implements CrudOperations<ResortObjectDTO, Long>, Validate<ResortObjectDTO> {

    private final ResortObjectRepository resortObjectRepository;

    @Autowired
    public ResortObjectService(ResortObjectRepository resortObjectRepository){
        this.resortObjectRepository = resortObjectRepository;
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

        if(Objects.isNull(user.getReservation())) throw new NullPointerException("User reservation cannot be null!");

        Reservation userReservation = user.getReservation();
        List<Accommodation> accommodationList = userReservation.getAccommodationList();

        if(Objects.isNull(accommodationList)) throw new NullPointerException("User accommodation list cannot be null!");

        List<ResortObjectDTO> userObjectList = accommodationList.stream()
                .map(Accommodation::getResortObject)
                .map(ResortObjectDTO::new)
                .collect(Collectors.toList());

        return userObjectList;
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

    @Override
    public void add(ResortObjectDTO resortObjectDTO) {
        Optional<ResortObject> resortObject = resortObjectRepository.findById(resortObjectDTO.getId());
        resortObject.ifPresent(obj -> resortObjectRepository.save(obj));
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
                resortObjectDTO.getObjectName() != null &&
                resortObjectDTO.getObjectName() != null;
    }
}


