package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Controllers.Exceptions.UserControllerExceptions;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginUser;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginUserRepository;
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
public class UserLoginService implements UserDetailsService, CrudOperations<LoginUser, Long>, Validate<LoginUser> {

    private final LoginUserRepository loginUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private static final Logger logger = LogManager.getLogger(UserControllerExceptions.class);

    @Autowired
    public UserLoginService(LoginUserRepository _loginUserRepository,
                            @Lazy PasswordEncoder _passwordEncoder,
                            UserService _userService)
    {

        this.passwordEncoder = _passwordEncoder;
        this.loginUserRepository = _loginUserRepository;
        this.userService = _userService;
    }

    @Transactional
    public void saveUserAndUserLoginObject(LoginUser loginUser){
        User userFromJSON = loginUser.getUser();

        userService.add(userFromJSON);
        this.add(loginUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        LoginUser loginUser = loginUserRepository.findByUsername(username);
        System.out.println("[DEBUG] " + loginUser);

        if(loginUser == null){
            throw new UsernameNotFoundException(username);
        }

        return loginUser;
    }

    @Override
    public List<LoginUser> getAll() {
        return loginUserRepository.findAll();
    }

    @Override
    public Optional<LoginUser> findById(Long aLong) {
        return loginUserRepository.findById(aLong);
    }

    @Override
    public void add(LoginUser loginUser) {

        loginUser.setPassword(passwordEncoder.encode(loginUser.getPassword()));
        loginUserRepository.save(loginUser);
    }

    @Override
    public Boolean delete(Long aLong) {

        try {
            loginUserRepository.deleteById(aLong);
        }catch (org.springframework.dao.EmptyResultDataAccessException exc){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public Boolean checkIntegrityOfData(LoginUser loginUser){
        if(Objects.nonNull(loginUser) && validate(loginUser)){

            User userFromJSON = loginUser.getUser();

            if(userService.validate(userFromJSON)) return Boolean.TRUE;
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean validate(LoginUser loginUser) {
        boolean userValidated = true;

        if(Objects.isNull(loginUser.getUser())) userValidated = false;
        if(Objects.isNull(loginUser.getUsername())) userValidated = false;
        if(Objects.isNull(loginUser.getPassword())) userValidated = false;

        return userValidated;
    }
}
