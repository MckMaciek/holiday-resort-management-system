package holiday_resort.management_system.com.holiday_resort.Events;

import holiday_resort.management_system.com.holiday_resort.Entities.VerificationToken;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class RegistrationCompleteEvent extends ApplicationEvent {

    private final VerificationToken verificationToken;

    public RegistrationCompleteEvent(VerificationToken verificationToken
    ){
        super(verificationToken);
        this.verificationToken = verificationToken;
    }

}
