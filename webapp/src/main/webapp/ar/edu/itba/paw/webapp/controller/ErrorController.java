package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "errors", method= RequestMethod.GET)
    public ModelAndView renderErrorPage(@ModelAttribute FilteredSearchForm searchForm,
                                        HttpServletRequest httpRequest){
        ModelAndView errorPage;
        String errorMsg;
 //       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User u = userService.getUserWithRelatedEntitiesByEmail(auth.getName());
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode){
            case 400:
                //errorMsg = "Http Error Code: 400. Bad Request";
                errorPage = new ModelAndView("404");
                return errorPage;
            case 401:
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            case 404:
                //errorMsg = "Http Error Code: 404. Resource Not Found";
                errorPage = new ModelAndView("404");
                return errorPage;
            case 500:
                //errorMsg = "Http Error Code: 500. Internal Server error";
                errorPage = new ModelAndView("500");
                return errorPage;
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
}
