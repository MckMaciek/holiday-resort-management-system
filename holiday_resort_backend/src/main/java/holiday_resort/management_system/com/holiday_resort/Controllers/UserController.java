package holiday_resort.management_system.com.holiday_resort.Controllers;

import holiday_resort.management_system.com.holiday_resort.Context.UserContext;
import holiday_resort.management_system.com.holiday_resort.Dto.UserDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Responses.UserInfoResponse;
import holiday_resort.management_system.com.holiday_resort.Responses.UserResponse;
import holiday_resort.management_system.com.holiday_resort.Services.UserService;
import io.swagger.annotations.Api;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

import static holiday_resort.management_system.com.holiday_resort.Enums.Access.ROLE_ADMIN;
import static holiday_resort.management_system.com.holiday_resort.Enums.Access.ROLE_USER;

@RestController
@Api(tags="[ADMIN] - Manage users")
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController extends Throwable{

    private final UserService userService;
    private final UserContext userContext;

    @Autowired
    public UserController(UserService userService,
                          UserContext userContext){
        this.userService = userService;
        this.userContext = userContext;
    }

    @PreAuthorize(ROLE_ADMIN)
    @RequestMapping(value = "/users/all", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<User> users = userService.getAll();
        if(users.size() == 0) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(users.stream().map(UserDTO::new).map(UserResponse::new).collect(Collectors.toList()));
    }

    @PreAuthorize(ROLE_ADMIN)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "id", required = true) Long id)
            throws InvalidParameterException {

        return userService.findById(id)
                .map(UserDTO::new)
                .map(ResponseEntity::ok)
                .orElseThrow(InvalidParameterException::new);
    }

    @PreAuthorize(ROLE_ADMIN)
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public ResponseEntity<UserResponseStatus> addUser(@RequestBody(required = true) User user){

        if(userService.validate(user)){
            userService.add(user);
            return ResponseEntity.ok(UserResponseStatus.builder()
                    .id(user.getId())
                    .response("CREATED")
                    .build());
        }

        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize(ROLE_USER)
    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public ResponseEntity<UserInfoResponse> getUserInfo(){

        LoginDetails loginDetails = userContext.getAssociatedUser();

        return ResponseEntity.ok(
                new UserInfoResponse(loginDetails.getUser())
                );
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private static class UserResponseStatus {
        private  Long id;
        private  String response;

        }
}

