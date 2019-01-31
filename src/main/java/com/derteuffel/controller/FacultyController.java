package com.derteuffel.controller;

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
import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Controller
@RequestMapping("/external/secure/faculty")
public class FacultyController {

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

    //faculty crud methods


/*
    @GetMapping("/faculties")
    public String findAll(Model model) {
        List<Faculty> faculties=facultyRepository.findAll();
        model.addAttribute("faculties",faculties);
        return "faculty/faculties";
    }
    */

    @GetMapping("/faculty/{facultyId}")
    public String findById(Model model, @PathVariable Long facultyId, HttpSession session) {
        session.setAttribute("facultyId",facultyId);
         Optional<Faculty> facultyOptional= facultyRepository.findById(facultyId);
        model.addAttribute("faculty", facultyOptional.get());
        model.addAttribute("optionses", optionsRepository.findAllByFaculty(facultyOptional.get().getFacultyId()));
        return "faculty/faculty";
    }


}
