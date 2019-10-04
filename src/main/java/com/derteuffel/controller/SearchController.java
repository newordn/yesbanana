package com.derteuffel.controller;

import com.derteuffel.data.Bibliography;
import com.derteuffel.data.Syllabus;
import com.derteuffel.data.These;
import com.derteuffel.repository.BibliographyRepository;
import com.derteuffel.repository.SyllabusRepository;
import com.derteuffel.repository.TheseRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by derteuffel on 20/05/2019.
 */
@Controller
@RequestMapping("/visitor")
public class SearchController {

    @Autowired
    private TheseRepository theseRepository;
    @Autowired
    private BibliographyRepository bibliographyRepository;
    @Autowired
    private SyllabusRepository syllabusRepository;

    @GetMapping("/rapide/search/catalogues")
    public String catalogues_list(@RequestParam(name = "motCle",defaultValue = "")String mc, Model model){
        List<These> all=theseRepository.findAll();
        System.out.println(all);
        List<These> theses=theseRepository.recherche(true,"%"+mc+"%","%"+mc+"%",
                "%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%",
                "%"+mc+"%");
        List<Bibliography> bibliographies=bibliographyRepository.rechercheB(true,"%"+mc+"%","%"+mc+"%",
                "%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%",
                "%"+mc+"%","%"+mc+"%","%"+mc+"%");
        List<Syllabus> syllabuses=syllabusRepository.rechercheS(true,"%"+mc+"%","%"+mc+"%","%"+mc+"%",
                "%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%");
        List<Object> elements=new ArrayList<>();
        Stream.of(theses,bibliographies,syllabuses).forEach(elements::addAll);
        System.out.println(elements);


        model.addAttribute("motCle", mc);
        //model.addAttribute("theses", theses);
        return "these_module/side/search_catalogues";
    }


    @GetMapping("/avance/search/catalogues")
    public String catalogues_list_avancee(@RequestParam(name = "motCle",defaultValue = "")String mc,  Model model){

        List<These> theses=theseRepository.recherche(true,"%"+mc+"%","%"+mc+"%",
                "%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%",
                "%"+mc+"%");
        model.addAttribute("motCle", mc);
        model.addAttribute("theses", theses);
        return "these_module/side/search_catalogues";
    }

    @GetMapping("/search/catalogues")
    public String catalogues_list_search(@RequestParam(name = "motCle",defaultValue = "")String mc, Model model){

        List<These> theses=theseRepository.recherche(true,"%"+mc+"%","%"+mc+"%",
                "%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%",
                "%"+mc+"%");
        model.addAttribute("motCle", mc);
        model.addAttribute("theses", theses);
        return "these_module/side/search_catalogues";
    }

    @GetMapping("/search/bibliographies/{theseId}")
    public String search_bibliographies(@PathVariable Long theseId, Model model){
        These these= theseRepository.getOne(theseId);
        List<Bibliography> bibliographies= bibliographyRepository.findAllByTheseAndDisponibility(these.getTheseId(),true);
        List<Bibliography> bibliographies1=bibliographyRepository.findByMotCleAndDisponibility(these.getMotCle(),true);
        bibliographies.addAll(bibliographies1);
        model.addAttribute("bibliographies",bibliographies);
        return "these_module/side/bibliographies";
    }
}
