package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Dto.ResortObjectDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.*;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.ResortObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ResortObjectService implements CrudOperations<ResortObjectDTO, Long>, Validate<ResortObjectDTO> {

    private final ResortObjectRepository resortObjectRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ResortObjectService(ResortObjectRepository resortObjectRepository,
                               ReservationRepository reservationRepository
                               ){
        this.resortObjectRepository = resortObjectRepository;
        this.reservationRepository = reservationRepository;
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

    @Override
    public void add(ResortObjectDTO resortObjectDTO) {

        ResortObject resortObject = new ResortObject(resortObjectDTO);
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
                resortObjectDTO.getObjectName() != null &&
                resortObjectDTO.getObjectName() != null;
    }
}


