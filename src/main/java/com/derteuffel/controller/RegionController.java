package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private OtherRepository otherRepository;

    //region crud methods
/*
    @GetMapping("/regions")
    public String findAll(Model model) {
        List<Region> regions= regionRepository.findAll();
        model.addAttribute("regions", regions);
        return "region/regions";
    }
*/


    @GetMapping("/region/university/{regionId}")
    public String findById(Model model, @PathVariable Long regionId,HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        List<University> universities=universityRepository.findAllByRegion(regionOptional.get().getRegionId());

        model.addAttribute("region",regionOptional.get());
        model.addAttribute("universities", universities);
        session.setAttribute("regionId",regionOptional.get().getRegionId());

        return "region/element";
    }

    @GetMapping("/region/logement/{regionId}")
    public String other(Model model, @PathVariable Long regionId, HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        session.setAttribute("regionId", regionId);
        List<Other> others= otherRepository.findAllByRegion(regionOptional.get().getRegionId());
        List<Other> others1= otherRepository.findAllByType("Logements");
        List<Other> others2= new ArrayList<>();
        for (Other other : others){
            for (int i=0; i<others1.size();i++){
                if (other.getOtherId().equals(others1.get(i).getOtherId())){
                    others2.add(other);
                }
            }
        }
        model.addAttribute("region", regionOptional.get());
        model.addAttribute("logements", others2);
        return "region/other/logement";
    }

    @GetMapping("/region/approvisionnement/{regionId}")
    public String approvisionnement(Model model, @PathVariable Long regionId, HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        session.setAttribute("regionId", regionId);
        List<Other> others= otherRepository.findAllByRegion(regionOptional.get().getRegionId());
        List<Other> others1= otherRepository.findAllByType("Approvisionnement");
        List<Other> others2= new ArrayList<>();
        for (Other other : others){
            for (int i=0; i<others1.size();i++){
                if (other.getOtherId().equals(others1.get(i).getOtherId())){
                    others2.add(other);
                }
            }
        }
        model.addAttribute("region", regionOptional.get());
        model.addAttribute("approvisionnements", others2);
        return "region/other/approvisionnement";
    }

    @GetMapping("/region/transport/{regionId}")
    public String transport(Model model, @PathVariable Long regionId, HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        session.setAttribute("regionId", regionId);
        List<Other> others= otherRepository.findAllByRegion(regionOptional.get().getRegionId());
        List<Other> others1= otherRepository.findAllByType("Transports");
        List<Other> others2= new ArrayList<>();
        for (Other other : others){
            for (int i=0; i<others1.size();i++){
                if (other.getOtherId().equals(others1.get(i).getOtherId())){
                    others2.add(other);
                }
            }
        }
        model.addAttribute("region", regionOptional.get());
        model.addAttribute("transports", others2);
        return "region/other/transport";
    }


}
