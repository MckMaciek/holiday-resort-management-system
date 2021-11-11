package holiday_resort.management_system.com.holiday_resort.Context;


import holiday_resort.management_system.com.holiday_resort.Controllers.Exceptions.UserControllerExceptions;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Repositories.LoginDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext {

    private final LoginDetailsRepository loginDetailsRepository;

    @Autowired
    public UserContext(LoginDetailsRepository loginDetailsRepository){
        this.loginDetailsRepository = loginDetailsRepository;
    }

    public LoginDetails getAssociatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return loginDetailsRepository.findByUsername(userName)
                .orElseThrow(UserControllerExceptions.UserNotFoundException::new);
    }

}
