package ar.edu.itba.paw.webapp.utilities;

import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

public class UserUtility {

    private UserUtility() { }

    public static String getUsernameOfCurrentlyLoggedUser(SecurityContext context) {
        Object principal = context.getAuthentication().getPrincipal();
        if(principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();
        else return principal.toString();
    }

    public static User getCurrentlyLoggedUser(SecurityContext context, UserService userService) {
        return userService.getByEmail(getUsernameOfCurrentlyLoggedUser(context));
    }

    public static <T> void addPaginationAttributes(ModelAndView mav, PageResponse<T> response, int maxItems) {
        mav.addObject("properties", response.getResponseData());
        mav.addObject("currentPage", response.getPageNumber());
        mav.addObject("totalPages", response.getTotalPages());
        mav.addObject("totalElements", response.getTotalItems());
        mav.addObject("maxItems",maxItems);
    }

    public static void addUserAttributes(ModelAndView mav, User user) {
        mav.addObject("currentUser", user);
        mav.addObject("userRole", user.getRole().toString());
    }
}
