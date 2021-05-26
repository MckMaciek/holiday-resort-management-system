package holiday_resort.management_system.com.holiday_resort.Controllers.Exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class UserControllerExceptions {

    private static final Logger logger = LogManager.getLogger(UserControllerExceptions.class);

    public UserControllerExceptions(){
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such user!")
    public static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(){
            logger.error("HTTP_STATUS.NOT_FOUND - NO SUCH USER!");

        }
    }


}
