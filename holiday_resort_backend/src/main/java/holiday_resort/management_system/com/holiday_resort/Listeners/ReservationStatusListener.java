package holiday_resort.management_system.com.holiday_resort.Listeners;

import holiday_resort.management_system.com.holiday_resort.Dto.ReservationDTO;
import holiday_resort.management_system.com.holiday_resort.Emails.GmailMailService;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Enums.ReservationStatus;
import holiday_resort.management_system.com.holiday_resort.Events.ReservationStatusChangedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;


@Component
public class ReservationStatusListener implements ApplicationListener<ReservationStatusChangedEvent> {

    private static final Logger logger = LogManager.getLogger(ReservationStatusListener.class);

    private static final String SUBJECT_MESSAGE = "Status of Your reservation has changed";
    private final GmailMailService gmailMailService;

    @Autowired
    public ReservationStatusListener(GmailMailService gmailMailService){
        this.gmailMailService = gmailMailService;
    }

    @Override
    public void onApplicationEvent(ReservationStatusChangedEvent reservationStatusChangedEvent) {
        this.sendMailAboutStatus(reservationStatusChangedEvent);
    }

    @Async
    private void sendMailAboutStatus(ReservationStatusChangedEvent reservationStatusChangedEvent){

        ReservationDTO reservationDTO = reservationStatusChangedEvent.getReservationDTO();
        User reservationUser = reservationDTO.getUser();

        ReservationStatus previousReservationState = reservationStatusChangedEvent.getPreviousState();

        String bodyMessage = String.format("Your reservation named %s, created %s has changed status from %s to %s",
                reservationDTO.getReservationName(),
                reservationDTO.getCreationDate(),
                previousReservationState.toString(),
                reservationDTO.getReservationStatus());

        try {
            boolean flag = gmailMailService.sendMessage(
                    reservationUser.getEmail(),
                    SUBJECT_MESSAGE,
                    bodyMessage
            );

            logger.info(String.format("Reservation status changed mail sent status for user: %s - %s", reservationUser.getEmail(), flag));

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
