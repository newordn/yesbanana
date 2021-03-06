package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.StudentWorkRepository;
import com.derteuffel.repository.TheseRepository;
import com.derteuffel.service.MailService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by derteuffel on 19/04/2019.
 */
@Controller
@RequestMapping("/studentWork")
public class StudentWorkController {
    @Autowired
    private StudentWorkRepository studentWorkRepository;
    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private TheseRepository theseRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/edit/{studentWorkId}")
    public String edit(Model model, @PathVariable Long studentWorkId, HttpSession session){
        StudentWork studentWork= studentWorkRepository.getOne(studentWorkId);
        Groupe groupe = groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        model.addAttribute("groupe",groupe);
        model.addAttribute("studentWork",studentWork);
        return "crew/editStudentWork";
    }

    @PostMapping("/save")
    public String save(StudentWork studentWork, HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        studentWork.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
        studentWorkRepository.save(studentWork);
        MailService backMessage = new MailService();
        backMessage.sendSimpleMessage(
                "solutioneducationafrique@gmail.com",
                "Notification de l'ajout d'un travail d'etudiant, Titre : "+ studentWork.getSubject(),
                user.getName() + " vous notifi celon le contenue suivant :  veuillez bien prendre connaissance du message et apporter des modifications souligner"
        );
        return "redirect:/groupe/groupe/biblib" +"/"+ (Long)session.getAttribute("theseId")+"/"+(Long)session.getAttribute("groupeId");
    }

    @GetMapping("/active/{studentWorkId}")
    public String active(@PathVariable Long studentWorkId,HttpSession session){
        StudentWork studentWork=studentWorkRepository.getOne(studentWorkId);
        if (studentWork.getStatus() == true){
            studentWork.setStatus(false);
        }else {
            studentWork.setStatus(true);
        }
        studentWorkRepository.save(studentWork);
        return "redirect:/groupe/groupe/biblib" +"/"+ (Long)session.getAttribute("theseId")+"/"+(Long)session.getAttribute("groupeId");
    }

    @PostMapping("/update")
    public String update( StudentWork studentWork, Long groupeId, HttpSession session){
        Groupe groupe=groupeRepository.getOne(groupeId);
        studentWork.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
        studentWorkRepository.save(studentWork);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId")+"/"+groupe.getGroupeId();
    }

    @GetMapping("/delete/{studentWorkId}")
    public String delete(@PathVariable Long studentWorkId, HttpSession session){
        studentWorkRepository.deleteById(studentWorkId);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId")+"/"+(Long)session.getAttribute("groupeId");
    }
}