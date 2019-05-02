package com.derteuffel.controller;

import com.derteuffel.data.Bibliography;
import com.derteuffel.data.Options;
import com.derteuffel.data.These;
import com.derteuffel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Controller
@RequestMapping("/external/secure/options")
public class OptionsController {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private BibliographyRepository bibliographyRepository;
    @Autowired
    private OptionsRepository optionsRepository;
    @Autowired
    private TheseRepository theseRepository;

    //options crud methods

    @GetMapping("/options")
    public String findAll(Model model) {
        List<Options> optionses= optionsRepository.findAll();
        model.addAttribute("optionses", optionses);
        return "options/optionses";
    }

    public List<String> removeDuplicates(List<String> list)
    {
        if (list == null){
            return new ArrayList<>();
        }

        // Create a new ArrayList
        List<String> newList = new ArrayList<String>();
        // Traverse through the first list
        for (String element : list) {

            // If this element is not present in newList
            // then add it

            if (element !=null && !newList.contains(element) && !element.isEmpty()) {

                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }
    @GetMapping("/options/{optionsId}")
    public String findById(Model model, @PathVariable Long optionsId, HttpSession session) {
        Optional<Options> optionsOptional= optionsRepository.findById(optionsId);
        List<These> theses=theseRepository.findAllByStatus(true);
        List<These> theses1=theseRepository.findAllByOptionsOrderByTheseIdDesc(optionsOptional.get().getOptionsName());
        List<These> theses2= new ArrayList<>();
        ArrayList<String> mots=new ArrayList<>();
        for (These  these : theses){
            for (int i=0; i<theses1.size(); i++){
                if (these.getTheseId().equals(theses1.get(i).getTheseId())){
                    mots.add(theses1.get(i).getMotCle());
                    theses2.add(these);
                }
            }
        }

        session.setAttribute("optionsId",optionsOptional.get().getOptionsId());
        model.addAttribute("mots",removeDuplicates(mots));
        model.addAttribute("options", optionsOptional.get());
        model.addAttribute("theses", theses2);
        return "options/options";
    }

    @GetMapping("/buy/pages/{motCle}")
    public String buyBooksPages(@PathVariable String motCle, Model model,HttpSession session){

        List<Bibliography> livresDisponobles=bibliographyRepository.findAllByDisponibility(true);
        List<Bibliography> livresFaculte=bibliographyRepository.findAllByTitle(motCle);
        List<These>findTheses=theseRepository.findAllByMotCle(motCle);
        session.setAttribute("motCle",motCle);
        model.addAttribute("mot",motCle);
        model.addAttribute("theses",findTheses);
        return "options/buy/pages";

    }

    @GetMapping("/buy/livres/{theseId}")
    public String buyBooks(@PathVariable Long theseId, Model model){

        These these= theseRepository.getOne(theseId);

        model.addAttribute("these",these);
        return "options/buy/books";

    }

}
