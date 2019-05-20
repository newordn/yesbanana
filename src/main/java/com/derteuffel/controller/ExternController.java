package com.derteuffel.controller;

import com.derteuffel.data.Bibliography;
import com.derteuffel.data.These;
import com.derteuffel.repository.BibliographyRepository;
import com.derteuffel.repository.TheseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by derteuffel on 07/05/2019.
 */
@Controller
@RequestMapping("/visitor")
public class ExternController {

    @Autowired
    private TheseRepository theseRepository;
    @Autowired
    private BibliographyRepository bibliographyRepository;

    @GetMapping("/catalogues")
    public String catalogues(){
        return "these_module/side/catalogues";
    }
    @GetMapping("/encadrements")
    public String encadrement(){
        return "these_module/side/encadrement";
    }
    @GetMapping("/livres")
    public String livres(Model model){
        List<Bibliography> bibliographies=bibliographyRepository.findAllByDisponibility(true);
        model.addAttribute("livres",bibliographies);
        return "these_module/side/livres";
    }
    @GetMapping("/livre/{bibliographyId}")
    public String livreSide(Model model,@PathVariable Long bibliographyId){
        Bibliography livre=bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("livre",livre);
        return "these_module/side/livre";
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
    public String search_theme(Model model){
        List<These> allTheses= theseRepository.findAll();
        List<These> theses=new ArrayList<>();
        for (These these : allTheses){
            if (these.getStatus() == true){
                theses.add(these);
            }
        }

        model.addAttribute("theses", theses);
        return "these_module/advanced/search_theme";
    }
    @GetMapping("/theme/{theseId}")
    public String theme(Model model, @PathVariable Long theseId, HttpSession session){
        These these=theseRepository.getOne(theseId);
        session.setAttribute("theseId", these.getTheseId());
        model.addAttribute("these", these);

        List<Bibliography> allThesesBibliographies=bibliographyRepository.findAllByThese(these.getTheseId());
        List<Bibliography> livres= new ArrayList<>();
        for (Bibliography bibliography : allThesesBibliographies){
            if (bibliography.getDisponibility() == true){
                livres.add(bibliography);
            }
        }
        model.addAttribute("livres",livres);
        return "these_module/advanced/theme";
    }
    @GetMapping("/livre/detail/{bibliographyId}")
    public String livre(Model model, @PathVariable Long bibliographyId){
        Bibliography bibliography=bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("livre", bibliography);
        return "these_module/advanced/livre";
    }


}
