package holiday_resort.management_system.com.holiday_resort.Controllers;

import holiday_resort.management_system.com.holiday_resort.Controllers.Exceptions.UserControllerExceptions;
import holiday_resort.management_system.com.holiday_resort.Dto.UserDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController extends Throwable{

    private final UserService userService;

    @Autowired
    public UserController(UserService _userService){
        this.userService = _userService;
    }

    @RequestMapping(value = "/users/all", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userService.getAll();
        if(users.size() == 0) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(users.stream().map(UserDTO::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "id", required = true) Long id)
            throws InvalidParameterException {

        return userService.findById(id)
                .map(UserDTO::new)
                .map(ResponseEntity::ok)
                .orElseThrow(UserControllerExceptions.UserNotFoundException::new);

    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public ResponseEntity<UserResponseStatus> addUser(@RequestBody(required = true) User user){

        if(userService.validate(user)){
            userService.add(user);
            return ResponseEntity.ok(new UserResponseStatus.UserLoginResponseBuilder()
                    .setId(user.getId())
                    .setResponse("CREATED")
                    .build());
        }

        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
    public ResponseEntity<UserResponseStatus> deleteUser(@RequestParam(required = true) Long id) {
        if(!userService.delete(id)){
            throw new UserControllerExceptions.UserNotFoundException();
        }

        return ResponseEntity.ok(new UserResponseStatus.UserLoginResponseBuilder()
                .setId(Long.valueOf(-1))
                .setResponse("DELETED")
                .build());
    }




    private static class UserResponseStatus {
        // FRONTEND RESPONSE IN BODY
        private final String id;
        private final String response;

        private static UserResponseStatus getInstance(String _id, String _response){
            return new UserResponseStatus(_id, _response);
        }

        private UserResponseStatus(String _id, String _response){
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

            public UserResponseStatus.UserLoginResponseBuilder setId(Long _id){
                this.id = _id.toString();
                return this;
            }
            public UserResponseStatus.UserLoginResponseBuilder setResponse(String _response){
                this.response = _response;
                return this;
            }
            public UserResponseStatus build(){
                return UserResponseStatus.getInstance(
                        this.id,
                        this.response
                );
            }


        }


    }//UserResponseStatusEND

}

