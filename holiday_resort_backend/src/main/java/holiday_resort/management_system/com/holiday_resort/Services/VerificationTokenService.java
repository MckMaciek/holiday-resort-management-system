package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.VerificationToken;
import holiday_resort.management_system.com.holiday_resort.Exceptions.OutOfDateTokenException;
import holiday_resort.management_system.com.holiday_resort.Repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenService {

    private static final int TOKEN_DAYS_TO_EXPIRE = 5;

    private final VerificationTokenRepository verificationTokenRepository;
    private final LoginDetailsService loginDetailsService;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository,
                                    LoginDetailsService loginDetailsService
    ){
        this.verificationTokenRepository = verificationTokenRepository;
        this.loginDetailsService = loginDetailsService;
    }

    public Pair<VerificationToken, Boolean> processToken(String tokenUUID){

        Optional<VerificationToken> verificationTokenOpt = verificationTokenRepository.findByToken(tokenUUID);
        if(verificationTokenOpt.isPresent()) {

            VerificationToken verificationToken = verificationTokenOpt.get();

            if(LocalDateTime.now().isBefore(verificationToken.getExpiryDate())){
                LoginDetails loginDetails  = verificationToken.getLoginDetails();

                loginDetails.setIsEnabled(true);
                loginDetailsService.save(loginDetails);

                return Pair.of(verificationToken, true);
            } else throw new OutOfDateTokenException(verificationToken);
        }

        return null;
    }

    public VerificationToken provideToken(LoginDetails loginDetails){
        UUID tokenValue = UUID.randomUUID();

        VerificationToken verificationToken = VerificationToken.builder()
                .token(tokenValue.toString())
                .loginDetails(loginDetails)
                .expiryDate(LocalDateTime.now().plus(TOKEN_DAYS_TO_EXPIRE, ChronoUnit.DAYS))
                .build();

        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

}
