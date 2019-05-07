package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.CareerService;
import ar.edu.itba.paw.interfaces.service.UniversityService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Gender;
import ar.edu.itba.paw.model.enums.Role;
import ar.edu.itba.paw.webapp.form.LogInForm;
import ar.edu.itba.paw.webapp.form.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Date;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private CareerService careerService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/logIn")
    public ModelAndView login() {
        return new ModelAndView("logInForm");
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET )
    public ModelAndView signUp(@ModelAttribute("signUpForm") final SignUpForm form) {
        ModelAndView mav = new ModelAndView("signUpForm");
        mav.addObject("universities", universityService.getAll());
        mav.addObject("careers", careerService.getAll());
        return mav;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST )
    public ModelAndView register(@Valid @ModelAttribute("signUpForm") SignUpForm form) {
        if(!form.getRepeatPassword().equals(form.getPassword())){
            return new ModelAndView("redirect:/user/signUp");
        }
        userService.CreateUser(new User.Builder()
                                        .withEmail(form.getEmail())
                                        .withGender(Gender.valueOf(form.getGender()))
                                        .withName(form.getName())
                                        .withLastName(form.getLastName())
                                        .withPasswordHash(passwordEncoder.encode(form.getPassword()))
                                        .withUniversityId(form.getUniversityId())
                                        .withCareerId(form.getCareerId())
                                        .withBio(form.getBio())
                                        .withBirthDate(Date.valueOf(form.getBirthDate()))
                                        .withContactNumber(form.getPhoneNumber())
                                        .withRole(Role.valueOf(form.getRole()))
                                        .build());
        return new ModelAndView("redirect:/");
    }
}
