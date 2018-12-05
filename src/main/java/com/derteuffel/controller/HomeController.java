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
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/login")
    public String login()
    {
            return "connection";
        }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
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

    @GetMapping("/validate/{userId}")
    public String validate(@PathVariable Long userId){
        User user=userService.getById(userId);

        System.out.println(user.getUserId());
        user.setActive(true);
        userRepository.save(user);
        return "redirect:/login";

    }

    @GetMapping("/")
    public  String home(){
        return "index";
    }

    @GetMapping("/deals")
    public  String deals(){
        return "deals";
    }

    @GetMapping("/about")
    public  String about(){
        return "about";
    }

    @GetMapping("/contact")
    public  String contact(){
        return "contact";
    }

    @GetMapping("/school/primary")
    public String schoolprimary(){
        return "primary/courses";
    }

    @GetMapping("/school/primary/transport")
    public String schoolprimarytransport(){
        return "primary/transport";
    }


    @GetMapping("/school/secondary")
    public String schoolsecondary(){
        return "secondary/courses";
    }

    @GetMapping("/school/secondary/transport")
    public String schoolsecondarytransport(){
        return "secondary/transport";
    }

    @GetMapping("/training/admin")
    public String trainingadminfinance(){
        return "training/adminfinance";
    }



    @GetMapping("/training/english/french")
    public String trainingenglishfrench(){
        return "training/englishfrench";
    }



    @GetMapping("/training/wash")
    public String trainingwash(){
        return "training/wash";
    }



    @GetMapping("/training/protection")
    public String trainingprotection(){
        return "training/protection";
    }



    @GetMapping("/training/resource")
    public String trainingresource(){
        return "training/resource";
    }



    @GetMapping("/training/logistic")
    public String traininglogistic(){
        return "training/logistic";
    }



    @GetMapping("/training/it")
    public String trainingit(){
        return "training/it";
    }


    @GetMapping("/other/services")
    public String other(){
        return "other";
    }

    @GetMapping("/visitor/student")
    public String student(Model model)
    {
        model.addAttribute("user", new User());
        return "visitor/student";
    }

    @GetMapping("/visitor/expert")
    public String expert(Model model){
        model.addAttribute("user", new User());
        return "visitor/expert";
    }

    @GetMapping("/visitor/teacher")
    public String teacher(Model model){
        model.addAttribute("user", new User());
        return "visitor/teacher";
    }

}