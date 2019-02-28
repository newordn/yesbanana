package com.derteuffel.controller;

import com.derteuffel.data.Post;
import com.derteuffel.data.Region;
import com.derteuffel.data.User;
import com.derteuffel.repository.PostRepository;
import com.derteuffel.repository.RegionRepository;
import com.derteuffel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by derteuffel on 13/12/2018.
 */
@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private UserRepository userRepository;


    public List<Post> findAll() {
        return postRepository.findAll();
    }


    @GetMapping("/other/form/procurements/{regionId}")
    public String other_procurements(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String procurements="Approvisionnement";
        model.addAttribute("procurements", procurements);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/other/procurements";
    }

    @GetMapping("/other/form/housing/{regionId}")
    public String other_housing(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String housing="Logements";
        model.addAttribute("housing", housing);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/other/housing";
    }
    @GetMapping("/other/form/transport/{regionId}")
    public String other_transport(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String transport="Transport";
        model.addAttribute("transport", transport);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/other/transport";
    }

    @GetMapping("/civic/form/{regionId}")
    public String civic(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/civic/post";
    }
/*
    @GetMapping("/detail/like/{postId}")
    public void likes(@PathVariable Long postId){
        Post post= postRepository.getOne(postId);
        int likes=post.getLikes();
        for (int i=0; i<= likes; i++){
            likes=likes+i;
        }
        System.out.println(likes);
        post.setLikes(likes);
        postRepository.save(post);
    }*/

    @GetMapping("/secondary/form/languages/{regionId}")
    public String secondary_languages(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String languages="Cours d'anglais et/ou francais";
        model.addAttribute("languages", languages);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/secondary/languages";
    }
    @GetMapping("/secondary/form/courses/{regionId}")
    public String secondary_courses(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String courses= "Cours d'appui";
        model.addAttribute("courses", courses);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/secondary/courses";
    }
    @GetMapping("/secondary/form/library/{regionId}")
    public String secondary_library(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String library= "Bibliotheque en ligne";
        model.addAttribute("library", library);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/secondary/library";
    }
    @GetMapping("/secondary/form/exam/{regionId}")
    public String secondary_exam(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String exam= "Préparation au baccalauréat";
        model.addAttribute("exam", exam);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/secondary/exam";
    }
    @GetMapping("/secondary/form/games/{regionId}")
    public String secondary_games(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String games= "Jeux educatif";
        model.addAttribute("games", games);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/secondary/games";
    }
    @GetMapping("/secondary/form/framing/{regionId}")
    public String secondary_framing(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String framing="Encadrement";
        model.addAttribute("framing", framing);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/secondary/framing";
    }
    @GetMapping("/secondary/form/transport/{regionId}")
    public String secondary_transport(Model model){
        String transport= "Transport securise";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("transport", transport);
        model.addAttribute("post", new Post());
        return "management/secondary/transport";
    }

    @GetMapping("/primary/form/transport/{regionId}")
    public String primary_transport(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String transport= "Transport securise";
        model.addAttribute("transport", transport);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/primary/transport";
    }


    @GetMapping("/primary/form/library/{regionId}")
    public String primary_library(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String library= "Bibliotheque en ligne";
        model.addAttribute("library", library);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/primary/library";
    }

    @GetMapping("/primary/form/languages/{regionId}")
    public String primary_languages(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String languages= "Cours d'anglais et/ou francais";
        model.addAttribute("languages", languages);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/primary/languages";
    }

    @GetMapping("/primary/form/hollidays/{regionId}")
    public String primary_hollidays(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String hollidays="Colonie de vacances";
        model.addAttribute("hollidays", hollidays);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/primary/hollidays";
    }

    @GetMapping("/primary/form/games/{regionId}")
    public String primary_games(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String games="Jeux educatif";
        model.addAttribute("games", games);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/primary/games";
    }

    @GetMapping("/primary/form/courses/{regionId}")
    public String primary_courses(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        String courses= "Cours d'appui";
        model.addAttribute("courses", courses);
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/primary/courses";
    }

    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
    }

    @PostMapping("/save")
    public String save(Post post, @RequestParam("files") MultipartFile[] files, HttpSession session) {
        List<FileUploadRespone> pieces= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArrayList<String> filesPaths = new ArrayList<String>();
        for(int i=0;i<pieces.size();i++)
        {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }

        System.out.println(filesPaths);
        post.setPieces(filesPaths);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        post.setUser(user);
        Region region=regionRepository.getOne((Long)session.getAttribute("regionId"));
        post.setRegion(region);
         postRepository.save(post);
        return "redirect:/management/region/visitor/"+(Long)session.getAttribute("regionId");
    }

    @GetMapping("/detail/{postId}")
    public String findById(Model model,@PathVariable Long postId) {
        Optional<Post> optional=postRepository.findById(postId);
        model.addAttribute("post", optional.get());
        return "management/post";
    }

    public void deleteById(Long aLong) {
        postRepository.deleteById(aLong);
    }
}
