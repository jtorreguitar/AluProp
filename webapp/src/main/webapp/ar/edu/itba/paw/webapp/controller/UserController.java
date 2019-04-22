package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.form.LogInForm;
import ar.edu.itba.paw.webapp.form.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/logIn", method = RequestMethod.GET )
    public ModelAndView login() {
        return new ModelAndView("logInForm");
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.POST )
    public ModelAndView login(@ModelAttribute("LogInForm") LogInForm form) {
        return new ModelAndView("logInForm");
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET )
    public ModelAndView register() {
        return new ModelAndView("signUpForm");
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST )
    public ModelAndView login(@ModelAttribute("SignUpForm") SignUpForm form) {
        return new ModelAndView("logInForm");
    }
}
