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
    public ResponseEntity<UserLoginResponse> addUser(@RequestBody(required = true) LoginUser loginUser){

        if(userLoginService.checkIntegrityOfData(loginUser)){
            userLoginService.saveUserAndUserLoginObject(loginUser);

            return ResponseEntity.ok(new UserLoginResponse.UserLoginResponseBuilder()
                    .setId(loginUser.getId())
                    .setResponse("CREATED")
                    .build());
        }

        return ResponseEntity.badRequest().build();
    }


    private static class UserLoginResponse {
        // FRONTEND RESPONSE IN BODY
        private final String id;
        private final String response;

        private static UserLoginResponse getInstance(String _id, String _response){
            return new UserLoginResponse(_id, _response);
        }

        private UserLoginResponse(String _id, String _response){
            this.response = _response;
            this.id = _id;
        }

        public String getId() {
            return id;
        }

        public String getResponse() {
            return response;
        }

        @Override
        public String toString() {
            return "ResponseStatus{" +
                    "id=" + id +
                    ", response='" + response + '\'' +
                    '}';
        }

        private static class UserLoginResponseBuilder{
            private String id;
            private String response;

            public UserLoginResponseBuilder setId(Long _id){
                this.id = _id.toString();
                return this;
            }
            public UserLoginResponseBuilder setResponse(String _response){
                this.response = _response;
                return this;
            }
            public UserLoginResponse build(){
                return UserLoginResponse.getInstance(
                        this.id,
                        this.response
                );
            }


        }


    }//userLoginResponseEND


}
