package com.derteuffel.controller;

import com.derteuffel.data.Country;
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


    //country crud methods

    @GetMapping("/countries")
    public String findAll(Model model) {
        List<Country> countries= countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "country/countries";
    }

    @GetMapping("/countries/management")
    public String findAllCountry(Model model) {
        List<Country> countries= countryRepository.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("country", new Country());
        return "management/countries";
    }
    @PostMapping("/save")
    public String save(Country country) {
         countryRepository.save(country);
        return "redirect:/external/secure/country/countries/management";
    }

    @GetMapping("/country/{countryId}")
    public String findById(Model model,@PathVariable Long countryId) {
        Optional<Country> countryOptional=countryRepository.findById(countryId);
        List<Region> regions= regionRepository.findAllByCountry(countryOptional.get().getCountryId());
        model.addAttribute("country", countryOptional.get());
        model.addAttribute("regions", regions);

        return "country/country";
    }
    @GetMapping("/country/management/{countryId}")
    public String findone(Model model,@PathVariable Long countryId) {
        Optional<Country> countryOptional=countryRepository.findById(countryId);
        List<Region> regions= regionRepository.findAllByCountry(countryOptional.get().getCountryId());
        model.addAttribute("country", countryOptional.get());
        model.addAttribute("regions", regions);
        model.addAttribute("region",new Region());

        return "management/country";
    }

    @GetMapping("/delete/{countryId}")
    public String deleteById(@PathVariable Long countryId) {
        countryRepository.deleteById(countryId);

        return "redirect:/external/secure/country/countries/management";
    }

    @GetMapping("/delete/region/{regionId}")
    public String delete(@PathVariable Long regionId, HttpSession session) {
        Long countryId= (Long)session.getAttribute("countryId");
        regionRepository.deleteById(regionId);

        return "redirect:/external/secure/country/country/management"+countryId;
    }
}
