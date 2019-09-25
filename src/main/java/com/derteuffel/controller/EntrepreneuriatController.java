package com.derteuffel.controller;

import com.derteuffel.data.Entrepreneuriat;
import com.derteuffel.data.PagerModel;
import com.derteuffel.data.Period;
import com.derteuffel.data.User;
import com.derteuffel.repository.EntrepreneuriatRepository;
import com.derteuffel.repository.EventRepository;
import com.derteuffel.repository.LessonRepository;
import com.derteuffel.repository.PeriodRepository;
import com.derteuffel.service.EntrepreneuriatService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Controller
@RequestMapping("/entrepreneuriat")
public class EntrepreneuriatController {

    @Autowired
    private EntrepreneuriatService entrepreneuriatService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private UserService userService;
    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
    }


    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("entrepreneuriat", new Entrepreneuriat());
        return "entrepreneuriat/form";
    }

    @PostMapping("/save")
    public String save(Entrepreneuriat entrepreneuriat, String price, @RequestParam("files") MultipartFile[] files){
        entrepreneuriat.setPrice(Double.parseDouble(price));
        List<FileUploadRespone> pieces = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArrayList<String> filesPaths = new ArrayList<String>();
        for (int i = 0; i < pieces.size(); i++) {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }
        entrepreneuriat.setPieces(filesPaths);

        entrepreneuriat.setStates(false);
        entrepreneuriat.setStatus(false);

        entrepreneuriatService.save(entrepreneuriat);

        return "redirect:/entrepreneuriat/entrepreneuriat/"+entrepreneuriat.getEntrepreneuriatId();

    }


    @GetMapping("/entrepreneuriat/{entrepreneuriatId}")
    public String getOne(Model model, @PathVariable Long entrepreneuriatId){
        Entrepreneuriat entrepreneuriat= entrepreneuriatService.getOne(entrepreneuriatId);
        model.addAttribute("entrepreneuriat",entrepreneuriat);
        return "entrepreneuriat/entrepreneuriat";
    }

    @GetMapping("/entrepreneuriats")
    public String findAll(Model model, HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("roles",user.getRoles());
        model.addAttribute("roles",user.getRoles());
        model.addAttribute("entrepreneuriats", entrepreneuriatService.findAll());
        return "entrepreneuriat/entrepreneuriats";
    }

    @GetMapping("/entrepreneuriats/false/false")
    public String findByStates(Model model){

        model.addAttribute("entrepreneuriats", entrepreneuriatService.findByStatesAndStatus(false,false));
        return "entrepreneuriat/entrepreneuriats";

    }

    @GetMapping("/activation/{entrepreneuriatId}")
    public String activation(@PathVariable Long entrepreneuriatId){
        entrepreneuriatService.activation(entrepreneuriatId);
        return "redirect:/entrepreneuriat/entrepreneuriats";
    }


    @GetMapping("/edit/{entrepreneuriatId}")
    public String editForm(@PathVariable Long entrepreneuriatId, Model model){
        Entrepreneuriat entrepreneuriat= entrepreneuriatService.getOne(entrepreneuriatId);
        model.addAttribute("entrepreneuriat",entrepreneuriat);
        return  "entrepreneuriat/edit";
    }



    @PostMapping("/edit")
    public String edit(Entrepreneuriat entrepreneuriat, @RequestParam("files") MultipartFile[] files, String price, String prix){

        System.out.println(files.length);
            List<FileUploadRespone> pieces = Arrays.asList(files)
                    .stream()
                    .map(file -> uploadFile(file))
                    .collect(Collectors.toList());
        System.out.println("longueur "+ entrepreneuriat.getPieces());
            if (pieces.size()<1) {
                System.out.println("je n'est pas ete modifier");
                if (price.isEmpty()) {
                    entrepreneuriat.setPrice(Double.parseDouble(prix));
                } else {
                    entrepreneuriat.setPrice(Double.parseDouble(price));
                }

                entrepreneuriat.setPieces(entrepreneuriat.getPieces());
                entrepreneuriatService.save(entrepreneuriat);
            }else {
            ArrayList<String> filesPaths = new ArrayList<String>();
            for (int i = 0; i < pieces.size(); i++) {
                filesPaths.add(pieces.get(i).getFileDownloadUri());
            }
            System.out.println("here i'm empty"+price);
            if (price.isEmpty()){
                entrepreneuriat.setPrice(Double.parseDouble(prix));
            }else {
                entrepreneuriat.setPrice(Double.parseDouble(price));
            }
            entrepreneuriat.setPieces(filesPaths);
            entrepreneuriatService.save(entrepreneuriat);
        }


        return "redirect:/entrepreneuriat/entrepreneuriat/"+entrepreneuriat.getEntrepreneuriatId();
    }






}
