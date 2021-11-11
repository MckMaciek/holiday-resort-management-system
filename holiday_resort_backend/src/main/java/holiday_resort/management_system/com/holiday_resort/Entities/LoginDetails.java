package holiday_resort.management_system.com.holiday_resort.Entities;

import holiday_resort.management_system.com.holiday_resort.Interfaces.LoginDetailsLinked;
import holiday_resort.management_system.com.holiday_resort.Requests.RegisterRequest;
import lombok.*;
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
@Table(name="login_details_tb",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username")
})

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDetails implements UserDetails, LoginDetailsLinked {

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
                .creationDate(LocalDateTime.now())
                .phoneNumber(registerRequest.getPhoneNumber())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .loginDetails(this)
                .build();
    }

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

    @Override
    public LoginDetails getLinkedLoginDetails() {
        return this;
    }
}
