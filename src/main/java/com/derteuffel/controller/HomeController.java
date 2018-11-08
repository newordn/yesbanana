package com.derteuffel.controller;

import com.derteuffel.data.Groupe;
import com.derteuffel.data.These;
import com.derteuffel.data.User;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.TheseRepository;
import com.derteuffel.repository.UserRepository;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by derteuffel on 20/10/2018.
 */
@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private TheseRepository theseRepository;
    @GetMapping("/")
    public String login(Model model, @RequestParam(value="success",required = false) boolean success, @RequestParam(value="error",required = false) boolean error)
    {
        if(success) {
            model.addAttribute("success", "Votre inscription a été éffectué avec succès, Veuillez vous connecter.");
            return "index";
        }
            if(error) {
            model.addAttribute("error", "Email ou mot de passe incorrect.");
            return "index";
        }
        else
            return "index";
        }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/stats")
    public String stats (Model model){
        List<User> users = userService.listAll();
        List<Groupe> groupes = groupeRepository.findAll();
        List<These> theses = theseRepository.findAll();
        model.addAttribute("usersSize",users.size());
        model.addAttribute("groupes",groupes);
        model.addAttribute("thesesSize",theses.size());
        return "stats/stats";
    }
}