package com.ad3bay0.rkots.controller.web;

import java.util.List;

import javax.validation.Valid;

import com.ad3bay0.rkots.exceptions.IEXApiException;
import com.ad3bay0.rkots.exceptions.InsufficientBalanceInWalletException;
import com.ad3bay0.rkots.exceptions.UserAlreadyHasWalletException;
import com.ad3bay0.rkots.iex.response.QuoteDto;
import com.ad3bay0.rkots.iex.services.QuoteApiService;
import com.ad3bay0.rkots.models.User;
import com.ad3bay0.rkots.security.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuoteApiService quoteApiService;

    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView registration(@Valid User user, BindingResult bindingResult)
            throws InsufficientBalanceInWalletException, UserAlreadyHasWalletException {

        ModelAndView modelAndView = new ModelAndView();

        boolean userExists = userService.existsByUserNameOrEmail(user);

        if (userExists) {
            bindingResult.rejectValue("username", "error.user",
                    "There is already a user registered with the user name provided");

            bindingResult.rejectValue("email", "error.user",
                    "There is already a user registered with the user email provided");
        }
        if (bindingResult.hasErrors()) {

            modelAndView.setViewName("registration");

        } else {

            userService.saveAndCreateWallet(user);
            modelAndView.addObject("successMessage", "User has been registered successfully!");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;

    }

    @GetMapping(value = { "/", "/login" })
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/user/dashboard")
    public String dashboard(Model model) {
       
            List<QuoteDto> quotes = quoteApiService.getDefaultQuotes();
        
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName()).get();
        model.addAttribute("username", "Welcome " + user.getUsername());
        model.addAttribute("walletBalance", "Wallet Balance: "+user.getWallet().getBalance().toString());
        model.addAttribute("quotes", quotes);
        return "user/dashboard";
    }
}