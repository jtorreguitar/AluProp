package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ProposalService;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.form.ProposalForm;
import ar.edu.itba.paw.webapp.utilities.NavigationUtility;
import ar.edu.itba.paw.webapp.utilities.StatusCodeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.HttpURLConnection;

@Controller
@RequestMapping("guest")
public class GuestController {

    @Autowired
    private NavigationUtility navigationUtility;
    @Autowired
    private ProposalService proposalService;

    @RequestMapping(value = "/delete/{proposalId}", method = RequestMethod.POST )
    public ModelAndView delete(@PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form,
                               final BindingResult errors,
                               @ModelAttribute FilteredSearchForm searchForm) {
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes();
        final int statusCode = proposalService.delete(proposalId);
        if(statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
            mav.setViewName("404");
            return mav;
        }
        mav.setViewName("redirect:/"); // TODO : redirect to confirmation screen, maybe with call to action.
        return mav;
    }

    @RequestMapping(value = "/accept/{proposalId}", method = RequestMethod.POST )
    public ModelAndView accept(HttpServletRequest request,
                               @PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form,
                               final BindingResult errors) {
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("redirect:/proposal/" + proposalId);
        int statusCode = proposalService.setAcceptInvite(proposalId);
        StatusCodeUtility.parseStatusCode(statusCode, mav);
        return mav;
    }

    @RequestMapping(value = "/decline/{proposalId}", method = RequestMethod.POST )
    public ModelAndView decline(@PathVariable(value = "proposalId") int proposalId,
                                @Valid @ModelAttribute("proposalForm") ProposalForm form,
                                final BindingResult errors) {
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("redirect:/proposal/" + proposalId);
        int statusCode = proposalService.setDeclineInvite(proposalId);
        StatusCodeUtility.parseStatusCode(statusCode, mav);
        return mav;
    }
}
