package com.derteuffel.controller;

import com.derteuffel.data.Bibliography;
import com.derteuffel.repository.BibliographyRepository;
import com.derteuffel.repository.TheseRepository;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by derteuffel on 30/01/2019.
 */
@Controller
@RequestMapping("/bibliography")
public class BibliographyController {

    @Autowired
    private BibliographyRepository bibliographyRepository;
    @Autowired
    private TheseRepository theseRepository;

    @GetMapping("/edit/{bibliographyId}")
    public String edit(Model model, @PathVariable Long bibliographyId){
        Bibliography bibliography= bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("bibliography",bibliography);
        return "crew/editBiblio";
    }

    @PostMapping("/save")
    public String save(Bibliography bibliography, Errors errors, Model model, HttpSession session){

    Bibliography bibliography1= bibliographyRepository.findByTitle(bibliography.getTitle());
        if (bibliography1 != null){
            errors.rejectValue("title","bibliography.error","il existe deja une reference avec ce titre");
        }
        if (errors.hasErrors()){
            model.addAttribute("error","il existe deja une reference avec ce titre");
            return "crew/editBiblio";
        }else {
            bibliography.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
            bibliography.setPrice(0.0);
            bibliography.setDisponibility(false);
            bibliographyRepository.save(bibliography);
        }
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId");
    }

    @PostMapping("/update")
    public String update(Bibliography bibliography, Model model, HttpSession session){
            bibliography.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
            bibliographyRepository.save(bibliography);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId");
    }

    @PostMapping("/disponibility/{bibliographyId}")
    public String changeDisponibility(Bibliography bibliography,HttpSession session,@PathVariable Long bibliographyId){

        System.out.println(bibliography.getDisponibility());
        System.out.println(bibliography.getDescription());
        if (bibliography.getDisponibility() == true){
            bibliography.setDisponibility(false);
        }else {
            bibliography.setDisponibility(true);
        }
        bibliography.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
        bibliography.setPagePrice(0.0);
        bibliographyRepository.save(bibliography);
        return "redirect:/groupe/groupe/biblib/"+(Long)session.getAttribute("theseId");
    }

    @GetMapping("/delete/{bibliographyId}")
    public String delete(@PathVariable Long bibliographyId, HttpSession session){
        bibliographyRepository.deleteById(bibliographyId);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId");
    }
}
