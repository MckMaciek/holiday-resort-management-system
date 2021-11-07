package holiday_resort.management_system.com.holiday_resort.Controllers;

import holiday_resort.management_system.com.holiday_resort.Controllers.Exceptions.UserControllerExceptions;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Services.LoginDetailsService;
import holiday_resort.management_system.com.holiday_resort.Services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags="[ADMIN] - UserLogin CRUD")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginDetailsController {

    private static final String ROLE_ADMIN= "hasRole('ROLE_ADMIN')";

    private final UserService userService;
    private final LoginDetailsService loginDetailsService;

    @Autowired
    public LoginDetailsController(UserService _userService,
                                  LoginDetailsService _useLoginService){

        this.userService = _userService;
        this.loginDetailsService = _useLoginService;
    }

    @PreAuthorize(ROLE_ADMIN)
    @RequestMapping(value = "/login-user/add", method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponse> addUser(@RequestBody(required = true) LoginDetails loginDetails){

        if(loginDetailsService.checkIntegrityOfData(loginDetails)){
            loginDetailsService.saveUserAndUserLoginObject(loginDetails);

            return ResponseEntity.ok(new UserLoginResponse.UserLoginResponseBuilder()
                    .setResponse("CREATED")
                    .build());
        }

        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize(ROLE_ADMIN)
    @RequestMapping(value = "/login-user/delete", method = RequestMethod.DELETE)
    public ResponseEntity<UserLoginResponse> deleteUser(@RequestParam(required = true) Long id) {
        if(!loginDetailsService.delete(id)){
            throw new UserControllerExceptions.UserNotFoundException();
        }

        return ResponseEntity.ok(new UserLoginResponse.UserLoginResponseBuilder()
                .setId(Long.valueOf(-1))
                .setResponse("DELETED")
                .build());
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
