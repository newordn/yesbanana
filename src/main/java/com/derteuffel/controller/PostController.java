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


    @GetMapping("/other/form/{regionId}")
    public String other(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/other/post";
    }

    @GetMapping("/civic/form/{regionId}")
    public String civic(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/civic/post";
    }

    @GetMapping("/secondary/form/{regionId}")
    public String secondary(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/secondary/post";
    }

    @GetMapping("/primary/form/{regionId}")
    public String primary(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("post", new Post());
        return "management/primary/post";
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
        User user=userRepository.findByEmail(auth.getName());
        post.setUser(user);
        Region region=regionRepository.getOne((Long)session.getAttribute("regionId"));
        post.setRegion(region);
         postRepository.save(post);
        return "redirect:/management/region/visitor/"+(Long)session.getAttribute("regionId");
    }

    public Optional<Post> findById(Long aLong) {
        return postRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        postRepository.deleteById(aLong);
    }
}
