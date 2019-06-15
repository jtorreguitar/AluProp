package ar.edu.itba.paw.webapp.utilities;

import org.springframework.web.servlet.ModelAndView;

import java.net.HttpURLConnection;

public class StatusCodeUtility {

    private StatusCodeUtility() { }

    public static void parseStatusCode(int statusCode, ModelAndView mavWithSuccessView) {
        switch (statusCode) {
            case HttpURLConnection.HTTP_OK:
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                mavWithSuccessView.setViewName("404");
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
            default:
                mavWithSuccessView.setViewName("500");
        }
    }
}
