package com.derteuffel.controller;

import com.derteuffel.data.User;
import com.derteuffel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Created by neword on 18/03/2019.
 */

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    UserRepository userRepository;
    @GetMapping("/form")
    public String getForm(HttpSession session, Model model)
    {
        Optional<User> user = userRepository.findById((Long)session.getAttribute("userId"));
        model.addAttribute("user",user.get());
        return "payment/paymentForm";
    }
    @GetMapping("/transactions")
    public String getTransactions()
    {
        return "payment/transactions";
    }
    @GetMapping("/error")
    public String error()
    {
        return "payment/error";
    }
}
