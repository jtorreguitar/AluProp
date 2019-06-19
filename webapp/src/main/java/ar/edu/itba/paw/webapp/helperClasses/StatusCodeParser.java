package ar.edu.itba.paw.webapp.helperClasses;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.net.HttpURLConnection;

@Component
public class StatusCodeParser {

    public void parseStatusCode(int statusCode, ModelAndView mavWithSuccessView) {
        switch (statusCode) {
            case HttpURLConnection.HTTP_OK:
                break;
            case HttpURLConnection.HTTP_FORBIDDEN:
                mavWithSuccessView.setViewName("redirect:/403");
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                mavWithSuccessView.setViewName("redirect:/404");
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
            default:
                mavWithSuccessView.setViewName("redirect:/500");
        }
    }
}
