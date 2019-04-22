package com.derteuffel.controller;

import com.derteuffel.data.Faculty;
import com.derteuffel.data.Region;
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
import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Controller
@RequestMapping("/external/secure/university")
public class UniversityController {
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

    //university crud methods
/*
    @GetMapping("/universities")
    public String findAll(Model model) {
        model.addAttribute("universities", universityRepository.findAll());
        return "university/universities";
    }
*/
    @GetMapping("/university/{universityId}")
    public String findById(Model model, @PathVariable Long universityId, HttpSession session) {
        Optional<University> universityOptional=universityRepository.findById(universityId);
        List<Faculty> faculties=facultyRepository.findAllByUnniversity(universityOptional.get().getUniversityId());
        model.addAttribute("university", universityOptional.get());
        session.setAttribute("universityId",universityOptional.get().getUniversityId());
        model.addAttribute("faculties", faculties);
        return "university/university";
    }

}
