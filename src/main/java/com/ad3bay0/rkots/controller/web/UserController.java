package com.ad3bay0.rkots.controller.web;

import java.util.List;

import javax.validation.Valid;

import com.ad3bay0.rkots.exceptions.InsufficientBalanceInWalletException;
import com.ad3bay0.rkots.exceptions.UserAlreadyHasWalletException;
import com.ad3bay0.rkots.iex.response.QuoteDto;
import com.ad3bay0.rkots.iex.services.QuoteApiService;
import com.ad3bay0.rkots.models.User;
import com.ad3bay0.rkots.models.Wallet;
import com.ad3bay0.rkots.security.services.UserService;
import com.ad3bay0.rkots.services.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuoteApiService quoteApiService;

    @Autowired
    private WalletService walletService;

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
       

        User user = userService.getCurrentlyLoggedInUser();
        Wallet wallet = user.getWallet();
        model.addAttribute("username", "Welcome " + user.getUsername());
        model.addAttribute("walletBalance", wallet.getBalance());
        model.addAttribute("quotes", quotes);
        model.addAttribute("transactions", wallet.getWalletTransactions());
        return "user/dashboard";
    }

    @PostMapping("/purchase")
    public String purchase(@RequestParam("quoteId") String quoteId,Model model, RedirectAttributes redirAttrs)
            throws InsufficientBalanceInWalletException, UserAlreadyHasWalletException {

        User user = userService.getCurrentlyLoggedInUser();
        Wallet wallet = user.getWallet();

        String message = "";

       try{
         wallet = walletService.buyStockFromWallet(user, quoteId);
         message = "Stock successfully purchased!";


       }catch(Exception ex){

        message = ex.getMessage();
       }

       List<QuoteDto> quotes = quoteApiService.getDefaultQuotes();

       model.addAttribute("username", "Welcome " + user.getUsername());
       model.addAttribute("walletBalance", wallet.getBalance());
       model.addAttribute("quotes", quotes);
       model.addAttribute("transactions", wallet.getWalletTransactions());
       redirAttrs.addFlashAttribute("message", message);

       return "redirect:user/dashboard";

    }
}