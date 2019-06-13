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
import java.net.HttpURLConnection;

@Controller
public class ErrorController {

    @Autowired
    UserService userService;

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
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
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
}
