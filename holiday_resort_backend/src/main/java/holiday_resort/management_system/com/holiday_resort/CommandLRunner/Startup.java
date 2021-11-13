package holiday_resort.management_system.com.holiday_resort.CommandLRunner;


import holiday_resort.management_system.com.holiday_resort.Controllers.AuthController;
import holiday_resort.management_system.com.holiday_resort.Emails.GmailMailService;
import holiday_resort.management_system.com.holiday_resort.Repositories.AccommodationRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginDetailsRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.JwtResponse;
import holiday_resort.management_system.com.holiday_resort.Requests.LoginRequest;
import holiday_resort.management_system.com.holiday_resort.Requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class Startup implements CommandLineRunner {

    private final LoginDetailsRepository loginDetailsRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AccommodationRepository accommodationRepository;
    private final AuthController authController;

    private final GmailMailService gmailMailService;
    @Autowired
    public Startup(UserRepository _userRepository,
                   LoginDetailsRepository _loginDetailsRepository,
                   AuthController _authController,
                   AccommodationRepository accommodationRepository,
                   GmailMailService gmailMailService,
                   @Lazy PasswordEncoder _passwordEncoder){

        this.userRepository = _userRepository;
        this.loginDetailsRepository = _loginDetailsRepository;
        this.authController = _authController;
        this.passwordEncoder = _passwordEncoder;
        this.accommodationRepository = accommodationRepository;
        this.gmailMailService = gmailMailService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("mckmus@gmail.com");
        registerRequest.setFirstName("Maciej");
        registerRequest.setLastName("Musial");
        registerRequest.setPhoneNumber("666666666");

        registerRequest.setUsername("123");
        registerRequest.setPassword("123");

        authController.registerUser(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerRequest.getUsername());
        loginRequest.setPassword(registerRequest.getPassword());

        ResponseEntity<JwtResponse> responseEntity = authController.authenticateUser(loginRequest);


        boolean flag = gmailMailService.sendMessage(
                "mckmusial@gmail.com", "dsadsadsadsa!", "dsadsadsads"
        );

        System.out.println(flag);
    }
}
