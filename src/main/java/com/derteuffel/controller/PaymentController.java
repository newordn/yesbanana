package com.derteuffel.controller;

import com.derteuffel.data.Article;
import com.derteuffel.data.Panier;
import com.derteuffel.data.User;
import com.derteuffel.repository.ArticleRepository;
import com.derteuffel.repository.PanierRepository;
import com.derteuffel.repository.UserRepository;
import com.derteuffel.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;

/**
 * Created by neword on 18/03/2019.
 */

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private UserService userService;
    @Autowired
    PanierRepository panierRepository;
    @Autowired
    ArticleRepository articleRepository;
   private UserRepository userRepository;
    @GetMapping("/form/{amount}")
    public String getForm(HttpSession session, Model model, @PathVariable String amount)
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        model.addAttribute("user",user);
        model.addAttribute("amount", Double.parseDouble(amount));
        return "payment/paymentForm";
    }
    @GetMapping("/panier")
    public String getPanier(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        List<Panier> paniers1 = user.getPaniers();
        model.addAttribute("panier",paniers1.get(paniers1.size()-1));
        return "payment/panier";
    }
    @GetMapping("/delete/article/{articleId}")
    public String deleteArticle(@PathVariable Long articleId )
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        List<Panier> paniers1 = user.getPaniers();
        Panier panier = paniers1.get(paniers1.size()-1);
        panier.setCount(panier.getCount()-articleRepository.findById(articleId).get().getPrix());
        articleRepository.deleteById(articleId);
        panierRepository.save(panier);
        return "redirect:/payment/panier";
    }
    @PostMapping("/article/panier")
    public String addArticle(HttpServletRequest request)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        List<Panier> paniers = user.getPaniers();
        Panier panier = paniers.get(paniers.size()-1);
        Article article  = new Article(request.getParameter("name"),Double.parseDouble(request.getParameter("prix")),panier);
        panier.setCount(article.getPrix() + panier.getCount());
        articleRepository.save(article);
        panierRepository.save(panier);
        return "redirect:/payment/panier";
    }
    @GetMapping("/transactions")
    public String getTransactions( Model model)
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        List<Panier> paniers = new ArrayList<Panier>();
        List<Panier> paniers1 = user.getPaniers();
        for(int i= paniers1.size()-1;i>=0;i--)
        {
            if(paniers1.get(i).getCount()!=0.0)
                paniers.add(paniers1.get(i));
        }
        model.addAttribute("paniers",paniers);
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
