package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Controllers.Exceptions.UserControllerExceptions;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginDetailsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoginDetailsService implements UserDetailsService, CrudOperations<LoginDetails, Long>, Validate<LoginDetails> {

    private final LoginDetailsRepository loginDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    private static final Logger logger = LogManager.getLogger(UserControllerExceptions.class);

    @Autowired
    public LoginDetailsService(LoginDetailsRepository _loginDetailsRepository,
                               @Lazy PasswordEncoder _passwordEncoder,
                               UserService _userService,
                               RoleService roleService)
    {

        this.passwordEncoder = _passwordEncoder;
        this.loginDetailsRepository = _loginDetailsRepository;
        this.userService = _userService;
        this.roleService = roleService;
    }

    @Transactional
    public void saveUserAndUserLoginObject(LoginDetails loginDetails){
        User user = loginDetails.getUser();
        user.setLoginDetails(loginDetails);

        userService.add(user);
        this.add(loginDetails);
        roleService.saveRole(loginDetails.getRoles());

        System.out.println("[DEBUG] " + loginDetails.getAuthorities());
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        LoginDetails loginDetails = loginDetailsRepository.findByUsername(username)
                .orElseThrow(() -> {throw new UsernameNotFoundException(username);});

        System.out.println("[DEBUG-USER-FOUND]-> " + loginDetails);
        return loginDetails;
    }

    @Override
    public List<LoginDetails> getAll() {
        return loginDetailsRepository.findAll();
    }

    @Override
    public Optional<LoginDetails> findById(Long aLong) {
        return loginDetailsRepository.findById(aLong);
    }

    @Override
    public void add(LoginDetails loginDetails) {

        loginDetails.setPassword(passwordEncoder.encode(loginDetails.getPassword()));
        loginDetailsRepository.save(loginDetails);
    }

    @Override
    public Boolean delete(Long aLong) {

        try {
            loginDetailsRepository.deleteById(aLong);
        }catch (org.springframework.dao.EmptyResultDataAccessException exc){
            return false;
        }

        return true;
    }

    public Boolean checkIntegrityOfData(LoginDetails loginDetails){
        if(Objects.nonNull(loginDetails) && validate(loginDetails)){

            User userFromJSON = loginDetails.getUser();

            if(userService.validate(userFromJSON)) return true;
            return false;
        }
        return false;
    }

    @Override
    public boolean validate(LoginDetails loginDetails) {
        boolean userValidated = true;

        if(Objects.isNull(loginDetails.getUser())) userValidated = false;
        if(Objects.isNull(loginDetails.getUsername())) userValidated = false;
        if(Objects.isNull(loginDetails.getPassword())) userValidated = false;

        return userValidated;
    }
}
