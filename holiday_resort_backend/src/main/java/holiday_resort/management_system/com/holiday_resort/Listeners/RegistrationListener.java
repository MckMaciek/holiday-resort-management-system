package holiday_resort.management_system.com.holiday_resort.Listeners;

import holiday_resort.management_system.com.holiday_resort.Events.RegistrationCompleteEvent;
import holiday_resort.management_system.com.holiday_resort.Services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public class RegistrationListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenService verificationTokenService;

    @Autowired
    public RegistrationListener(VerificationTokenService verificationTokenService
                                ){
        this.verificationTokenService = verificationTokenService;
    }


    @Override
    public void onApplicationEvent(RegistrationCompleteEvent registrationCompleteEvent) {
        this.provideRegistrationToken(registrationCompleteEvent);
    }


    private void provideRegistrationToken(RegistrationCompleteEvent registrationCompleteEvent){

    }
}
