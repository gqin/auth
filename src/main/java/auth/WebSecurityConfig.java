package auth;




import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USERS_BY_USER_NAME_QUERY =
        "select email as username, password, active as enabled from user where email=?";
    private static final String AUTHORITIES_BY_USERNAME_QUERY =
        "select email as username, role_name from user, role where user.fk_role_id = role.id and email=?";
    @Autowired
    @Qualifier("userdatasource")
    DataSource dataSource;

    @Autowired
    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery(USERS_BY_USER_NAME_QUERY)
            .passwordEncoder(passwordencoder())
            .authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
		// @formatter:on
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        // http.authorizeRequests().antMatchers("/oauth/authorize").permitAll().anyRequest()
        // .authenticated().and().formLogin()
        // .permitAll();
        http
            .requestMatchers().antMatchers("/oauth/authorize")
            .and()
            .authorizeRequests().anyRequest().authenticated();

        // @formatter:on
    }

    private PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }
}
