package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.*;
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
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private RegionRepository regionRepository;

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
    public  String education(Model model){
        List<Country> countries=countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "country/education";
    }
    @GetMapping("/education/region/{countryId}")
    public String region(Model model, @PathVariable Long countryId){
        Country country= countryRepository.getOne(countryId);
        List<Region> regions= regionRepository.findAllByCountry(country.getCountryId());
        model.addAttribute("regions", regions);
        return "region/education";
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


    //secondary school courses
    @GetMapping("/school/secondary")
    public String schoolsecondary(){
        return "secondary/courses";
    }



    // training finance and administration
    @GetMapping("/training/admin")
    public String training(){
        return "training/adminfinance";
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