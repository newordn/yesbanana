package com.derteuffel.controller;

import com.derteuffel.data.Event;
import com.derteuffel.data.PagerModel;
import com.derteuffel.data.User;
import com.derteuffel.repository.EventRepository;
import com.derteuffel.service.EventService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by derteuffel on 05/01/2019.
 */
@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private UserService userService;

    @GetMapping("/event/{eventId}")
    public String event(Model model, @PathVariable Long eventId){

       Event event= eventService.getOne(eventId);
        model.addAttribute("event", event);
        return "event/event";
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("event",new Event());
        return "event/form";
    }

    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
    }


    @PostMapping("/save")
    public String save(Event event, @RequestParam("couverture") MultipartFile couverture, @RequestParam("files") MultipartFile[] files){
        String fileName = fileUploadService.storeFile(couverture);

        List<FileUploadRespone> pieces = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArrayList<String> filesPaths = new ArrayList<String>();
        for (int i = 0; i < pieces.size(); i++) {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }

        event.setImage("/downloadFile/"+fileName);
        event.setPieces(filesPaths);

        event.setLikes(1);
        event.setStatus(false);
        eventService.save(event);
        return "redirect:/event/event/"+event.getEventId();

    }


    @GetMapping("/events")
    public String findAll(Model model, HttpSession session ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("roles",user.getRoles());
        model.addAttribute("roles",user.getRoles());
        List<Event> events=eventService.findAll();

        model.addAttribute("events",events);
        return "event/events";
    }

    @GetMapping("/activation/{eventId}")
    public String activation(@PathVariable Long eventId){
        Event event=eventService.getOne(eventId);
        if (event.getStatus()== false){
            event.setStatus(true);
        }else {
            event.setStatus(false);
        }

        eventService.save(event);
        return "redirect:/event/events";
    }

    @GetMapping("/event/search")
    public String findForSearch(@RequestParam(name = "motCle", defaultValue = "") String mc, Model model){
        List<Event> events=eventService.findForSearch("%"+mc+"%","%"+mc+"%","%"+mc+"%");
        model.addAttribute("events",events);
        return "event/events";
    }


    @GetMapping("/edit/{eventId}")
    public String edit(@PathVariable Long eventId, Model model){
        Event event=eventService.getOne(eventId);

        model.addAttribute("event",event);

        return "event/edit";
    }


    @PostMapping("/update")
    public String update(Event event, @RequestParam("couverture") MultipartFile couverture, @RequestParam("files") MultipartFile[] files){

        if (couverture.isEmpty()){
            event.setImage(event.getImage());
        }else {
            String fileName = fileUploadService.storeFile(couverture);
            event.setImage("/downloadFile/"+fileName);

        }

        List<FileUploadRespone> pieces= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        if (pieces.size()==0){
            event.setPieces(event.getPieces());
        }else {
            ArrayList<String> filesPaths = new ArrayList<String>();
            for(int i=0;i<pieces.size();i++)
            {

                filesPaths.add(pieces.get(i).getFileDownloadUri());

            }

            event.setPieces(filesPaths);
        }

        eventService.save(event);

        return "redirect:/event/event/"+event.getEventId();
    }


    @GetMapping("/delete/{eventId}")
    public String delete(@PathVariable Long eventId){
        eventService.delete(eventId);
        return "redirect:/event/events";
    }


}
