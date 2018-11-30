package com.derteuffel.controller;

import com.derteuffel.data.User;
import com.derteuffel.service.MailService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by derteuffel on 30/11/2018.
 */
@Controller
@RequestMapping("/password")
public class PasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //display forgot password page

    @GetMapping("/forgot")
    public String forgot(){
        return "resetSentEmail";
    }

    @PostMapping("/forgot")
    public String sent(Model model, @RequestParam("email") String email, HttpServletRequest request){

        // look up user in database by e-mail

        User user= userService.findByEmail(email);

        if (user == null){
            model.addAttribute("errors", "We didn't find an account for that e-mail address.");
        }else {
            // Generate random 36-character string token for reset password
            user.setResetToken(UUID.randomUUID().toString());

            //save token to data base
            userService.update(user);


            String appUrl= request.getScheme() + "://" + request.getServerName();

            MailService passworMail= new MailService();

            passworMail.sendSimpleMessage(user.getEmail(),
                    "Demande de réinitialisation de mot de passe",
                    "Pour réinitialiser votre mot de passe, cliquez sur le lien ci-dessous:\n" + appUrl+
                            ":8080/password/reset?token=" + user.getResetToken());

            // add message in view to confirmation
            model.addAttribute("success", "un lien pour reinitialliser votre mot de passe a été envoyé a cette adresse" + email);

        }
        return "resetSentEmail";
    }

    // display reste password view
    @GetMapping("/reset")
    public  String resetview(Model model, @RequestParam("token") String token){

        Optional<User> user= userService.findByResetToken(token);

        System.out.println(user.get().getPassword());

        if (user.isPresent()){
            // token found in DB
            model.addAttribute("resetToken", token);
        }else {
            // token not found in dataBase
            model.addAttribute("errormessage", "Ooops this is an invalid password link.");
        }

        return "resetPassword";

    }


    // process reset password form
    @PostMapping("/reset")
    public  String resetform(Model model, @RequestParam Map<String, String> requestParams, RedirectAttributes redirectAttributes){
        // find the user associated with the reset token

        Optional<User> user= userService.findByResetToken(requestParams.get("token"));


        // this will be non-null but we check just in case
        if (user.isPresent()){
            // set new password
            System.out.println(user.get().getPassword());
            user.get().setPassword(requestParams.get("password"));
            System.out.println(user.get().getPassword());
            // set the reset token in null so it cannot be used again
            user.get().setResetToken(null);

            // save user
            userService.saveOrUpdate(user.get());

            System.out.println(user.get().getPassword());
            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            redirectAttributes.addFlashAttribute("success", "Vous avez reinitialise avec succes votre Mot de Passe, veuillez vous connecter");
            return "redirect:/login";
        }else {
            model.addAttribute("errorMessage", "Ooops vous n'avez pas le bon lien de reinitialistaion");
            return "resetPassword";
        }
    }
}
