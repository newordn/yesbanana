package com.derteuffel.controller;

import com.derteuffel.data.Country;
import com.derteuffel.data.Region;
import com.derteuffel.data.University;
import com.derteuffel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Controller
@RequestMapping("/external/secure/region")
public class RegionController {

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

    //region crud methods
/*
    @GetMapping("/regions")
    public String findAll(Model model) {
        List<Region> regions= regionRepository.findAll();
        model.addAttribute("regions", regions);
        return "region/regions";
    }
*/
    @PostMapping("/save")
    public  String save(Region region, Long countryId) {
        Country country= countryRepository.getOne(countryId);
        regionRepository.save(region);
        return "redirect:/external/secure/country/country/management/"+ country.getCountryId();
    }

    @GetMapping("/region/{regionId}")
    public String findById(Model model, @PathVariable Long regionId) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        List<University> universities=universityRepository.findAllByRegion(regionOptional.get().getRegionId());
        model.addAttribute("region",regionOptional.get());
        model.addAttribute("universities", universities);
        return "region/region";
    }

    @GetMapping("/region/management/{regionId}")
    public String findOne(Model model, @PathVariable Long regionId,HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        session.setAttribute("regionId", regionId);
        List<University> universities=universityRepository.findAllByRegion(regionOptional.get().getRegionId());
        model.addAttribute("region",regionOptional.get());
        model.addAttribute("universities", universities);
        model.addAttribute("university", new University());
        return "management/region";
    }

    @GetMapping("/delete/university/{universityId}")
    public String delete(@PathVariable Long universityId, HttpSession session) {
        Long regionId=(Long)session.getAttribute("regionId");
        universityRepository.deleteById(universityId);
        return "redirect:/external/secure/region/region/management/"+regionId;
    }

}
