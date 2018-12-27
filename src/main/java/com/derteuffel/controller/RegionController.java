package com.derteuffel.controller;

import com.derteuffel.data.Country;
import com.derteuffel.data.Post;
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

    //region crud methods
/*
    @GetMapping("/regions")
    public String findAll(Model model) {
        List<Region> regions= regionRepository.findAll();
        model.addAttribute("regions", regions);
        return "region/regions";
    }
*/


    @GetMapping("/region/{regionId}")
    public String findById(Model model, @PathVariable Long regionId,HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        List<University> universities=universityRepository.findAllByRegion(regionOptional.get().getRegionId());
        List<Post> post_by_region= postRepository.findAllByRegion(regionOptional.get().getRegionId());
        List<Post> post_by_level= postRepository.findAllByNiveauOrderByPostIdDesc(4);
        List<Post> post_region_by_niveau= new ArrayList<>();
        for (Post post:post_by_region){
            for (int i=0; i< post_by_level.size();i++ ){
                if (post.getPostId().equals(post_by_level.get(i).getPostId())){
                    post_region_by_niveau.add(post);
                }
            }

        }
        List<Post> posts_by_housings= new ArrayList<>(), posts_by_procurements=new ArrayList<>(), posts_by_transport=new ArrayList<>();
        List<Post> housingPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Logements");
        List<Post> procurementPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Approvisionnement");
        List<Post> transportPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Transport");

        for (Post post:post_region_by_niveau){
            for (int h=0; h< housingPosts.size();h++ ){
                if (post.getPostId().equals(housingPosts.get(h).getPostId())){
                    posts_by_housings.add(post);
                }
            }

        }

        for (Post post:post_region_by_niveau){
            for (int p=0; p< procurementPosts.size();p++ ){
                if (post.getPostId().equals(procurementPosts.get(p).getPostId())){
                    posts_by_procurements.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int t=0; t< transportPosts.size();t++ ){
                if (post.getPostId().equals(transportPosts.get(t).getPostId())){
                    posts_by_transport.add(post);
                }
            }

        }
        System.out.println(posts_by_housings);
        System.out.println(posts_by_transport);
        System.out.println(posts_by_procurements);
        model.addAttribute("region",regionOptional.get());
        model.addAttribute("universities", universities);
        model.addAttribute("posts_housings", posts_by_housings);
        model.addAttribute("posts_transports", posts_by_transport);
        model.addAttribute("posts_procurements", posts_by_procurements);
        return "region/element";
    }

}
