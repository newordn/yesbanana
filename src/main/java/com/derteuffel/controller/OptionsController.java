package com.derteuffel.controller;

import com.derteuffel.data.Options;
import com.derteuffel.data.These;
import com.derteuffel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/options/{optionsId}")
    public String findById(Model model, @PathVariable Long optionsId) {
        Optional<Options> optionsOptional= optionsRepository.findById(optionsId);
        List<These> theses=theseRepository.findAllByStatus(true);
        List<These> theses1=theseRepository.findAllByOptionsOrderByTheseIdDesc(optionsOptional.get().getOptionsName());
        List<These> theses2= new ArrayList<>();
        for (These  these : theses){
            for (int i=0; i<theses1.size(); i++){
                if (these.getTheseId().equals(theses1.get(i).getTheseId())){
                    theses2.add(these);
                }
            }
        }
        model.addAttribute("options", optionsOptional.get());
        model.addAttribute("theses", theses2);
        return "options/options";
    }

}
