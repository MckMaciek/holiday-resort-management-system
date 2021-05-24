package holiday_resort.management_system.com.holiday_resort.CommandLRunner;

import holiday_resort.management_system.com.holiday_resort.Entities.LoginUser;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Enums.Roles;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginUserRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import org.h2.engine.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class Startup implements CommandLineRunner {

    private final LoginUserRepository loginUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Startup(UserRepository _userRepository, LoginUserRepository _loginUserRepository, PasswordEncoder _passwordEncoder){
        userRepository = _userRepository;
        loginUserRepository = _loginUserRepository;
        passwordEncoder = _passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUNNING");

//        User user  = new User();
//            user.setEmail("testowe123@gmail.com");
//            user.setFirstName("test123");
//            user.setLastName("test123");
//            user.setUserCreationDate(LocalDateTime.now());
//
//            userRepository.save(user);
//
//        LoginUser loginUser = new LoginUser();
//            loginUser.setUser(user);
//            loginUser.setEnabled(Boolean.TRUE);
//            loginUser.setUsername("test");
//            loginUser.setPassword(passwordEncoder.encode("test"));
//            loginUser.setRole(Roles.USER);
//
//            loginUserRepository.save(loginUser);
    }
}
