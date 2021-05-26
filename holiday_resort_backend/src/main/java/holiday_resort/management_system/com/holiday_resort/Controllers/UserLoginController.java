package holiday_resort.management_system.com.holiday_resort.Controllers;

import holiday_resort.management_system.com.holiday_resort.Entities.LoginUser;
import holiday_resort.management_system.com.holiday_resort.Services.UserLoginService;
import holiday_resort.management_system.com.holiday_resort.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserLoginController {

    private final UserService userService;
    private final UserLoginService userLoginService;

    @Autowired
    public UserLoginController(UserService _userService,
                               UserLoginService _useLoginService){

        this.userService = _userService;
        this.userLoginService = _useLoginService;
    }

    @RequestMapping(value = "/login-user/add", method = RequestMethod.POST)
    public ResponseEntity<UserController.ResponseStatus> addUser(@RequestBody(required = true) LoginUser loginUser){

        if(userLoginService.checkIntegrityOfData(loginUser)){
            userLoginService.saveUserAndUserLoginObject(loginUser);

            return ResponseEntity.ok(UserController.ResponseStatus.getInstance(loginUser.getId().toString(),
                    "CREATED"));
        }

        return ResponseEntity.badRequest().build();
    }




}
