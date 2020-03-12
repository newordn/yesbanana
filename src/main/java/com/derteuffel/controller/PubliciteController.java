package com.derteuffel.controller;

import com.derteuffel.data.Publicite;
import com.derteuffel.service.PubliciteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/publicite")
public class PubliciteController {

    @Value("${file.upload-dir}")
    private String fileStorage;

    @Autowired
    private PubliciteService publiciteService;



    @GetMapping("/publicites")
    public String list(Model model){
            List<Publicite> publicites = publiciteService.findAll();
            model.addAttribute("publicites",publicites);
            model.addAttribute("publicite",new Publicite());
            return "publicite/publicites";
    }

    @PostMapping("/save")
    public String save(Publicite publicite, @RequestParam("file") MultipartFile file, @RequestParam("fichier") MultipartFile fichier) throws IOException {
        if (!(file.isEmpty())){
            file.transferTo(new File(System.getProperty(fileStorage)+file.getOriginalFilename()));
        }

        if (!(fichier.isEmpty())){
            fichier.transferTo(new File(System.getProperty(fileStorage)+fichier.getOriginalFilename()));
        }

        publicite.setCouverture(fileStorage+file.getOriginalFilename());
        publicite.setFile(fileStorage+fichier.getOriginalFilename());

        publiciteService.save(publicite);

        return "redirect:/publicite/publicites";

    }


}
