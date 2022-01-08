package holiday_resort.management_system.com.holiday_resort.Listeners;

import holiday_resort.management_system.com.holiday_resort.Emails.GmailMailService;
import holiday_resort.management_system.com.holiday_resort.Entities.VerificationToken;
import holiday_resort.management_system.com.holiday_resort.Events.TokenEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class VerificationTokenListener implements ApplicationListener<TokenEvent> {

    private static final Logger logger = LogManager.getLogger(VerificationTokenListener.class);

    private static final String TOKEN_URL = "http://localhost:8080/api/token/link-activate?tokenUUID=";
    private static final String SUBJECT_MESSAGE = "Holiday Resort App verification token";

    private final GmailMailService gmailMailService;

    @Autowired
    public VerificationTokenListener(GmailMailService gmailMailService){
        this.gmailMailService = gmailMailService;
    }

    @Override
    public void onApplicationEvent(TokenEvent tokenEvent) {
        this.provideRegistrationToken(tokenEvent);
    }

    @Async
    private void provideRegistrationToken(TokenEvent tokenEvent){

        VerificationToken verificationToken = tokenEvent.getVerificationToken();
        String userEmail = verificationToken.getLoginDetails().getUser().getEmail();
        String bodyMessage = TOKEN_URL + verificationToken.getToken();

        try {
            boolean flag = gmailMailService.sendMessage(
                    userEmail,
                    SUBJECT_MESSAGE,
                    bodyMessage
            );
            logger.info(String.format("Activation mail sent status for user: %s - %s", userEmail, flag));

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
