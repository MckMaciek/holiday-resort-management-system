package holiday_resort.management_system.com.holiday_resort.Entities;

import holiday_resort.management_system.com.holiday_resort.Dto.RegisterRequest;
import holiday_resort.management_system.com.holiday_resort.Enums.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="users_login_tb",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username")
        })


public class LoginUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name="username")
    private String username;

    @NotBlank
    @Size(max = 20)
    @Column(name="password")
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @NotNull
    private User user;

    @NotNull
    private Boolean isEnabled = true;

    @NotNull
    private Roles roles = Roles.USER;

    public LoginUser(RegisterRequest registerRequest){

        this.username = registerRequest.getUsername();
        this.password = registerRequest.getPassword();
        this.user = new User.UserBuilder()
                .setCreationDate(LocalDateTime.now())
                .setPhoneNumber(registerRequest.getPhoneNumber())
                .setFirstName(registerRequest.getFirstName())
                .setLastName(registerRequest.getLastName())
                .setEmail(registerRequest.getEmail())
                .setLoginUser(this)
                .build();
    }

    public LoginUser(){}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + getRoles()));
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
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
        private Roles role = Roles.ADMIN;

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

        public UserLoginBuilder setRole(Roles role) {
            this.role = role;
            return this;
        }

        public LoginUser build(){
            LoginUser loginUser = new LoginUser();

            loginUser.setId(this.id);
            loginUser.setUser(this.user);
            loginUser.setRoles(this.role);
            loginUser.setEnabled(this.isEnabled);
            loginUser.setUsername(this.username);
            loginUser.setPassword(this.password);

            return loginUser;
        }


    }
}
