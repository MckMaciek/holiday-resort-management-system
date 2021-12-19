package holiday_resort.management_system.com.holiday_resort.CommandLRunner;


import holiday_resort.management_system.com.holiday_resort.Controllers.AccommodationController;
import holiday_resort.management_system.com.holiday_resort.Controllers.AuthController;
import holiday_resort.management_system.com.holiday_resort.Controllers.ExternalServiceController;
import holiday_resort.management_system.com.holiday_resort.Controllers.ResortObjectController;
import holiday_resort.management_system.com.holiday_resort.Dto.EventDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.ResortObjectDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.ServiceRequestDTO;
import holiday_resort.management_system.com.holiday_resort.Emails.GmailMailService;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.ResortObject;
import holiday_resort.management_system.com.holiday_resort.Entities.ServiceRequest;
import holiday_resort.management_system.com.holiday_resort.Enums.EventEnum;
import holiday_resort.management_system.com.holiday_resort.Repositories.*;
import holiday_resort.management_system.com.holiday_resort.Requests.*;
import holiday_resort.management_system.com.holiday_resort.Responses.JwtResponse;
import holiday_resort.management_system.com.holiday_resort.Services.ReservationService;
import holiday_resort.management_system.com.holiday_resort.Services.ResortObjectService;
import holiday_resort.management_system.com.holiday_resort.Services.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Component
public class Startup implements CommandLineRunner {

    private final LoginDetailsRepository loginDetailsRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AccommodationRepository accommodationRepository;
    private final AuthController authController;
    private final ResortObjectController resortObjectController;
    private final AccommodationController accommodationController;
    private final ExternalServiceController externalServiceController;

    private final ReservationService reservationService;

    private final GmailMailService gmailMailService;
    private final ResortObjectService resortObjectService;
    private final ServiceRequestRepository serviceRequestRepository;

    private final ServiceRequestService serviceRequestService;

    private final ResortObjectRepository resortObjectRepository;

