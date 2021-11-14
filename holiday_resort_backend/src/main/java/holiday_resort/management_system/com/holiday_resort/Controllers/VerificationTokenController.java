package holiday_resort.management_system.com.holiday_resort.Controllers;


import holiday_resort.management_system.com.holiday_resort.Entities.VerificationToken;
import holiday_resort.management_system.com.holiday_resort.Events.TokenEvent;
import holiday_resort.management_system.com.holiday_resort.Exceptions.OutOfDateTokenException;
import holiday_resort.management_system.com.holiday_resort.Requests.ExpiredToken;
import holiday_resort.management_system.com.holiday_resort.Services.VerificationTokenService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags="[ALL] VerificationToken Boundary")
@RestController
@RequestMapping("/api/token")
public class VerificationTokenController {

    private static final String ALLOW_ONLY_ANONYMOUS = "isAnonymous()";

    private final VerificationTokenService verificationTokenService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public VerificationTokenController(VerificationTokenService verificationTokenService,
                                       ApplicationEventPublisher applicationEventPublisher
    ){
        this.verificationTokenService=  verificationTokenService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping("/link-activate")
    @PreAuthorize(ALLOW_ONLY_ANONYMOUS)
    public ResponseEntity<?> postToken(@RequestParam(required = true, name = "tokenUUID") @NotBlank String tokenUUID)
    {
        try{
            Pair<VerificationToken, Boolean> pair = verificationTokenService.processToken(tokenUUID);
            if(pair == null) return ResponseEntity.status(400).build();

        }catch (OutOfDateTokenException outOfDateTokenException){
            ExpiredToken expiredToken = outOfDateTokenException.getExpiredToken();
            return ResponseEntity.status(400).body(expiredToken);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/token-renew")
    @PreAuthorize(ALLOW_ONLY_ANONYMOUS)
    public ResponseEntity<?> renewToken(@RequestParam(required = true, name = "email") @Email String userEmail){

        Optional<VerificationToken> verificationTokenOpt = verificationTokenService.renewToken(userEmail);

        if(verificationTokenOpt.isPresent()){
            applicationEventPublisher.publishEvent(new TokenEvent(verificationTokenOpt.get()));
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(400).build();
    }

}
