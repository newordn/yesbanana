package com.derteuffel.controller;

import com.derteuffel.data.User;
import com.derteuffel.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by neword on 18/03/2019.
 */

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
   private UserRepository userRepository;
    @GetMapping("/form")
    public String getForm(HttpSession session, Model model)
    {
        Optional<User> user = userRepository.findById((Long)session.getAttribute("userId"));
        model.addAttribute("user",user.get());
        return "payment/paymentForm";
    }
    @GetMapping("/panier")
    public String getPanier()
    {
        return "payment/panier";
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

    @PostMapping("/payBy/card")
    public String payByCard(HttpServletRequest request) {
        Stripe.apiKey = "sk_test_XQayGouNu0oTFSRnX6lFzkru00GjI4eZlA";
        // Token is created using Checkout or Elements!
        // Get the payment token ID submitted by the form:
        String token = request.getParameter("stripeToken");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", 999);
        params.put("currency", "usd");
        params.put("description", "Charging for goods on Yesbanana");
        params.put("source", token);
        try {
            Charge charge = Charge.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
        }


        return "payment/transactions";
    }
}
