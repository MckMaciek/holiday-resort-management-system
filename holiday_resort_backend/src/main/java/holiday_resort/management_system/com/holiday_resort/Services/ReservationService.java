package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Dto.*;
import holiday_resort.management_system.com.holiday_resort.Entities.*;
import holiday_resort.management_system.com.holiday_resort.Enums.ReservationStatus;
import holiday_resort.management_system.com.holiday_resort.Enums.RoleTypes;
import holiday_resort.management_system.com.holiday_resort.Events.ReservationStatusChangedEvent;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationOwnerRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationOwnerRequest;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationRemarksRequest;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationRequest;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReservationService implements CrudOperations<ReservationDTO, Long>, Validate<ReservationDTO> {

    private final ReservationRepository reservationRepository;

    private final GenericAction<Reservation, ReservationRepository> reservationContext;

    private final AccommodationService accommodationService;
    private final ReservationRemarksService reservationRemarksService;
    private final ExternalServiceService externalServiceService;
    private final PriceService priceService;
    private final ReservationOwnerRepository reservationOwnerRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final static String SYSTEM_AUTHOR = "SYSTEM";
    private final static String STATUS_CHANGED = "Status changed";


    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              AccommodationService accommodationService,
                              PriceService priceService,
                              ReservationRemarksService reservationRemarksService,
                              ExternalServiceService externalServiceService,
                              GenericAction<Reservation, ReservationRepository> reservationContext,
                              ReservationOwnerRepository reservationOwnerRepository,
                              ApplicationEventPublisher applicationEventPublisher
    ){
        this.reservationRepository = reservationRepository;
        this.accommodationService = accommodationService;
        this.priceService = priceService;
        this.reservationRemarksService = reservationRemarksService;
        this.reservationContext = reservationContext;
        this.externalServiceService = externalServiceService;
        this.reservationOwnerRepository = reservationOwnerRepository;
        this.applicationEventPublisher = applicationEventPublisher;
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

        ReservationRemarksRequest welcomingMessage = ReservationRemarksRequest.builder()
                .creationDate(Date.from(Instant.now()))
                .topic("Welcome!")
                .description("Thank You for Your reservation!")
                .build();

        ReservationRemarksRequest reservationRemarksRequest = ReservationRemarksRequest.builder()
                .creationDate(Date.from(Instant.now()))
                .topic("Status change")
                .description("Changed status to DRAFT")
                .build();

        reservationReq.setReservationRemarksRequestList(List.of(welcomingMessage, reservationRemarksRequest));

        List<ReservationRemarksDTO> reservationRemarksDTOS = reservationReq.getReservationRemarksRequestList()
                .stream()
                .map(reservationRemarksService::convertRequestToDTO)
                .collect(Collectors.toList());

        ReservationOwnerRequest reservationOwnerRequest = reservationReq.getReservationOwnerRequest();

        ReservationDTO reservationDTO = ReservationDTO.builder()
                .accommodationListDTO(accommodationDTOS)
                .reservationName(reservationReq.getReservationName())
                .reservationEndingDate(reservationReq.getReservationEndingDate().plus(1, ChronoUnit.DAYS))
                .creationDate(LocalDate.now())
                .reservationStatus(ReservationStatus.DRAFT)
                .reservationDate(reservationReq.getReservationStartingDate().plus(1, ChronoUnit.DAYS))
                .externalServiceDTOS(externalServiceDTOS)
                .reservationRemarks(reservationRemarksDTOS)
                .reservationOwnerDTO(new ReservationOwnerDTO(reservationOwnerRequest))
                .description(this.setDescription(reservationReq.getDescription()))
                .user(loginDetails.getUser())
                .description(this.setDescription(reservationReq.getDescription()))
                .build();


        this.add(reservationDTO);
    }


    @Transactional
    public void patchReservation(LoginDetails loginDetails, ReservationRequest reservationReq, Long reservationId){

        if(!validateReservationRequest(reservationReq)) throw new IllegalArgumentException("Invalid Reservation Request!");
        if(Objects.isNull(loginDetails.getUser())) throw new NullPointerException("User cannot be null!");

        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if(reservationOpt.isEmpty()) throw new NullPointerException(String.format("Reservation with the id of %s not found", reservationId));

        List<ExternalServiceDTO> externalServiceDTOS = reservationReq.getExternalServicesRequests()
                .stream()
                .map(externalServiceService::convertRequestToDTO)
                .collect(Collectors.toList());

        List<AccommodationDTO> accommodationDTOS =  reservationReq.getAccommodationRequestList()
                .stream()
                .map(accommodationService::convertRequestToDTO)
                .collect(Collectors.toList());

        accommodationDTOS.forEach(accommodationDTO -> accommodationDTO.setUser(loginDetails.getUser()));

        ReservationOwnerRequest reservationOwnerRequest = reservationReq.getReservationOwnerRequest();

        Reservation userReservation = reservationOpt.get();

        if (!reservationContext.checkIfOwnerAndUserRequestAreSame(userReservation.getLinkedLoginDetails(), loginDetails)){
            throw new IllegalArgumentException(
                    String.format("Reservation owner - %s and username in request - %s do not match!",
                            userReservation.getLinkedLoginDetails().getUsername(), loginDetails.getUsername()));
        }

        ReservationDTO userReservationDTO = new ReservationDTO(userReservation);

        userReservationDTO.setReservationDate(reservationReq.getReservationStartingDate().plus(1, ChronoUnit.DAYS));
        userReservationDTO.setReservationEndingDate(reservationReq.getReservationEndingDate().plus(1, ChronoUnit.DAYS));
        userReservationDTO.setReservationOwnerDTO(new ReservationOwnerDTO(reservationOwnerRequest));
        userReservationDTO.setReservationName(reservationReq.getReservationName());
        userReservationDTO.setUser(loginDetails.getUser());
        userReservationDTO.setDescription(this.setDescription(reservationReq.getDescription()));
        userReservationDTO.setId(reservationId);

        List<AccommodationDTO> existingAccommodationsDTO = userReservationDTO.getAccommodationListDTO();
        List<AccommodationDTO> unionList = Stream.concat(existingAccommodationsDTO.stream(), accommodationDTOS.stream()).distinct()
                .collect(Collectors.toList());

        userReservationDTO.setAccommodationListDTO(unionList);

        userReservationDTO.setExternalServiceDTOS(externalServiceDTOS);

        this.modify(userReservationDTO);
    }

    @Transactional
    public void deleteReservation(LoginDetails loginDetails, Long reservationId){
        if(reservationId == null) throw new NullPointerException("Invalid reservationId parameter");

        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if(reservationOpt.isEmpty()) throw new IllegalArgumentException(String.format("Reservation with id of %s not found", reservationId));

        Reservation reservation = reservationOpt.get();

        if (!reservationContext.checkIfOwnerAndUserRequestAreSame(reservation.getLinkedLoginDetails(), loginDetails)){
            throw new IllegalArgumentException(
                    String.format("Reservation owner - %s and username in request - %s do not match!",
                            reservation.getLinkedLoginDetails().getUsername(), loginDetails.getUsername()));
        }

        reservation.getAccommodationList().forEach(accommodation -> {
            accommodation.getResortObject().setIsReserved(false);
        });

        reservationOwnerRepository.delete(reservation.getReservationOwner());
        reservation.setReservationOwner(null);

        List<Accommodation> accommodationList = reservation.getAccommodationList();
        if(!accommodationList.isEmpty()){
            accommodationList.forEach(accommodation -> {
                accommodation.setResortObject(null);
                accommodation.setUserEventList(null);
            });
        }

        reservationRepository.delete(reservation);
    }

    public List<ReservationDTO> getUserReservations(LoginDetails loginDetails){

        List<Reservation> reservationList = reservationRepository.findByUser(loginDetails.getUser());

        return reservationList
                    .stream()
                    .map(ReservationDTO::new)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
    }

    @Transactional
    public void changeReservationStatus(ReservationStatus reservationStatus, LoginDetails loginDetails, Long reservationId){

        Pair<LoginDetails, Reservation> reservationOwnerPair =
                reservationContext.getAssociatedUser(reservationRepository, reservationId);

        if (!reservationContext.checkIfOwnerAndUserRequestAreSame(reservationOwnerPair.getFirst(), loginDetails)){
            throw new IllegalArgumentException(
                    String.format("Reservation owner - %s and username in request - %s do not match!",
                            reservationOwnerPair.getFirst().getUsername(), loginDetails.getUsername()));
        }

        Reservation reservation = reservationOwnerPair.getSecond();
        ReservationStatus beforeStatus = reservation.getReservationStatus();

        if(!ReservationStatus.DRAFT.equals(reservation.getReservationStatus())){
            List<RoleTypes> userRoles = reservationOwnerPair.getFirst().getRoles().getRoleTypesList();

            if(userRoles.contains(RoleTypes.MANAGER) || userRoles.contains(RoleTypes.ADMIN)){
                reservation.setReservationStatus(reservationStatus);
                flushReservationRemarks(reservation, reservationStatus, loginDetails);

                applicationEventPublisher.publishEvent(new ReservationStatusChangedEvent(
                        new ReservationDTO(reservation),
                        beforeStatus
                ));
            }
            else throw new UnsupportedOperationException("User is not privileged enough to change status");

        }
        else if(ReservationStatus.DRAFT.equals(reservation.getReservationStatus())){
            reservation.setReservationStatus(reservationStatus);
            flushReservationRemarks(reservation, reservationStatus, loginDetails);

            applicationEventPublisher.publishEvent(new ReservationStatusChangedEvent(
                    new ReservationDTO(reservation),
                    beforeStatus
            ));
        }
    }

    private void flushReservationRemarks(Reservation reservation, ReservationStatus reservationStatus, LoginDetails loginDetails){

        ReservationRemarks reservationRemarks = ReservationRemarks.builder()
                .reservation(reservation)
                .author(SYSTEM_AUTHOR)
                .creationDate(LocalDate.now())
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
            reservation.setFinalPrice(priceService.calculateFinalPrice(reservation));
            reservationRepository.save(reservation);
        }
    }

    public void modify(ReservationDTO reservationDTO){

        if(reservationDTO.getId() == null) throw new NullPointerException("Cannot update null id reservation");

        Reservation reservation = transformToEntity(reservationDTO);
        reservation.setFinalPrice(priceService.calculateFinalPrice(reservation));

        reservationRepository.save(reservation);
    }

    private Reservation transformToEntity(ReservationDTO reservationDTO){
        Reservation reservation = new Reservation();

        reservation.setReservationStatus(reservationDTO.getReservationStatus());
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setReservationEndingDate(reservationDTO.getReservationEndingDate());
        reservation.setReservationName(reservationDTO.getReservationName());
        reservation.setCreationDate(reservationDTO.getCreationDate());
        reservation.setUser(reservationDTO.getUser());
        reservation.setDescription(reservationDTO.getDescription());
        reservation.setId(reservationDTO.getId());
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


        ReservationOwner reservationOwner = new ReservationOwner(reservationDTO.getReservationOwnerDTO());

        if(!validateReservationOwner(reservationOwner)) throw new IllegalArgumentException("Illegal reservation owner provided");
        if(reservationOwner.getId() != null) throw new IllegalArgumentException("Cannot insert entity with non null reservationOwner id");

        //reservationOwnerRepository.save(reservationOwner);
        //accommodationList.forEach(accommodationService::add);
        reservation.setAccommodationList(accommodationList);
        reservation.setReservationRemarks(reservationRemarksList);
        reservation.setExternalServiceList(externalServiceList);

        reservation.setReservationOwner(reservationOwner);
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
                && !reservationDTO.getAccommodationListDTO().isEmpty()  &&
                    reservationDTO.getUser() != null;
    }

    private boolean validate(Reservation reservation){
        return reservation != null && reservation.getAccommodationList() != null
                && !reservation.getAccommodationList().isEmpty() && reservation.getFinalPrice() != null &&
                reservation.getUser() != null;
    }

    private boolean validateReservationOwner(ReservationOwner reservationOwner){
        return  !StringUtils.isNullOrEmpty(reservationOwner.getFirstName()) ||
                !StringUtils.isNullOrEmpty(reservationOwner.getLastName())  ||
                !StringUtils.isNullOrEmpty(reservationOwner.getPhoneNumber());
    }

    private String setDescription(String desc){
        return StringUtils.isNullOrEmpty(desc) ? "" : desc;
    }

    private boolean validateReservationRequest(ReservationRequest reservationRequest){
        return reservationRequest != null && reservationRequest.getAccommodationRequestList() != null;
    }
}


