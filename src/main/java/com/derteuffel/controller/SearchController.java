package com.derteuffel.controller;

import com.derteuffel.data.Bibliography;
import com.derteuffel.data.These;
import com.derteuffel.repository.BibliographyRepository;
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
import java.util.List;

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

    @GetMapping("/rapide/search/catalogues")
    public String catalogues_list(@RequestParam(name = "motCle",defaultValue = "")String mc, @RequestParam(name = "page",defaultValue = "0")int p, Model model){
        List<These> all=theseRepository.findAll();
        System.out.println(all);
        Page<These> theses=theseRepository.findByStatesAndMotCle(true,"%"+mc+"%", new PageRequest(p,5));
        int pagesCount=theses.getTotalPages();
        int[] pages= new int[pagesCount];
        for (int i=0;i<pagesCount;i++){
            pages[i]=i;
        }
        System.out.println(theses.getTotalElements());
        System.out.println(theses);
        model.addAttribute("pages",pages);
        model.addAttribute("pageCourante",p);
        model.addAttribute("motCle", mc);
        model.addAttribute("theses", theses);
        return "these_module/side/catalogues_search";
    }


    @GetMapping("/avance/search/catalogues")
    public String catalogues_list_avancee(@RequestParam(name = "motCle",defaultValue = "")String mc, @RequestParam(name = "page",defaultValue = "0")int p, Model model){
        List<These> all=theseRepository.findAll();
        System.out.println(all);
        Page<These> theses=theseRepository.recherche_avance(true,"%"+mc+"%","%"+mc+"%",
                "%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%",
                "%"+mc+"%",new PageRequest(p,5));
        int pagesCount=theses.getTotalPages();
        int[] pages= new int[pagesCount];
        for (int i=0;i<pagesCount;i++){
            pages[i]=i;
        }
        System.out.println(theses.getTotalElements());
        System.out.println(theses);
        model.addAttribute("pages",pages);
        model.addAttribute("pageCourante",p);
        model.addAttribute("motCle", mc);
        model.addAttribute("theses", theses);
        return "these_module/side/catalogues_search";
    }

    @GetMapping("/search/catalogues")
    public String catalogues_list_search(@RequestParam(name = "motCle",defaultValue = "")String mc, @RequestParam(name = "page",defaultValue = "0")int p, Model model){
        List<These> all=theseRepository.findAll();
        System.out.println(all);
        Page<These> theses=theseRepository.recherche_avance(true,"%"+mc+"%","%"+mc+"%",
                "%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%","%"+mc+"%",
                "%"+mc+"%",new PageRequest(p,5));
        int pagesCount=theses.getTotalPages();
        int[] pages= new int[pagesCount];
        for (int i=0;i<pagesCount;i++){
            pages[i]=i;
        }
        System.out.println(theses.getTotalElements());
        System.out.println(theses);
        model.addAttribute("pages",pages);
        model.addAttribute("pageCourante",p);
        model.addAttribute("motCle", mc);
        model.addAttribute("theses", theses);
        return "these_module/side/catalogues_search";
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
