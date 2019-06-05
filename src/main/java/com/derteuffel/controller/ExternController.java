package com.derteuffel.controller;

import com.derteuffel.data.Bibliography;
import com.derteuffel.data.Bourse;
import com.derteuffel.data.Syllabus;
import com.derteuffel.data.These;
import com.derteuffel.repository.BibliographyRepository;
import com.derteuffel.repository.BourseRepository;
import com.derteuffel.repository.SyllabusRepository;
import com.derteuffel.repository.TheseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private SyllabusRepository syllabusRepository;
    @Autowired
    private BourseRepository bourseRepository;

    @GetMapping("/catalogues")
    public String catalogues(){
        return "these_module/side/catalogues";
    }
    @GetMapping("/livres")
    public String livres(Model model){
        List<Bibliography> bibliographies=bibliographyRepository.findAllByDisponibility(true);
        System.out.println(bibliographies.size());
        model.addAttribute("livres",bibliographies);
        return "these_module/side/search_livres";
    }
    @GetMapping("/syllabuses")
    public String syllabus(Model model){
        List<Syllabus> syllabuses=syllabusRepository.findBySuprimeeAndStatusOrderBySyllabusIdDesc(true,true);
        model.addAttribute("syllabuses",syllabuses);
        return "these_module/side/search_syllabus";
    }
    @GetMapping("/livre/{bibliographyId}")
    public String livreSide(Model model,@PathVariable Long bibliographyId){
        Bibliography livre=bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("livre",livre);
        return "these_module/side/livre";
    }
    @GetMapping("/syllabus/{syllabusId}")
    public String syllabusSide(Model model,@PathVariable Long syllabusId){
        Syllabus syllabus=syllabusRepository.getOne(syllabusId);
        model.addAttribute("syllabus",syllabus);
        return "these_module/side/syllabus";
    }
    @GetMapping("/magazines")
    public String magazines(){
        return "these_module/side/magazines";
    }
    @GetMapping("/bourses")
    public String bourses(Model model){
        List<Bourse> bourses= bourseRepository.findFirst12ByStatusAndSuprime(true,false, Sort.by(Sort.Direction.DESC,"bourseId"));
        model.addAttribute("bourses",bourses);
        return "these_module/side/bourses";
    }
    @GetMapping("/students_work")
    public String students_work(){
        return "these_module/side/search_student_work";
    }



    @GetMapping("/professeurs")
    public String help(){
        return "these_module/encadrement_travaux/professeurs";
    }
    @GetMapping("/what_are/encadreur")
    public String what(){
        return "these_module/encadrement_travaux/what";
    }
    @GetMapping("/chef/travaux")
    public String about_us(){
        return "these_module/encadrement_travaux/chef_travaux";
    }
    @GetMapping("/enseignants/primaire")
    public String question(){
        return "these_module/encadrement_travaux/primaire";
    }
    @GetMapping("/enseignants/secondaire")
    public String question1(){
        return "these_module/encadrement_travaux/secondaire";
    }
    @GetMapping("/expert/yesb")
    public String work_with_us(){
        return "these_module/encadrement_travaux/expert_yesb";
    }

    @GetMapping("/why_work/with_us")
    public String search_theme(Model model){
        return "these_module/side/why";
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
