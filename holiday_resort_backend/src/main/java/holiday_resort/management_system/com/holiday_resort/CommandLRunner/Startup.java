package holiday_resort.management_system.com.holiday_resort.CommandLRunner;


import holiday_resort.management_system.com.holiday_resort.Controllers.AuthController;
import holiday_resort.management_system.com.holiday_resort.Dto.ResortObjectDTO;
import holiday_resort.management_system.com.holiday_resort.Emails.GmailMailService;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.ResortObject;
import holiday_resort.management_system.com.holiday_resort.Repositories.AccommodationRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginDetailsRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.ResortObjectRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.*;
import holiday_resort.management_system.com.holiday_resort.Services.ReservationService;
import holiday_resort.management_system.com.holiday_resort.Services.ResortObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Component
public class Startup implements CommandLineRunner {

    private final LoginDetailsRepository loginDetailsRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AccommodationRepository accommodationRepository;
    private final AuthController authController;
    private final ReservationService reservationService;

    private final GmailMailService gmailMailService;
    private final ResortObjectService resortObjectService;

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
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

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

        resortObjectService.add(resortObjectDTO);
    }

    private void setReservation(LoginDetails loginDetails){

        ReservationRequest reservationRequest = new ReservationRequest();

        setResortObjectService("Domek letniskowy", "Dom", true, 11l, BigDecimal.ONE, BigDecimal.TEN);
        setResortObjectService("Namiot XXL", "Namiot", false, 43l, BigDecimal.ONE, BigDecimal.TEN);
        setResortObjectService("Domek", "Dom", true, 11l, BigDecimal.ONE, BigDecimal.TEN);
        setResortObjectService("Maly namiot", "Namiot", false, 55l, BigDecimal.ONE, BigDecimal.TEN);

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

        reservationRequest.setAccommodationRequestList(List.of(accommodationRequest, accommodationRequest1, accommodationRequest2, accommodationRequest3));
        reservationRequest.setReservationRemarksRequestList(Collections.emptyList());

        reservationService.setReservation(loginDetails, reservationRequest);

    }
}
