package com.derteuffel.controller;

import com.derteuffel.data.Bibliography;
import com.derteuffel.data.Faculty;
import com.derteuffel.data.Options;
import com.derteuffel.data.University;
import com.derteuffel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Controller
@RequestMapping("/external/secure/faculty")
public class FacultyController {

   @Autowired
   private BibliographyRepository bibliographyRepository;
    @Autowired
    private OptionsRepository optionsRepository;

    //faculty crud methods


/*
    @GetMapping("/faculties")
    public String findAll(Model model) {
        List<Faculty> faculties=facultyRepository.findAll();
        model.addAttribute("faculties",faculties);
        return "faculty/faculties";
    }
    */
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

    @GetMapping("/faculty/{faculte}")
    public String findById(Model model, @PathVariable String faculte, HttpSession session) {

        List<Bibliography> bibliographies=bibliographyRepository.findByFaculteAndDisponibility(faculte,true);
        List<String> optionses= new ArrayList<>();
        for (Bibliography bibliography : bibliographies){
            if (bibliography.getOptions()!= null && !bibliography.getOptions().isEmpty()){
                optionses.add(bibliography.getOptions());
            }
        }
        String faculty=faculte;
        System.out.println(optionses);
        model.addAttribute("faculty", faculty);
        model.addAttribute("optionses", removeDuplicates(optionses));
        return "these_module/side/search_livres_faculte_option";
    }


}
