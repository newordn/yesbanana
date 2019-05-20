package com.derteuffel.controller;

import com.derteuffel.data.These;
import com.derteuffel.repository.BibliographyRepository;
import com.derteuffel.repository.TheseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/search/catalogues")
    public String catalogues_list_search(@RequestParam(name = "motCle",defaultValue = "")String mc, @RequestParam(name = "page",defaultValue = "0")int p, Model model){
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
}
