package holiday_resort.management_system.com.holiday_resort.Controllers;


import holiday_resort.management_system.com.holiday_resort.Entities.VerificationToken;
import holiday_resort.management_system.com.holiday_resort.Exceptions.OutOfDateTokenException;
import holiday_resort.management_system.com.holiday_resort.Requests.ExpiredToken;
import holiday_resort.management_system.com.holiday_resort.Services.VerificationTokenService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags="[ALL] VerificationToken Boundary")
@RestController
@RequestMapping("/api/token")
public class VerificationTokenController {

    private static final String ALLOW_ONLY_ANONYMOUS = "isAnonymous()";

    private final VerificationTokenService verificationTokenService;

    @Autowired
    public VerificationTokenController(VerificationTokenService verificationTokenService){
        this.verificationTokenService=  verificationTokenService;
    }

    @GetMapping("/link-activate")
    public ResponseEntity<?> postToken(@RequestParam(required = true, name = "tokenUUID") @NotNull String tokenUUID)
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
}
