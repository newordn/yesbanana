package com.derteuffel.controller;


import com.derteuffel.data.Role;
import com.derteuffel.data.User;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.RoleRepository;
import com.derteuffel.repository.UserRepository;
import com.derteuffel.service.MailService;
import com.derteuffel.service.RoleService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by derteuffel on 06/10/2018.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileUploadServices fileUploadServices;
    @Autowired FileUploadService fileUploadService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private GroupeRepository groupeRepository;


    @Autowired
    private UserService userService;

    @GetMapping("")
    public String allUsers(Model model) {

        List<User> users = userService.listAll();
        model.addAttribute("users", users);
        String avatar = "";
        for (User user : users) {
            avatar = user.getImg();
            int d = avatar.indexOf("d");
            user.setImg(avatar.substring(d));
        }

        return "user/users";
    }


    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/user";
    }

    @GetMapping("/registration")
    public String getform(Model model) {
        model.addAttribute("user", new User());
        return "user/userForm";
    }



    @GetMapping("/updateRole")
    public String updateRole(@RequestParam("userId") Long userId, @RequestParam("role") String role) {
        User user = userService.getById(userId);
        Role role1 = roleService.getById(user.getRole().getRoleId());
        role1.setRole(role);
        roleService.saveOrUpdate(role1);
        user.setRole(role1);
        userRepository.save(user);
        return "redirect:/user";


    }

    @GetMapping("/update/role")
    public String updateRoles(@RequestParam("userId") Long userId, @RequestParam("role") String role, HttpSession session) {
        User user = userService.getById(userId);
        Long groupeId = (Long) session.getAttribute("groupeId");
        Role role1 = roleService.getById(user.getRole().getRoleId());
        role1.setRole(role);
        roleService.saveOrUpdate(role1);
        return "redirect:/user";
    }


    private String validate_url="yesbanana.org/validate/";
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createUser(@Valid User user, Model model, @RequestParam("file") MultipartFile file, BindingResult bindingResult, String role) {
        String fileName = fileUploadServices.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
/*
            FileUploadRespone fileUploadRespone = new FileUploadRespone(fileName, fileDownloadUri);*/
        user.setImg("/downloadFile/" + fileName);
        //user.setActive(true);
        List<User> users=userService.listAll();
        if (users.size()<=1){
        Role role1= new Role("root");
            user.setRole(role1);
        }else {
            Role userRole = roleService.findByRole(role);
            if (userRole == null) {
                Role newRole = new Role("user");
                user.setRole(newRole);
                System.out.println(userRole);
            } else {
                user.setRole(userRole);
            }
        }



        User user1 = userService.findByEmail(user.getEmail());
        if (user1 != null) {

            bindingResult.rejectValue("email", "user.error", "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Il existe un utilisateur avec le même email.");
            return "user/userForm";
        } else {
            userService.saveOrUpdate(user);

            MailService mailService = new MailService();
            mailService.sendSimpleMessage(
                    user.getEmail(),
                    "Bienvenue sur Yesbanana votre inscription a été éffectuer avec succès",
                    "veuillez cliquer sur le lien de confirmation "+validate_url+user.getUserId()+ " pour terminer votre inscription et" +
                            " profiter pleinement de votre espace membre"
            );

            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de s'inscrire " +
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");


            return "redirect1";
        }
    }


    @PostMapping(value = "/create/visitor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createVisitor(@Valid User user, Model model, @RequestParam("file") MultipartFile file, BindingResult bindingResult, String role) {
        String fileName = fileUploadServices.storeFile(file);
        String fileNameCv= fileUploadService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
/*
            FileUploadRespone fileUploadRespone = new FileUploadRespone(fileName, fileDownloadUri);*/
        user.setImg("/downloadFile/" + fileName);

            user.setCv("/downloadFile/"+fileNameCv);

        //user.setActive(true);
        List<User> users=userService.listAll();
        if (users.size()<=1){
            Role role1= new Role("root");
            user.setRole(role1);
        }else {
            Role userRole = roleService.findByRole(role);
            if (userRole == null) {
                Role newRole = new Role("user");
                user.setRole(newRole);
                System.out.println(userRole);
            } else {
                user.setRole(userRole);
            }
        }



        User user1 = userService.findByEmail(user.getEmail());
        if (user1 != null) {

            bindingResult.rejectValue("email", "user.error", "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Il existe un utilisateur avec le même email.");
            return "user/userForm";
        } else {
            userService.saveOrUpdate(user);

            MailService mailService = new MailService();
            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur a partir du module emploi",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de s'inscrire  en tant que" + user.getAnotherDetail()+
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");


            return "redirect2";
        }
    }

    @GetMapping("/update")
    public String update(Model model, HttpSession session) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        session.setAttribute("avatar", user.getImg());
        session.setAttribute("name", user.getName());
        User user1=userService.getById(user.getUserId());
        model.addAttribute("user", user1);
        model.addAttribute("groupes", groupeRepository.findAll());
        System.out.println(groupeRepository.findAll());
        if (user1.getAutorization().equals(true)){
            return "user/user1";
        }else {
            return "user/user";
        }




    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public String updateUser ( User user, @RequestParam("file") MultipartFile file, @RequestParam("cvFile") MultipartFile cvFile){
            if(!file.isEmpty()) {
                String fileName = fileUploadService.storeFile(file);

                user.setImg("/downloadFile/" + fileName);

            }
            else
            {
                user.setImg(user.getImg());
            }
        if (!cvFile.isEmpty()){
            String fileNameCv= fileUploadServices.storeFile(cvFile);
                user.setCv("/downloadFile/"+fileNameCv);
        }else {
            user.setCv(user.getCv());
        }
            Role role1=roleService.getById(user.getRole().getRoleId());
            user.setRole(role1);

            System.out.println(user.getRole().getRole());

        if (user.getPassword().isEmpty()){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user1 = userService.findByEmail(auth.getName());
        System.out.println(user1.getPassword());
            user.setPassword(user1.getPassword());
            user.setActive(true);
            userService.update(user);
            MailService mailService = new MailService();

            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de modifier son profil " +
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");



            return "redirect:/groupe/groupes";

        }

        else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(true);
            userService.update(user);
            MailService mailService = new MailService();
            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de modifier son profil " +
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");



            return "redirect:/logout";

        }



        }


    @PostMapping(value = "/modifier", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateUser1 ( User user, @RequestParam("file") MultipartFile file){
        if(!file.isEmpty()) {
            String fileName = fileUploadServices.storeFile(file);
            user.setImg("/downloadFile/" + fileName);
        }
        else
        {
            user.setImg(user.getImg());
        }
        Role role1=roleService.getById(user.getRole().getRoleId());
        user.setRole(role1);

        System.out.println(user.getRole().getRole());

        if (user.getPassword().isEmpty()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user1 = userService.findByEmail(auth.getName());
            System.out.println(user1.getPassword());
            user.setPassword(user1.getPassword());
            user.setActive(true);
            userService.update(user);
            MailService mailService = new MailService();

            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de modifier son profil " +
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");



            return "redirect:/groupe/groupes";

        }

        else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(true);
            userService.update(user);
            MailService mailService = new MailService();
            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de modifier son profil " +
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");



            return "redirect:/logout";

        }



    }




    @GetMapping("/users/{groupeId}")
    public String findAllByGroupe(Model model, Long groupeId) {
        model.addAttribute("users", userRepository.findByGroupes_GroupeId(groupeId));
        return "user/users";

    }


}