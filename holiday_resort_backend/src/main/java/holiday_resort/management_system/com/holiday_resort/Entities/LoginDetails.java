package holiday_resort.management_system.com.holiday_resort.Entities;

import holiday_resort.management_system.com.holiday_resort.Requests.RegisterRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users_login_tb",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username")
})

@Getter
@Setter
public class LoginDetails implements UserDetails {

    private final static String ROLE_PREFIX = "ROLE_";

    @Id
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name="username")
    private String username;

    @NotBlank
    @Size(max = 20)
    @Column(name="password")
    private String password;

    @OneToOne
    @NotNull
    @MapsId
    private User user;

    @OneToOne
    private Roles roles;

    @NotNull
    private Boolean isEnabled = true;

    public LoginDetails(RegisterRequest registerRequest){

        this.username = registerRequest.getUsername();
        this.password = registerRequest.getPassword();
        this.user = new User.UserBuilder()
                .setCreationDate(LocalDateTime.now())
                .setPhoneNumber(registerRequest.getPhoneNumber())
                .setFirstName(registerRequest.getFirstName())
                .setLastName(registerRequest.getLastName())
                .setEmail(registerRequest.getEmail())
                .setLoginDetails(this)
                .build();
    }

    public LoginDetails(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Roles roles = getRoles();
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();

        roles.getRoleTypesList().forEach(
                role -> simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(ROLE_PREFIX + role))
        );

        return simpleGrantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserLoginBuilder getInstanceOfBuilder(){
        return new UserLoginBuilder();
    }

    public static class UserLoginBuilder{
        private Long id;
        private String username;
        private String password;
        private User user;
        private Boolean isEnabled = true;
        private Roles roles;

        public UserLoginBuilder setId(Long id){
            this.id = id;
            return this;
        }

        public UserLoginBuilder setUsername(String username){
            this.username = username;
            return this;
        }
        public UserLoginBuilder setPassword(String password){
            this.password = password;
            return this;
        }
        public UserLoginBuilder setUser(User user){
            this.user = user;
            return this;
        }

        public UserLoginBuilder setEnabled(Boolean enabled) {
            isEnabled = enabled;
            return this;
        }

        public UserLoginBuilder setRole(Roles roles) {
            this.roles = roles;
            return this;
        }

        public LoginDetails build(){
            LoginDetails loginDetails = new LoginDetails();

            loginDetails.setId(this.id);
            loginDetails.setUser(this.user);
            loginDetails.setRoles(this.roles);
            loginDetails.setIsEnabled(this.isEnabled);
            loginDetails.setUsername(this.username);
            loginDetails.setPassword(this.password);

            return loginDetails;
        }


    }
}
