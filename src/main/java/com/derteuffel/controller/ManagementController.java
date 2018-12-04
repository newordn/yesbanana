package com.derteuffel.controller;

import com.derteuffel.data.*;
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
 * Created by derteuffel on 03/12/2018.
 */
@Controller
@RequestMapping("/management")
public class ManagementController {

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

    // options management methods
    @PostMapping("/options/form/save")
    public String save(Options options, Long facultyId) {
        Faculty faculty=facultyRepository.getOne(facultyId);
        options.setFaculty(faculty);
        optionsRepository.save(options);
        return "redirect:/management/faculty/"+ faculty.getFacultyId();
    }
    @GetMapping("/options/{optionsId}")
    public String findById(Model model, @PathVariable Long optionsId) {
        Optional<Options> optionsOptional= optionsRepository.findById(optionsId);
        model.addAttribute("theses", theseRepository.findAllByOptionsOrderByTheseIdDesc(optionsOptional.get().getOptionsName().toLowerCase()));
        return "management/options";
    }

    @GetMapping("/options/delete/{optionsId}")
    public String deleteById(@PathVariable Long optionsId, HttpSession session) {
        Long facultyId= (Long)session.getAttribute("facultyId");
        optionsRepository.deleteById(optionsId);
        return "redirect:/management/faculty/"+facultyId;
    }

    // university management methods
    @GetMapping("/delete/university/{universityId}")
    public String delete(@PathVariable Long universityId, HttpSession session) {
        Long regionId=(Long)session.getAttribute("regionId");
        universityRepository.deleteById(universityId);
        return "redirect:/management/region/"+regionId;
    }

    @PostMapping("/university/form/save")
    public String save(University university, Long regionId) {
        Region region= regionRepository.getOne(regionId);
        university.setRegion(region);
        universityRepository.save(university);
        return "redirect:/management/region/"+ region.getRegionId();
    }
    @GetMapping("/university/{universityId}")
    public String findOneUniversity(Model model, @PathVariable Long universityId, HttpSession session) {
        Optional<University> universityOptional=universityRepository.findById(universityId);
        session.setAttribute("universityId",universityId);
        List<Faculty> faculties=facultyRepository.findAllByUnniversity(universityOptional.get().getUniversityId());
        model.addAttribute("university", universityOptional.get());
        model.addAttribute("faculties", faculties);
        model.addAttribute("faculty",new Faculty());
        return "management/university";
    }


    // region management methods
    @GetMapping("/region/{regionId}")
    public String findOne(Model model, @PathVariable Long regionId, HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        session.setAttribute("regionId", regionId);
        List<University> universities=universityRepository.findAllByRegion(regionOptional.get().getRegionId());
        model.addAttribute("region",regionOptional.get());
        model.addAttribute("universities", universities);
        model.addAttribute("university", new University());
        return "management/region";
    }
    @PostMapping("/region/form/save")
    public String saveRegion(Region region, HttpSession session) {
        Long countryId=(Long) session.getAttribute("countryId");
        region.setCountry(countryRepository.getOne(countryId));
        regionRepository.save(region);
        return "redirect:/management/country/"+ countryId;
    }
    @GetMapping("/delete/region/{regionId}")
    public String deleteRegion(@PathVariable Long regionId, HttpSession session) {
        Long countryId= (Long)session.getAttribute("countryId");
        regionRepository.deleteById(regionId);

        return "redirect:/management/country/"+countryId;
    }

    // faculty management methods
    @GetMapping("/faculty/{facultyId}")
    public String findOneFaculty(Model model, @PathVariable Long facultyId, HttpSession session) {
        session.setAttribute("facultyId", facultyId);
        Optional<Faculty> facultyOptional= facultyRepository.findById(facultyId);
        model.addAttribute("faculty", facultyOptional.get());
        model.addAttribute("optionses", optionsRepository.findAllByFaculty(facultyOptional.get().getFacultyId()));
        model.addAttribute("options",new Options());
        return "management/faculty";
    }

    @PostMapping("/faculty/form/save")
    public String save(Faculty faculty, Long universityId) {
        University university= universityRepository.getOne(universityId);
        faculty.setUniversity(university);
        facultyRepository.save(faculty);
        return "redirect:/management/university/"+ university.getUniversityId();
    }

    @GetMapping("/delete/faculty/{facultyId}")
    public String deletefaculty(@PathVariable Long facultyId, HttpSession session) {
        Long universityId= (Long)session.getAttribute("universityId");
        facultyRepository.deleteById(facultyId);
        return "redirect:/management/university/"+ universityId;
    }

    // country management methods
    @GetMapping("/countries")
    public String findAllCountry(Model model) {
        List<Country> countries= countryRepository.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("country", new Country());
        return "management/countries";
    }
    @PostMapping("/country/form/save")
    public String save(Country country) {
        System.out.println(country.getCountryId());
        countryRepository.save(country);
        return "redirect:/management/countries";
    }

    @GetMapping("/country/{countryId}")
    public String findone(Model model,@PathVariable Long countryId, HttpSession session) {
        session.setAttribute("countryId",countryId);
        Optional<Country> countryOptional=countryRepository.findById(countryId);
        List<Region> regions= regionRepository.findAllByCountry(countryOptional.get().getCountryId());
        model.addAttribute("country", countryOptional.get());
        model.addAttribute("regions", regions);
        model.addAttribute("region",new Region());

        return "management/country";
    }
    @GetMapping("/country/delete/{countryId}")
    public String deleteById(@PathVariable Long countryId) {
        countryRepository.deleteById(countryId);

        return "redirect:/management/countries";
    }


}
