package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Entities.LoginUser;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserLoginService implements UserDetailsService, CrudOperations<LoginUser, Long> {

    private final LoginUserRepository loginUserRepository;

    @Autowired
    public UserLoginService(LoginUserRepository _loginUserRepository){
        loginUserRepository = _loginUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        LoginUser loginUser = loginUserRepository.findByUsername(username);
//        System.out.println("[DEBUG] " + loginUser);

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
        loginUserRepository.save(loginUser);
    }

}
