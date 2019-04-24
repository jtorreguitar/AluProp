package ar.edu.itba.paw.webapp.config;

import java.util.concurrent.TimeUnit;

import ar.edu.itba.paw.webapp.auth.APUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    protected void configure(final HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService)
                .sessionManagement()
                .invalidSessionUrl("/logIn")
            .and().authorizeRequests()
                .antMatchers("/logIn").anonymous()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").authenticated()
            .and().formLogin()
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .defaultSuccessUrl("/", false)
                .loginPage("/logIn")
            .and().rememberMe()
                .rememberMeParameter("j_rememberme")
                .userDetailsService(userDetailsService)
                .key(remembermeKey)
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
            .and().logout()
                .logoutUrl("/logOut")
                .logoutSuccessUrl("/logIn")
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
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
