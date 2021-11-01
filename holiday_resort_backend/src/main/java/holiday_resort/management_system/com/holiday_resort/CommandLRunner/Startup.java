package holiday_resort.management_system.com.holiday_resort.CommandLRunner;


import holiday_resort.management_system.com.holiday_resort.Controllers.AuthController;
import holiday_resort.management_system.com.holiday_resort.Dto.RegisterRequest;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginUserRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Startup implements CommandLineRunner {

    private final LoginUserRepository loginUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthController authController;

    @Autowired
    public Startup(UserRepository _userRepository,
                   LoginUserRepository _loginUserRepository,
                   AuthController _authController,
                   @Lazy PasswordEncoder _passwordEncoder){

        userRepository = _userRepository;
        loginUserRepository = _loginUserRepository;
        authController = _authController;
        passwordEncoder = _passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("mckmus@gmail.com");
        registerRequest.setFirstName("Maciej");
        registerRequest.setLastName("Musial");
        registerRequest.setPhoneNumber("666666666");

        registerRequest.setUsername("123");
        registerRequest.setPassword("123");

        authController.registerUser(registerRequest);
    }
}
