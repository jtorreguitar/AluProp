package ar.edu.itba.paw.webapp.config;

import java.util.concurrent.TimeUnit;

import ar.edu.itba.paw.webapp.auth.APUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private APUserDetailsService userDetailsService;
    @Value("${rememberme.key}")
    private String remembermeKey;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
            .invalidSessionUrl("/")
            .and().authorizeRequests()
                .antMatchers("/user/logIn", "/user/signUp").anonymous()
                .antMatchers("**/guest/**").hasRole("GUEST")
                .antMatchers("**/host/**").hasRole("HOST")
                .antMatchers("/host/**").hasRole("HOST")
                .antMatchers("**/user/**").authenticated()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/proposal/**").authenticated()
                .antMatchers("/notifications").authenticated()
                .antMatchers("/**/interest").authenticated()
                .antMatchers("/**").permitAll()

            .and().formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", false)
                .loginPage("/user/logIn")
            .and().rememberMe()
                .rememberMeParameter("rememberme")
                .userDetailsService(userDetailsService)
                .key(remembermeKey)
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
            .and().logout()
                .logoutUrl("/user/logOut")
                .logoutSuccessUrl("/user/logIn")
            .and().exceptionHandling()
                .accessDeniedPage("/403")
            .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
