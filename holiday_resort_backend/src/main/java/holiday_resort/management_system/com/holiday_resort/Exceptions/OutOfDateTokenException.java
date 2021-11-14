package holiday_resort.management_system.com.holiday_resort.Exceptions;

import holiday_resort.management_system.com.holiday_resort.Entities.VerificationToken;
import holiday_resort.management_system.com.holiday_resort.Interfaces.TokenException;
import holiday_resort.management_system.com.holiday_resort.Requests.ExpiredToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class OutOfDateTokenException extends RuntimeException implements TokenException {

    private static final Logger logger = LogManager.getLogger(OutOfDateTokenException.class);

    private ExpiredToken expiredToken;

    public OutOfDateTokenException(VerificationToken verificationToken){
        logger.error(String.format("Token date - %s has expired!", verificationToken.getExpiryDate()));

        expiredToken = ExpiredToken.builder()
                .tokenDate(verificationToken.getExpiryDate())
                .nowDate(LocalDateTime.now())
                .tokenUUID(expiredToken.getTokenUUID())
                .build();
    }

    public ExpiredToken getExpiredToken(){
        return expiredToken;
    }
}
