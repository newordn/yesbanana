package com.derteuffel.controller;

import com.derteuffel.data.Bibliotheque;
import com.derteuffel.repository.BibliothequeRepository;
import com.derteuffel.repository.TheseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by derteuffel on 02/02/2019.
 */
@Controller
@RequestMapping("/bibliotheque")
public class BibliothequeController {

    @Autowired
    private BibliothequeRepository bibliothequeRepository;

    @Autowired
    private TheseRepository theseRepository;

    @PostMapping("/save")
    public String save(Bibliotheque bibliotheque, Errors errors, Model model, HttpSession session){

            bibliotheque.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
            bibliothequeRepository.save(bibliotheque);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId");
    }

    @PostMapping("/update")
    public String update(Bibliotheque bibliotheque, HttpSession session){
        bibliotheque.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
        bibliothequeRepository.save(bibliotheque);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId");
    }

    @GetMapping("/delete/{bibliothequeId}")
    public String delete(@PathVariable Long bibliothequeId, HttpSession session){
        bibliothequeRepository.deleteById(bibliothequeId);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId");
    }
}
