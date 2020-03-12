package com.derteuffel.controller;

import com.derteuffel.data.Bibliography;
import com.derteuffel.data.Groupe;
import com.derteuffel.data.User;
import com.derteuffel.repository.BibliographyRepository;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.TheseRepository;
import com.derteuffel.service.MailService;
import com.derteuffel.service.UserService;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by derteuffel on 30/01/2019.
 */
@Controller
@RequestMapping("/bibliography")
public class BibliographyController {

    @Value("${file.upload-dir}")
    private String fileStorage;
    @Autowired
    private BibliographyRepository bibliographyRepository;
    @Autowired
    private TheseRepository theseRepository;
    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/edit/{bibliographyId}")
    public String edit(Model model, @PathVariable Long bibliographyId, HttpSession session){


        Groupe groupe= groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        Bibliography bibliography= bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("bibliography",bibliography);
        model.addAttribute("groupe",groupe);
        return "crew/editBiblio";
    }

    @PostMapping("/save")
    public String save(Bibliography bibliography,String faculte, String option, Errors errors, Model model, HttpSession session, @RequestParam("file") MultipartFile file, @RequestParam("document") MultipartFile document){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());

    Bibliography bibliography1= bibliographyRepository.findByTitle(bibliography.getTitle());
        if (bibliography1 != null){
            errors.rejectValue("title","bibliography.error","il existe deja une reference avec ce titre");
        }
        if (errors.hasErrors()){
            model.addAttribute("error","il existe deja une reference avec ce titre");
            return "crew/editBiblio";
        }else {
            if (!(file.isEmpty())){
                try{
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(fileStorage+file.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
                bibliography.setCouverture("/downloadFile/"+file.getOriginalFilename());
            }
            if (!(document.isEmpty())){
                try{
                    byte[] bytes = document.getBytes();
                    Path path = Paths.get(fileStorage+document.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
                bibliography.setFichier("/downloadFile/"+document.getOriginalFilename());
            }
            bibliography.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));

            bibliography.setPrice(0.0);
            bibliography.setPagePrice(0.0);
            bibliography.setFaculte(faculte);
            bibliography.setOptions(option);
            bibliography.setUser(user);
            bibliography.setDisponibility(false);
            bibliographyRepository.save(bibliography);
        }
        MailService backMessage = new MailService();
        backMessage.sendSimpleMessage(
                "solutioneducationafrique@gmail.com",
                "Notification de l'ajout d'un livre, Titre : "+ bibliography.getTitle(),
                user.getName() + " vous notifi celon le contenue suivant :  veuillez bien prendre connaissance du message et apporter des modifications souligner"
        );
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId")+"/"+(Long)session.getAttribute("groupeId");
    }

    @PostMapping("/update")
    public String update(Bibliography bibliography, Long groupeId, Long userId,@RequestParam("document") MultipartFile document, HttpSession session, String prix,@RequestParam("file") MultipartFile file){

        if (file.isEmpty()){
            bibliography.setCouverture(bibliography.getCouverture());
        }else {
            if (!(file.isEmpty())){
                try{
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(fileStorage+file.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
                bibliography.setCouverture("/downloadFile/"+file.getOriginalFilename());
            }
        }

        if (document.isEmpty()){
            bibliography.setFichier(bibliography.getFichier());
        }else {

            if (!(document.isEmpty())){
                try{
                    byte[] bytes = document.getBytes();
                    Path path = Paths.get(fileStorage+document.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
                bibliography.setFichier("/downloadFile/"+document.getOriginalFilename());
            }
        }
            bibliography.setThese(theseRepository.getOne((Long)session.getAttribute("theseId")));
        Groupe groupe= groupeRepository.getOne(groupeId);
        if (!prix.isEmpty()) {
            bibliography.setPrice(Double.parseDouble(prix));
        }else {
            bibliography.setPrice(0.0);
        }
        if (!(userId == null)) {
            bibliography.setUser(userService.getById(userId));
        }else {
            System.out.println("je suis dedans");
        }

        MailService backMessage = new MailService();
        backMessage.sendSimpleMessage(
                "solutioneducationafrique@gmail.com",
                "Notification de l'ajout d'un livre, Titre : "+ bibliography.getTitle(),
                 " notification celon le contenue suivant :  veuillez bien prendre connaissance du message et apporter des modifications souligner"
        );
            bibliographyRepository.save(bibliography);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId")+"/"+groupe.getGroupeId();
    }

    @PostMapping("/disponibility/{bibliographyId}")
    public String changeDisponibility(HttpSession session,@PathVariable Long bibliographyId, String prix,String description){

        Bibliography bibliography= bibliographyRepository.getOne(bibliographyId);
        if (bibliography.getDisponibility() == true){
            bibliography.setDisponibility(false);
        }else {
            bibliography.setDisponibility(true);
        }
        bibliographyRepository.save(bibliography);
        return "redirect:/groupe/groupe/biblib/"+(Long)session.getAttribute("theseId")+"/"+(Long)session.getAttribute("groupeId");
    }

    @GetMapping("/delete/{bibliographyId}")
    public String delete(@PathVariable Long bibliographyId, HttpSession session){
        bibliographyRepository.deleteById(bibliographyId);
        return "redirect:/groupe/groupe/biblib/"+ (Long)session.getAttribute("theseId")+"/"+(Long)session.getAttribute("groupeId");
    }
}
