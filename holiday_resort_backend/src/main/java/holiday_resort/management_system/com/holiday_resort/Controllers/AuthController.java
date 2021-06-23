package holiday_resort.management_system.com.holiday_resort.Controllers;

import holiday_resort.management_system.com.holiday_resort.Configuration.JwtUtils;
import holiday_resort.management_system.com.holiday_resort.Entities.JwtResponse;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginRequest;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginUser;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginUserRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import holiday_resort.management_system.com.holiday_resort.Services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserLoginService userLoginService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final LoginUserRepository loginUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                   UserLoginService userLoginService,
                   PasswordEncoder passwordEncoder,
                   JwtUtils jwtUtils,
                   LoginUserRepository loginUserRepository,
                   UserRepository userRepository){
        this.authenticationManager = authenticationManager;
        this.userLoginService = userLoginService;
        this.encoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.loginUserRepository = loginUserRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        LoginUser userDetails = (LoginUser) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getUser().getEmail(),
                roles));
    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody LoginUser signUpUser){
        if (loginUserRepository.existsByUsername(signUpUser.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        if (userRepository.existsByEmail(signUpUser.getUser().getEmail())){
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        LoginUser loginUser = LoginUser.getInstanceOfBuilder()
                .setUser(signUpUser.getUser())
                .setPassword(signUpUser.getPassword())
                .setUsername(signUpUser.getUsername())
                .build();

        userLoginService.saveUserAndUserLoginObject(loginUser);

        return ResponseEntity.ok().build();
    }

}