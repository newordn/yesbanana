package com.derteuffel.controller;

import com.derteuffel.data.Event;
import com.derteuffel.data.PagerModel;
import com.derteuffel.data.User;
import com.derteuffel.repository.EventRepository;
import com.derteuffel.service.EventService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Value("${file.upload-dir}")
    private String fileStorage;

    @Autowired
    private EventService eventService;


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



    @PostMapping("/save")
    public String save(Event event, @RequestParam("couverture") MultipartFile couverture, @RequestParam("files") MultipartFile[] files, Model model) throws IOException {

        if (!(couverture.isEmpty())){
            try{
                byte[] bytes = couverture.getBytes();
                Path path = Paths.get(fileStorage+couverture.getOriginalFilename());
                Files.write(path,bytes);
            }catch (Exception e){
                e.printStackTrace();
            }
            event.setImage("/downloadFile/"+couverture.getOriginalFilename());
        }

       /* System.out.println("test access");
        if (!(couverture.isEmpty())){
            System.out.println("inside if"+ fileStorage);
            couverture.transferTo(new File(fileStorage+"/"+couverture.getOriginalFilename()));
        }

        System.out.println("je suis la"+fileStorage);*/

        ArrayList<String> filesPaths = new ArrayList<String>();

        for (MultipartFile file : files){
            if (!(file.isEmpty())){
                try{
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(fileStorage+file.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
                filesPaths.add("/downloadFile/"+file.getOriginalFilename());
            }
        }

            if (event.getType().contains("magazine") && event.getCategory().isEmpty()) {

                model.addAttribute("errors", "Le champ Categorie ne peut etre vide lorsque le type top info est selectionner");
                return "event/form";
            } else {

                event.setPieces(filesPaths);

                event.setLikes(1);
                event.setStatus(false);
                eventService.save(event);
                return "redirect:/event/event/" + event.getEventId();

        }

    }


    @GetMapping("/events")
    public String findAll(Model model, HttpSession session ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("roles",user.getRoles());
        model.addAttribute("roles",user.getRoles());
        List<Event> events=eventService.findAll();

        System.out.println(fileStorage);
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
    public String edit(@PathVariable Long eventId, Model model, HttpSession session){
        Event event=eventService.getOne(eventId);

        session.setAttribute("files", event.getPieces());

        model.addAttribute("event",event);

        return "event/edit";
    }


    @PostMapping("/update/{eventId}")
    public String update(@PathVariable Long eventId,String category, String description, String title, String type){

        Event event= eventService.getOne(eventId);

        event.setCategory(category);
        event.setDescription(description);
        event.setTitle(title);
        event.setType(type);


        eventService.save(event);

        return "redirect:/event/event/"+event.getEventId();
    }


    @GetMapping("/delete/{eventId}")
    public String delete(@PathVariable Long eventId){
        eventService.delete(eventId);
        return "redirect:/event/events";
    }


    @PostMapping("/update/file/{eventId}")
    public String updateFile(@PathVariable Long eventId, @RequestParam("file") MultipartFile file){
        Event event=eventService.getOne(eventId);

        if (!(file.isEmpty())){
            try{
                byte[] bytes = file.getBytes();
                Path path = Paths.get(fileStorage+file.getOriginalFilename());
                Files.write(path,bytes);
            }catch (Exception e){
                e.printStackTrace();
            }
            event.setImage("/downloadFile/"+file.getOriginalFilename());
        }

        eventService.save(event);

        return "redirect:/event/event/"+event.getEventId();

    }

    @PostMapping("/update/files/{eventId}")
    public String updateFiles(@PathVariable Long eventId, @RequestParam("files") MultipartFile[] files){
        Event event=eventService.getOne(eventId);

        ArrayList<String> filesPaths = new ArrayList<String>();

        for (MultipartFile file : files){
            if (!(file.isEmpty())){
                try{
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(fileStorage+file.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
                filesPaths.add("/downloadFile/"+file.getOriginalFilename());
            }
        }

        event.setPieces(filesPaths);

        eventService.save(event);
        return "redirect:/event/event/"+event.getEventId();
    }


}
