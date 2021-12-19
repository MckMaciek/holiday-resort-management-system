package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.ExternalServiceDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.ReservationDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.ReservationRemarksDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.*;
import holiday_resort.management_system.com.holiday_resort.Enums.ReservationStatus;
import holiday_resort.management_system.com.holiday_resort.Enums.RoleTypes;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService implements CrudOperations<ReservationDTO, Long>, Validate<ReservationDTO> {

    private final ReservationRepository reservationRepository;

    private final GenericAction<Reservation, ReservationRepository> reservationContext;

    private final AccommodationService accommodationService;
    private final ReservationRemarksService reservationRemarksService;
    private final ExternalServiceService externalServiceService;
    private final PriceService priceService;

    private final static String SYSTEM_AUTHOR = "SYSTEM";
    private final static String STATUS_CHANGED = "Status changed";


    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              AccommodationService accommodationService,
                              PriceService priceService,
                              ReservationRemarksService reservationRemarksService,
                              ExternalServiceService externalServiceService,
                              GenericAction<Reservation, ReservationRepository> reservationContext
    ){
        this.reservationRepository = reservationRepository;
        this.accommodationService = accommodationService;
        this.priceService = priceService;
        this.reservationRemarksService = reservationRemarksService;
        this.reservationContext = reservationContext;
        this.externalServiceService = externalServiceService;
    }

    @Transactional
    public void setReservation(LoginDetails loginDetails, ReservationRequest reservationReq){

        if(!validateReservationRequest(reservationReq)) throw new IllegalArgumentException("Invalid Reservation Request!");
        if(Objects.isNull(loginDetails.getUser())) throw new NullPointerException("User cannot be null!");

        List<ExternalServiceDTO> externalServiceDTOS = reservationReq.getExternalServicesRequests()
                .stream()
                .map(externalServiceService::convertRequestToDTO)
                .collect(Collectors.toList());

        List<AccommodationDTO> accommodationDTOS =  reservationReq.getAccommodationRequestList()
                .stream()
                .map(accommodationService::convertRequestToDTO)
                .collect(Collectors.toList());

        accommodationDTOS.forEach(accommodationDTO -> accommodationDTO.setUser(loginDetails.getUser()));

        List<ReservationRemarksDTO> reservationRemarksDTOS = reservationReq.getReservationRemarksRequestList()
                .stream()
                .map(reservationRemarksService::convertRequestToDTO)
                .collect(Collectors.toList());

        ReservationDTO reservationDTO = ReservationDTO.builder()
                .accommodationListDTO(accommodationDTOS)
                .reservationName(reservationReq.getReservationName())
                .reservationEndingDate(reservationReq.getReservationEndingDate())
                .reservationStatus(ReservationStatus.DRAFT)
                .reservationDate(reservationReq.getReservationStartDate())
                .externalServiceDTOS(externalServiceDTOS)
                .reservationRemarks(reservationRemarksDTOS)
                .finalPrice(priceService.calculateFinalPrice())
                .user(loginDetails.getUser())
                .build();

        this.add(reservationDTO);
    }


    public List<ReservationDTO> getUserReservations(LoginDetails loginDetails){

        List<Reservation> reservationList = reservationRepository.findByUser(loginDetails.getUser());

        return reservationList
                    .stream()
                    .map(ReservationDTO::new)
                    .collect(Collectors.toList());
    }

    public void changeReservationStatus(ReservationStatus reservationStatus, LoginDetails loginDetails, Long reservationId){

        Pair<LoginDetails, Reservation> reservationOwnerPair =
                reservationContext.getAssociatedUser(reservationRepository, reservationId);

        if (!reservationContext.checkIfOwnerAndUserRequestAreSame(reservationOwnerPair.getFirst(), loginDetails)){
            throw new IllegalArgumentException(
                    String.format("Reservation owner - %s and username in request - %s do not match!",
                            reservationOwnerPair.getFirst().getUsername(), loginDetails.getUsername()));
        }

        Reservation reservation = reservationOwnerPair.getSecond();
        if(!ReservationStatus.DRAFT.equals(reservation.getReservationStatus())){
            List<RoleTypes> userRoles = reservationOwnerPair.getFirst().getRoles().getRoleTypesList();

            if(userRoles.contains(RoleTypes.MANAGER) || userRoles.contains(RoleTypes.ADMIN)){
                reservation.setReservationStatus(reservationStatus);
                flushReservationRemarks(reservation, reservationStatus, loginDetails);
            }
            else throw new UnsupportedOperationException("User is not privileged enough to change status");

        }
        else if(ReservationStatus.DRAFT.equals(reservation.getReservationStatus())){
            reservation.setReservationStatus(reservationStatus);
            flushReservationRemarks(reservation, reservationStatus, loginDetails);
        }
    }

    private void flushReservationRemarks(Reservation reservation, ReservationStatus reservationStatus, LoginDetails loginDetails){

        ReservationRemarks reservationRemarks = ReservationRemarks.builder()
                .reservation(reservation)
                .author(SYSTEM_AUTHOR)
                .creationDate(LocalDateTime.now())
                .topic(STATUS_CHANGED)
                .description(String.format("Reservation set to %s", reservationStatus))
                .build();

        reservationRemarksService.updateReservationRemarks(List.of(reservationRemarks),loginDetails, reservation.getId());
        reservationRepository.save(reservation);
    }

    @Override
    public List<ReservationDTO> getAll() {
        return reservationRepository.findAll().stream().map(ReservationDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDTO> findById(Long aLong) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(aLong);
        return reservationOptional.map(ReservationDTO::new);
    }

    @Override
    @Transactional
    public void add(ReservationDTO reservationDTO) {
        if(validate(reservationDTO)){

            Reservation reservation = transformToEntity(reservationDTO);
            reservationRepository.save(reservation);
        }
    }

    private Reservation transformToEntity(ReservationDTO reservationDTO){
        Reservation reservation = new Reservation();

        reservation.setReservationStatus(reservationDTO.getReservationStatus());
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setReservationEndingDate(reservationDTO.getReservationEndingDate());
        reservation.setReservatonName(reservationDTO.getReservationName());
        reservation.setUser(reservationDTO.getUser());
        reservation.setFinalPrice(reservationDTO.getFinalPrice());

        List<Accommodation> accommodationList = reservationDTO.getAccommodationListDTO()
                .stream()
                .map(accommodationDTO -> accommodationService.transformToEntity(accommodationDTO, reservation))
                .collect(Collectors.toList());

        List<ReservationRemarks> reservationRemarksList = reservationDTO.getReservationRemarks()
                .stream()
                .map(reservationRemarksDTO -> reservationRemarksService.transformToEntity(reservationRemarksDTO, reservation))
                .collect(Collectors.toList());

        List<ExternalService> externalServiceList = reservationDTO.getExternalServiceDTOS()
                .stream()
                .map(externalServiceDTO -> externalServiceService.transformToEntity(externalServiceDTO, reservation))
                .collect(Collectors.toList());


        //accommodationList.forEach(accommodationService::add);
        reservation.setAccommodationList(accommodationList);
        reservation.setReservationRemarks(reservationRemarksList);
        reservation.setExternalServiceList(externalServiceList);
        //reservationRemarksList.forEach(reservationRemarksService::add);

        return reservation;
    }

    @Override
    public Boolean delete(Long aLong) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(aLong);
        if(reservationOptional.isPresent()){
            reservationRepository.delete(reservationOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean validate(ReservationDTO reservationDTO) {
        return reservationDTO != null && reservationDTO.getAccommodationListDTO() != null
                && !reservationDTO.getAccommodationListDTO().isEmpty() && reservationDTO.getFinalPrice() != null &&
                    reservationDTO.getUser() != null;
    }

    private boolean validate(Reservation reservation){
        return reservation != null && reservation.getAccommodationList() != null
                && !reservation.getAccommodationList().isEmpty() && reservation.getFinalPrice() != null &&
                reservation.getUser() != null;
    }

    private boolean validateReservationRequest(ReservationRequest reservationRequest){
        return reservationRequest != null && reservationRequest.getAccommodationRequestList() != null;
    }
}


