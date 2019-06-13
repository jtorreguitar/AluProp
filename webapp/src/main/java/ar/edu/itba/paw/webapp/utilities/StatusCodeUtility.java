package ar.edu.itba.paw.webapp.utilities;

import org.springframework.web.servlet.ModelAndView;

import java.net.HttpURLConnection;

public class StatusCodeUtility {

    private StatusCodeUtility() { }

    public static ModelAndView parseStatusCode(int statusCode, String successJsp) {
        switch (statusCode) {
            case HttpURLConnection.HTTP_OK:
                return new ModelAndView(successJsp);
            case HttpURLConnection.HTTP_NOT_FOUND:
                return new ModelAndView("404");
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
            default:
                return new ModelAndView("500");
        }
    }
}
