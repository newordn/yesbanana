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

    @GetMapping("/education")
    public  String education(){
        return "redirect:/school/primary";
    }

    // civic education
    @GetMapping("/school/civic")
    public  String civic(){
        return "civic/civic";
    }

    //primary school courses
    @GetMapping("/school/primary")
    public String schoolprimary(){
        return "primary/courses";
    }

    //primary school transport
    @GetMapping("/school/primary/transport")
    public String schoolprimarytransport(){
        return "primary/transport";
    }

    //primary school librairy
    @GetMapping("/school/primary/librairy")
    public String schoolprimarylibrairy(){
        return "primary/librairy";
    }

    //primary school hooliday
    @GetMapping("/school/primary/holliday")
    public String schoolprimaryhollidays(){
        return "primary/holliday";
    }

    //primary school language
    @GetMapping("/school/primary/language")
    public String schoolprimarylanguage(){
        return "primary/language";
    }

    //primary school games
    @GetMapping("/school/primary/games")
    public String schoolprimarygames(){
        return "primary/games";
    }


    //secondary school courses
    @GetMapping("/school/secondary")
    public String schoolsecondary(){
        return "secondary/courses";
    }

    //secondary school transport
    @GetMapping("/school/secondary/transport")
    public String schoolsecondarytransport(){
        return "secondary/transport";
    }

    //secondary school librairy
    @GetMapping("/school/secondary/librairy")
    public String schoolsecondarylibrairy(){
        return "secondary/librairy";
    }

    //secondary school exam
    @GetMapping("/school/secondary/exam")
    public String schoolsecondaryexam(){
        return "secondary/exam";
    }

    //secondary school language
    @GetMapping("/school/secondary/language")
    public String schoolsecondarylanguage(){
        return "secondary/language";
    }

    //secondary school holliday
    @GetMapping("/school/secondary/holliday")
    public String schoolsecondaryholliday(){
        return "secondary/holliday";
    }

    //secondary school games
    @GetMapping("/school/secondary/games")
    public String schoolsecondarygames(){
        return "secondary/games";
    }

    // training finance and administration
    @GetMapping("/training/admin")
    public String trainingadminfinance(){
        return "training/adminfinance";
    }


//training english and french
    @GetMapping("/training/english/french")
    public String trainingenglishfrench(){
        return "training/englishfrench";
    }


//training wash
    @GetMapping("/training/wash")
    public String trainingwash(){
        return "training/wash";
    }


    //training protection
    @GetMapping("/training/protection")
    public String trainingprotection(){
        return "training/protection";
    }


    //training resources
    @GetMapping("/training/resource")
    public String trainingresource(){
        return "training/resource";
    }


    //training logistic
    @GetMapping("/training/logistic")
    public String traininglogistic(){
        return "training/logistic";
    }


    //training it
    @GetMapping("/training/it")
    public String trainingit(){
        return "training/it";
    }

    //training leadership
    @GetMapping("/training/leadership")
    public String trainingleadership(){
        return "training/leadership";
    }

    //training management
    @GetMapping("/training/management")
    public String trainingmanagement(){
        return "training/management";
    }


    @GetMapping("/other/services")
    public String other(){
        return "other";
    }

    @GetMapping("/other/markets")
    public String market(){
        return "markets/market";
    }

    @GetMapping("/school/project")
    public String concour(){
        return "project/concour";
    }

    @GetMapping("/school/undertake/expert")
    public String expert(){
        return "project/expert";
    }

    @GetMapping("/school/undertake")
    public String advice(){
        return "project/undertake";
    }

    @GetMapping("/visitor/student")
    public String student(Model model)
    {
        model.addAttribute("user", new User());
        return "visitor/student";
    }

    @GetMapping("/visitor/expert/universitory")
    public String expertu(Model model){
        model.addAttribute("user", new User());
        return "visitor/expertu";
    }
    @GetMapping("/visitor/expert/professional")
    public String expertp(Model model){
        model.addAttribute("user", new User());
        return "visitor/expertp";
    }

    @GetMapping("/visitor/teacher")
    public String teacher(Model model){
        model.addAttribute("user", new User());
        return "visitor/teacher";
    }

}