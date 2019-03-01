package com.derteuffel.controller;

import com.derteuffel.data.Bibliotheque;
import com.derteuffel.repository.BibliothequeRepository;
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
        Bibliotheque bibliotheque1= bibliothequeRepository.findByBibliotheques(bibliotheque.getBibliotheques());
        if (bibliotheque1 != null){
            errors.rejectValue("bibliotheque","bibliotheque.error","il existe deja une reference avec ce titre");
        }
        if (errors.hasErrors()){
            model.addAttribute("error","il existe deja une reference avec ce titre");
            return "crew/bibliotheque";
        }else {
            bibliotheque.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
            bibliothequeRepository.save(bibliotheque);
        }
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId");
    }

    @PostMapping("/update")
    public String update(Bibliotheque bibliotheque, HttpSession session){
        bibliotheque.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
        bibliothequeRepository.save(bibliotheque);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId");
    }

    @GetMapping("/delete/{bibliographyId}")
    public String delete(@PathVariable Long bibliothequeId, HttpSession session){
        bibliothequeRepository.deleteById(bibliothequeId);
        return "redirect:/groupe/groupe/biblib/ "+ (Long)session.getAttribute("theseId");
    }
}
