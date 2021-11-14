package holiday_resort.management_system.com.holiday_resort.Listeners;

import holiday_resort.management_system.com.holiday_resort.Emails.GmailMailService;
import holiday_resort.management_system.com.holiday_resort.Entities.VerificationToken;
import holiday_resort.management_system.com.holiday_resort.Events.RegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompleteEvent> {

    private static final String TOKEN_URL = "http://localhost:8080/api/token/link-activate?tokenUUID=";
    private static final String SUBJECT_MESSAGE = "Holiday Resort App verification token";

    private final GmailMailService gmailMailService;

    @Autowired
    public RegistrationListener(GmailMailService gmailMailService){
        this.gmailMailService = gmailMailService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent registrationCompleteEvent) {
        this.provideRegistrationToken(registrationCompleteEvent);
    }

    private void provideRegistrationToken(RegistrationCompleteEvent registrationCompleteEvent){

        VerificationToken verificationToken = registrationCompleteEvent.getVerificationToken();
        String userEmail = verificationToken.getLoginDetails().getUser().getEmail();
        String bodyMessage = TOKEN_URL + verificationToken.getToken();

        try {
            boolean flag = gmailMailService.sendMessage(
                    userEmail,
                    SUBJECT_MESSAGE,
                    bodyMessage
            );
            System.out.println(String.format("[DEBUG] - mail send status - %s", flag));

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
