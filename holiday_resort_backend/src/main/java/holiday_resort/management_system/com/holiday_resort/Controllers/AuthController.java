package holiday_resort.management_system.com.holiday_resort.Controllers;

import holiday_resort.management_system.com.holiday_resort.Configuration.JwtUtils;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Enums.RoleTypes;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginDetailsRepository;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.JwtResponse;
import holiday_resort.management_system.com.holiday_resort.Requests.LoginRequest;
import holiday_resort.management_system.com.holiday_resort.Requests.RegisterRequest;
import holiday_resort.management_system.com.holiday_resort.Services.RoleService;
import holiday_resort.management_system.com.holiday_resort.Services.LoginDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags="[ALL] Authentication controller")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final LoginDetailsService loginDetailsService;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;
    private final LoginDetailsRepository loginDetailsRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                   LoginDetailsService loginDetailsService,
                   RoleService roleService,
                   JwtUtils jwtUtils,
                   LoginDetailsRepository loginDetailsRepository,
                   UserRepository userRepository
    ){
        this.authenticationManager = authenticationManager;
        this.loginDetailsService = loginDetailsService;
        this.roleService = roleService;
        this.jwtUtils = jwtUtils;
        this.loginDetailsRepository = loginDetailsRepository;
        this.userRepository = userRepository;

    }

    @PostMapping("/sign-in")
    @ApiOperation(value = "sign-in", response = ResponseEntity.class)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        LoginDetails userDetails = (LoginDetails) authentication.getPrincipal();
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
    @ApiOperation(value = "sign-up", response = ResponseEntity.class)
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest){

        LoginDetails signUpUser = new LoginDetails(registerRequest);

        if (loginDetailsRepository.existsByUsername(signUpUser.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        if (userRepository.existsByEmail(signUpUser.getUser().getEmail())){
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        roleService.assignRolesAndOverride(signUpUser, RoleTypes.USER, RoleTypes.ADMIN);
        loginDetailsService.saveUserAndUserLoginObject(signUpUser);

        return ResponseEntity.ok().build();
    }

}
