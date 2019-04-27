package ar.edu.itba.paw.webapp.Utilities;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtility {

    public static String getUsernameOfCurrentlyLoggedUser(SecurityContext context) {
        Object principal = context.getAuthentication().getPrincipal();
        if(principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();
        else return principal.toString();
    }
}
