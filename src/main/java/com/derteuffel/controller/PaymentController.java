package com.derteuffel.controller;

import com.derteuffel.data.Commande;
import com.derteuffel.data.User;
import com.derteuffel.repository.CommandeRepository;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PaymentController {

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private UserService userService;


    @GetMapping("/payment/form/{commandeId}")
    public String form(@PathVariable Long commandeId, Model model){
        Commande commande=commandeRepository.getOne(commandeId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());

        model.addAttribute("user",user);
        model.addAttribute("commande",commande);
        return "payment/paymentForm";

    }
}
