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

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


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

    /*
        ServiceRequestDTO serviceRequestDTO = ServiceRequestDTO.builder()
                .serviceName("Kajaki")
                .isNumberOfPeopleIrrelevant(false)
                .cost(new BigDecimal("25.99"))
                .build();

        ServiceRequestDTO serviceRequestDTO2 = ServiceRequestDTO.builder()
                .serviceName("Ognisko")
                .isNumberOfPeopleIrrelevant(true)
                .cost(new BigDecimal("30.29"))
                .build();

        ServiceRequestDTO serviceRequestDTO3 = ServiceRequestDTO.builder()
                .serviceName("Obiad")
                .isNumberOfPeopleIrrelevant(false)
                .cost(new BigDecimal("20.50"))
                .build();

        ServiceRequestDTO serviceRequestDTO4 = ServiceRequestDTO.builder()
                .serviceName("Kolacja")
                .isNumberOfPeopleIrrelevant(false)
                .cost(new BigDecimal("10.50"))
                .build();

        ServiceRequestDTO serviceRequestDTO5 = ServiceRequestDTO.builder()
                .serviceName("Sniadanie")
                .isNumberOfPeopleIrrelevant(false)
                .cost(new BigDecimal("7.50"))
                .build();

        serviceRequestService.add(serviceRequestDTO);
        serviceRequestService.add(serviceRequestDTO2);
        serviceRequestService.add(serviceRequestDTO3);
        serviceRequestService.add(serviceRequestDTO4);
        serviceRequestService.add(serviceRequestDTO5);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("mckmusial@gmail.com");
        registerRequest.setFirstName("Maciej");
        registerRequest.setLastName("Musial");
        registerRequest.setPhoneNumber("+48666666666");

        registerRequest.setUsername("Maciej12345");
        registerRequest.setPassword("Maciej12345");

        authController.registerUser(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Maciej12345");
        loginRequest.setPassword("Maciej12345");


        setReservations(loginRequest);

     */
    }


    private void setReservations(LoginRequest loginRequest) {

        ResponseEntity<JwtResponse> response = authController.authenticateUser(loginRequest);
        String username = response.getBody().getUsername();

        Optional<LoginDetails> optUser = loginDetailsRepository.findByUsername(username);

        optUser.ifPresent(loginDetails -> {
            try {
                setReservation(loginDetails);
                setReservation(loginDetails);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setResortObjectService(String objectName, String objectType, Boolean isReserved, Long maxAmountOfPeople,
                                        BigDecimal pricePerPerson, BigDecimal pricePerUnusedSpace) throws IOException {

//        File file = ResourceUtils.getFile("C:\\Users\\mckmu\\holiday_resort_app\\holiday_resort_backend\\src\\main\\resources\\static\\tent.jpg");
//        InputStream in = new FileInputStream(file);

        ResortObjectDTO resortObjectDTO = ResortObjectDTO.builder()
                .objectName(objectName)
                .objectType(objectType)
                .isReserved(isReserved)
                .maxAmountOfPeople(maxAmountOfPeople)
                .pricePerPerson(pricePerPerson)
                .unusedSpacePrice(pricePerUnusedSpace)
                //.photo(in.readAllBytes())
                .build();

        EventDTO eventDTO = EventDTO.builder()
                .eventType(EventEnum.BUTLA_GAZOWA)
                .durationDate(LocalDate.now())
                .startingDate(LocalDate.now())
                .priority(3)
                .price(new BigDecimal("50.50"))
                .build();

        EventDTO eventDTO2 = EventDTO.builder()
                .eventType(EventEnum.PRĄD)
                .durationDate(LocalDate.now())
                .startingDate(LocalDate.now())
                .priority(3)
                .price(new BigDecimal("25.50"))
                .build();

        EventDTO eventDTO3 = EventDTO.builder()
                .eventType(EventEnum.WODA)
                .durationDate(LocalDate.now())
                .startingDate(LocalDate.now())
                .priority(3)
                .price(new BigDecimal("35.50"))
                .build();

        resortObjectController.postResortObject(resortObjectDTO, List.of(eventDTO, eventDTO2, eventDTO3));
    }

    private void setReservation(LoginDetails loginDetails) throws IOException {

        ReservationRequest reservationRequest = new ReservationRequest();

        ReservationOwnerRequest reservationOwnerRequest = ReservationOwnerRequest.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .phoneNumber("+666666666")
                .build();

        reservationRequest.setReservationOwnerRequest(reservationOwnerRequest);

        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();

        List<ServiceRequestDTO> externalServiceDTOS = externalServiceController.getAvailableServices().getBody();


        setResortObjectService(String.format("Domek letniskowy-%s",new Random().nextInt(100)), "Dom", true, 9l, new BigDecimal("25.99"), new BigDecimal("0.90"));
        setResortObjectService(String.format("Duzy namiot grupowy-%s", new Random().nextInt(100)), "Namiot", true, 15l, new BigDecimal("50.99"), new BigDecimal("0.95"));
        setResortObjectService(String.format("Domek letniskowy-%s", new Random().nextInt(100)), "Dom", true, 8l, new BigDecimal("23.99"), new BigDecimal("0.85"));
        setResortObjectService(String.format("Maly namiot nauczycielski-%s", new Random().nextInt(100)) , "Namiot", true, 3l, new BigDecimal("15.99"), new BigDecimal("0.90"));

        List<ResortObject> resortObjectList = resortObjectRepository.findAll();

        AccommodationRequest accommodationRequest = new AccommodationRequest();
        accommodationRequest.setNumberOfPeople((long) new Random().nextInt(resortObjectList.get(0).getMaxAmountOfPeople().intValue()));
        accommodationRequest.setResortObjectId(resortObjectList.get(0).getId());

        AccommodationRequest accommodationRequest1 = new AccommodationRequest();
        accommodationRequest1.setNumberOfPeople((long) new Random().nextInt(resortObjectList.get(1).getMaxAmountOfPeople().intValue()));
        accommodationRequest1.setResortObjectId(resortObjectList.get(1).getId());

        AccommodationRequest accommodationRequest2 = new AccommodationRequest();
        accommodationRequest2.setNumberOfPeople((long) new Random().nextInt(resortObjectList.get(2).getMaxAmountOfPeople().intValue()));
        accommodationRequest2.setResortObjectId(resortObjectList.get(2).getId());

        AccommodationRequest accommodationRequest3 = new AccommodationRequest();
        accommodationRequest3.setNumberOfPeople((long) new Random().nextInt(resortObjectList.get(3).getMaxAmountOfPeople().intValue()));
        accommodationRequest3.setResortObjectId(resortObjectList.get(3).getId());

        setResortObjectService(String.format("Duzy namiot grupowy-%s", new Random().nextInt(100)), "Namiot", false, 15l, new BigDecimal("50.99"), new BigDecimal("0.80"));
        setResortObjectService(String.format("Duzy namiot grupowy-%s", new Random().nextInt(100)), "Namiot", false, 16l, new BigDecimal("53.99"), new BigDecimal("0.87"));
        setResortObjectService(String.format("Duzy namiot grupowy-%s", new Random().nextInt(100)), "Namiot", false, 17l, new BigDecimal("56.99"), new BigDecimal("0.86"));

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
                externalServicesRequest.setAmountOfPeople((long) new Random().nextInt(20));
                externalServicesRequest.setRemarks("");
                externalServicesRequest.setDate(LocalDate.now());

            externalServicesRequests.add(externalServicesRequest);
        });


        reservationRequest.setReservationRemarksRequestList(List.of(reservationRemarksRequest, reservationRemarksRequest2));
        reservationRequest.setReservationStartingDate(LocalDate.now());
        reservationRequest.setReservationEndingDate(LocalDate.now());
        reservationRequest.setReservationName("MOJA-REZERWACJA" + new Random().nextInt(50));
        reservationRequest.setExternalServicesRequests(externalServicesRequests);


        reservationService.setReservation(loginDetails, reservationRequest);

    }
}
