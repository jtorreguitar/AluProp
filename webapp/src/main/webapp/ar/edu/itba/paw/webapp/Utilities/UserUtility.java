package ar.edu.itba.paw.webapp.Utilities;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtility {

    public static String getUsernameOfCurrentlyLoggedUser(SecurityContext context) {
        Object principal = context.getAuthentication().getPrincipal();
        if(principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();
        else return principal.toString();
    }

    public static User getCurrentlyLoggedUser(SecurityContext context, UserService userService) {
        return userService.getByEmail(getUsernameOfCurrentlyLoggedUser(context));
    }
}
