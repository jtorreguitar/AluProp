package ar.edu.itba.paw.webapp.auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import ar.edu.itba.paw.interfaces.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Service;

@Service
public class APUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService us;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = us.getByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("No user with the email " + email);
        final Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().toString())
        );
        return new org.springframework.security.core.userdetails.User(email, user.getPasswordHash(), authorities);
    }
}