    @Autowired
    public Startup(UserRepository _userRepository,
                   LoginDetailsRepository _loginDetailsRepository,
                   AuthController _authController,
                   AccommodationRepository accommodationRepository,
                   GmailMailService gmailMailService,
                   ReservationService reservationService,
                   ResortObjectService resortObjectService,
                   ResortObjectRepository resortObjectRepository,
                   ResortObjectController resortObjectController,
                   AccommodationController accommodationController,
                   ExternalServiceController externalServiceController,
                   ServiceRequestService serviceRequestService,
                   ServiceRequestRepository serviceRequestRepository,
                   @Lazy PasswordEncoder _passwordEncoder) {

        this.userRepository = _userRepository;
        this.loginDetailsRepository = _loginDetailsRepository;
        this.authController = _authController;
        this.passwordEncoder = _passwordEncoder;
        this.accommodationRepository = accommodationRepository;
        this.gmailMailService = gmailMailService;
        this.reservationService = reservationService;
        this.resortObjectService = resortObjectService;
        this.resortObjectRepository = resortObjectRepository;
        this.resortObjectController = resortObjectController;
        this.accommodationController = accommodationController;
        this.externalServiceController = externalServiceController;
        this.serviceRequestService = serviceRequestService;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {


        ServiceRequestDTO serviceRequestDTO = ServiceRequestDTO.builder()
                .serviceName("Kajaki")
                .cost(BigDecimal.TEN)
                .build();

        ServiceRequestDTO serviceRequestDTO2 = ServiceRequestDTO.builder()
                .serviceName("Ognisko")
                .cost(BigDecimal.TEN)
                .build();

        serviceRequestService.add(serviceRequestDTO);
        serviceRequestService.add(serviceRequestDTO2);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("mckmusial@gmail.com");
        registerRequest.setFirstName("Maciej");
        registerRequest.setLastName("Musial");
        registerRequest.setPhoneNumber("+48666666666");

        registerRequest.setUsername("MckMaciek");
        registerRequest.setPassword("Koteczek12");

        authController.registerUser(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("MckMaciek");
        loginRequest.setPassword("Koteczek12");


        setReservations(loginRequest);
    }


    private void setReservations(LoginRequest loginRequest) {

        ResponseEntity<JwtResponse> response = authController.authenticateUser(loginRequest);
        String username = response.getBody().getUsername();

        Optional<LoginDetails> optUser = loginDetailsRepository.findByUsername(username);

        optUser.ifPresent(loginDetails -> {
            setReservation(loginDetails);
            setReservation(loginDetails);
        });
    }

    private void setResortObjectService(String objectName, String objectType, Boolean isReserved, Long maxAmountOfPeople,
                                        BigDecimal pricePerPerson, BigDecimal pricePerUnusedSpace){

        ResortObjectDTO resortObjectDTO = ResortObjectDTO.builder()
                .objectName(objectName)
                .objectType(objectType)
                .isReserved(isReserved)
                .maxAmountOfPeople(maxAmountOfPeople)
                .pricePerPerson(pricePerPerson)
                .unusedSpacePrice(pricePerUnusedSpace)
                .build();

        EventDTO eventDTO = EventDTO.builder()
                .eventType(EventEnum.BUTLA_GAZOWA)
                .durationDate(LocalDateTime.now())
                .startingDate(LocalDateTime.now())
                .priority(3)
                .price(BigDecimal.TEN)
                .build();

        EventDTO eventDTO2 = EventDTO.builder()
                .eventType(EventEnum.PRĄD)
                .durationDate(LocalDateTime.now())
                .startingDate(LocalDateTime.now())
                .priority(3)
                .price(BigDecimal.TEN)
                .build();

        resortObjectController.postResortObject(resortObjectDTO, List.of(eventDTO, eventDTO2));
    }

    private void setReservation(LoginDetails loginDetails){

        ReservationRequest reservationRequest = new ReservationRequest();

        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();

        List<ServiceRequestDTO> externalServiceDTOS = externalServiceController.getAvailableServices().getBody();


        setResortObjectService("Domek letniskowy", "Dom", true, 11l, BigDecimal.ONE, BigDecimal.TEN);
        setResortObjectService("Namiot XXL", "Namiot", true, 43l, BigDecimal.ONE, BigDecimal.TEN);
        setResortObjectService("Domek", "Dom", true, 11l, BigDecimal.ONE, BigDecimal.TEN);
        setResortObjectService("Maly namiot", "Namiot", true, 55l, BigDecimal.ONE, BigDecimal.TEN);

        List<ResortObject> resortObjectList = resortObjectRepository.findAll();

        AccommodationRequest accommodationRequest = new AccommodationRequest();
        accommodationRequest.setNumberOfPeople(11l);
        accommodationRequest.setResortObjectId(resortObjectList.get(0).getId());

        AccommodationRequest accommodationRequest1 = new AccommodationRequest();
        accommodationRequest1.setNumberOfPeople(43l);
        accommodationRequest1.setResortObjectId(resortObjectList.get(1).getId());

        AccommodationRequest accommodationRequest2 = new AccommodationRequest();
        accommodationRequest2.setNumberOfPeople(55l);
        accommodationRequest2.setResortObjectId(resortObjectList.get(2).getId());

        AccommodationRequest accommodationRequest3 = new AccommodationRequest();
        accommodationRequest3.setNumberOfPeople(11l);
        accommodationRequest3.setResortObjectId(resortObjectList.get(3).getId());

        setResortObjectService("NIEZAREZERWOWANE1", "Namiot", false, 33l, BigDecimal.ONE, BigDecimal.TEN);
        setResortObjectService("NIEZAREZERWOWANE2", "Namiot", false, 5l, BigDecimal.ONE, BigDecimal.TEN);
        setResortObjectService("NIEZAREZERWOWANE3", "Namiot", false, 22l, BigDecimal.ONE, BigDecimal.TEN);

        reservationRequest.setAccommodationRequestList(List.of(accommodationRequest, accommodationRequest1, accommodationRequest2, accommodationRequest3));

        ReservationRemarksRequest reservationRemarksRequest = ReservationRemarksRequest.builder()
                .topic("1WAZNY_TEMAT")
                .description("1WAZNY_OPIS")
                .build();

        ReservationRemarksRequest reservationRemarksRequest2 = ReservationRemarksRequest.builder()
                .topic("2WAZNY_TEMAT")
                .description("2WAZNY_OPIS")
                .build();

        List<ExternalServicesRequest> externalServicesRequests = new ArrayList<>();

        externalServiceDTOS.forEach(externalServiceDTOS1 -> {
            ExternalServicesRequest externalServicesRequest = new ExternalServicesRequest();
            Long id = externalServiceDTOS1.getId();
            externalServicesRequest.setServiceRequestId(id);
            externalServicesRequest.setAmountOfPeople(11l);
            externalServicesRequest.setRemarks("");

            externalServicesRequests.add(externalServicesRequest);
        });


        reservationRequest.setReservationRemarksRequestList(List.of(reservationRemarksRequest, reservationRemarksRequest2));
        reservationRequest.setReservationStartDate(LocalDateTime.now());
        reservationRequest.setReservationEndingDate(LocalDateTime.now().plus(5, ChronoUnit.DAYS));
        reservationRequest.setReservationName("MOJA-REZERWACJA" + new Random().nextInt(50));
        reservationRequest.setExternalServicesRequests(externalServicesRequests);


        reservationService.setReservation(loginDetails, reservationRequest);

    }
}
