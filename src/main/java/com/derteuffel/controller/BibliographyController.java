package com.derteuffel.controller;

import com.derteuffel.data.Bibliography;
import com.derteuffel.data.Groupe;
import com.derteuffel.repository.BibliographyRepository;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.TheseRepository;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("/edit/{bibliographyId}")
    public String edit(Model model, @PathVariable Long bibliographyId, HttpSession session, @RequestParam("file") MultipartFile file){


        Groupe groupe= groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        Bibliography bibliography= bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("bibliography",bibliography);
        model.addAttribute("groupe",groupe);
        return "crew/editBiblio";
    }

    @PostMapping("/save")
    public String save(Bibliography bibliography, Errors errors, Model model, HttpSession session, @RequestParam("file") MultipartFile file){
        String fileName = fileUploadService.storeFile(file);
    Bibliography bibliography1= bibliographyRepository.findByTitle(bibliography.getTitle());
        if (bibliography1 != null){
            errors.rejectValue("title","bibliography.error","il existe deja une reference avec ce titre");
        }
        if (errors.hasErrors()){
            model.addAttribute("error","il existe deja une reference avec ce titre");
            return "crew/editBiblio";
        }else {
            bibliography.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
            bibliography.setCouverture("/downloadFile/"+fileName);
            bibliography.setPrice(0.0);
            bibliography.setPagePrice(0.0);
            bibliography.setDisponibility(false);
            bibliographyRepository.save(bibliography);
        }
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId")+"/"+(Long)session.getAttribute("groupeId");
    }

    @PostMapping("/update")
    public String update(Bibliography bibliography, Long groupeId, HttpSession session, String prix,@RequestParam("file") MultipartFile file){
        String fileName = fileUploadService.storeFile(file);
        if (fileName.isEmpty()){
            bibliography.setCouverture(bibliography.getCouverture());
        }else {
            bibliography.setCouverture("/downloadFile/"+fileName);
        }
            bibliography.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
        Groupe groupe= groupeRepository.getOne(groupeId);
        bibliography.setPrice(Double.parseDouble(prix));
            bibliographyRepository.save(bibliography);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId")+"/"+groupe.getGroupeId();
    }

    @PostMapping("/disponibility/{bibliographyId}")
    public String changeDisponibility(Bibliography bibliography,HttpSession session,@PathVariable Long bibliographyId, String prix,String description){

        System.out.println(bibliography.getDisponibility());
        System.out.println(bibliography.getDescription());
        if (bibliography.getDisponibility() == true){
            bibliography.setDisponibility(false);
            bibliography.setPrice(0.0);
        }else {
            bibliography.setDisponibility(true);
            bibliography.setPrice(Double.parseDouble(prix));
        }
        bibliography.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
        bibliography.setDescription(description);
        bibliography.setPagePrice(0.0);
        bibliographyRepository.save(bibliography);
        return "redirect:/groupe/groupe/biblib/"+(Long)session.getAttribute("theseId")+"/"+(Long)session.getAttribute("groupeId");
    }

    @GetMapping("/delete/{bibliographyId}")
    public String delete(@PathVariable Long bibliographyId, HttpSession session){
        bibliographyRepository.deleteById(bibliographyId);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId")+"/"+(Long)session.getAttribute("groupeId");
    }
}
