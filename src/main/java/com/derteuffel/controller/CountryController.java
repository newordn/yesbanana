package com.derteuffel.controller;

import com.derteuffel.data.Bibliography;
import com.derteuffel.data.Country;
import com.derteuffel.data.Faculty;
import com.derteuffel.data.Region;
import com.derteuffel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/external/secure/country")
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private OptionsRepository optionsRepository;
    @Autowired
    private BibliographyRepository bibliographyRepository;

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
    //country crud methods

    @GetMapping("/countries")
    public String findAll(Model model) {
        List<Bibliography> bibliographies=bibliographyRepository.findAllByDisponibility(true);
        List<String> faculties=new ArrayList<>();

        for (Bibliography bibliography : bibliographies){
            if (bibliography.getFaculte()!= null && !bibliography.getFaculte().isEmpty()) {
                faculties.add(bibliography.getFaculte());
            }

        }
        System.out.println(faculties);
        model.addAttribute("faculties",removeDuplicates(faculties));
        return "these_module/side/search_livres_faculte";
    }

    @GetMapping("/country/{countryId}")
    public String findById(Model model,@PathVariable Long countryId) {
        Optional<Country> countryOptional=countryRepository.findById(countryId);
        List<Region> regions= regionRepository.findAllByCountry(countryOptional.get().getCountryId());
        model.addAttribute("country", countryOptional.get());
        model.addAttribute("regions", regions);

        return "country/country";
    }

    @GetMapping("/education/{countryId}")
    public String findByIdEducation(Model model,@PathVariable Long countryId) {
        Optional<Country> countryOptional=countryRepository.findById(countryId);
        List<Region> regions= regionRepository.findAllByCountry(countryOptional.get().getCountryId());
        model.addAttribute("country", countryOptional.get());
        model.addAttribute("regions", regions);

        return "country/education";
    }


}
