package com.derteuffel.controller;

import com.derteuffel.data.Bibliography;
import com.derteuffel.data.Groupe;
import com.derteuffel.data.StudentWork;
import com.derteuffel.data.These;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.StudentWorkRepository;
import com.derteuffel.repository.TheseRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        studentWork.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
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