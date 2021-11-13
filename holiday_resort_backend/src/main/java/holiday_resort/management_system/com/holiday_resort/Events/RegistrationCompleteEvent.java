package holiday_resort.management_system.com.holiday_resort.Events;

import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class RegistrationCompleteEvent extends ApplicationEvent {

    private final LoginDetails loginDetails;
    private final String verificationUrl;

    @Autowired
    public RegistrationCompleteEvent(LoginDetails loginDetails,
                                     String verificationUrl
    ){
        super(loginDetails);
        this.loginDetails = loginDetails;
        this.verificationUrl = verificationUrl;
    }

}
