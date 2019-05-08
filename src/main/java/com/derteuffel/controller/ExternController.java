package com.derteuffel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by derteuffel on 07/05/2019.
 */
@Controller
@RequestMapping("/visitor")
public class ExternController {

    @GetMapping("/catalogues")
    public String catalogues(){
        return "these_module/side/catalogues";
    }
    @GetMapping("/encadrements")
    public String encadrement(){
        return "these_module/side/encadrement";
    }
    @GetMapping("/livres")
    public String livres(){
        return "these_module/side/livres";
    }
    @GetMapping("/magazines")
    public String magazines(){
        return "these_module/side/magazines";
    }
    @GetMapping("/students_work")
    public String students_work(){
        return "these_module/side/students_work";
    }
    @GetMapping("/syllabus")
    public String syllabus(){
        return "these_module/side/syllabus";
    }



    @GetMapping("/help")
    public String help(){
        return "these_module/home_nav/help";
    }
    @GetMapping("/about_us")
    public String about_us(){
        return "these_module/home_nav/about_us";
    }
    @GetMapping("/question")
    public String question(){
        return "these_module/home_nav/question";
    }
    @GetMapping("/work_with_us")
    public String work_with_us(){
        return "these_module/home_nav/work_with_us";
    }

    @GetMapping("/search_theme")
    public String search_theme(){
        return "these_module/advanced/search_theme";
    }
    @GetMapping("/theme")
    public String theme(){
        return "these_module/advanced/theme";
    }
    @GetMapping("/livre")
    public String livre(){
        return "these_module/advanced/livre";
    }


}
