package holiday_resort.management_system.com.holiday_resort.Context;


import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext {


    public LoginDetails getAssociatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginDetails) authentication.getPrincipal();
    }

}
