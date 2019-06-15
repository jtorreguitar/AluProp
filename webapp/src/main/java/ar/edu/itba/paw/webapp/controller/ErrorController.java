package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.utilities.NavigationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;

@Controller
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private NavigationUtility navigationUtility;

    @RequestMapping(value = "errors", method= RequestMethod.GET)
    public ModelAndView renderErrorPage(@ModelAttribute FilteredSearchForm searchForm,
                                        HttpServletRequest httpRequest){
        ModelAndView errorPage;
        String errorMsg;
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode){
            case HttpURLConnection.HTTP_BAD_REQUEST:
            case HttpURLConnection.HTTP_NOT_FOUND:
                return new ModelAndView("404");
            case HttpURLConnection.HTTP_FORBIDDEN:
                return new ModelAndView("403");
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                return new ModelAndView("500");
            default:
                errorMsg = "Unhandled Error. Something went wrong!";
                break;
        }

        errorPage = new ModelAndView("errorPage");
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }

    @RequestMapping(value = "403", method = RequestMethod.GET)
    public ModelAndView forbidden() {
        final User u = navigationUtility.getCurrentlyLoggedUser();
        if(u != null)
            logger.warn("User tried to access forbidden endpoint: " + u.toString());
        return navigationUtility.mavWithGeneralNavigationAttributes("403");
    }
}
