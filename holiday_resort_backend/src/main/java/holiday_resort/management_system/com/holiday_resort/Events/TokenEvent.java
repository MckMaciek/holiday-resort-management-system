package holiday_resort.management_system.com.holiday_resort.Events;

import holiday_resort.management_system.com.holiday_resort.Entities.VerificationToken;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class TokenEvent extends ApplicationEvent {

    private final VerificationToken verificationToken;

    public TokenEvent(VerificationToken verificationToken
    ){
        super(verificationToken);
        this.verificationToken = verificationToken;
    }

}
