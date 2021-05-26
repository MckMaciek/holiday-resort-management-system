package holiday_resort.management_system.com.holiday_resort.Configuration;

import holiday_resort.management_system.com.holiday_resort.Enums.Roles;
import holiday_resort.management_system.com.holiday_resort.Services.UserLoginService;
import org.h2.engine.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserLoginService userLoginService;

    public WebSecurity(UserLoginService _userLoginService){
        userLoginService = _userLoginService;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userLoginService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/v1/**").permitAll()
                .antMatchers("/console/**").hasRole(Roles.USER.toString())
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}
