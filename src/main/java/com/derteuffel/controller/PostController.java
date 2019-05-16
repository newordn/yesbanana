package com.derteuffel.controller;

import com.derteuffel.data.Post;
import com.derteuffel.data.Region;
import com.derteuffel.data.User;
import com.derteuffel.repository.PostRepository;
import com.derteuffel.repository.RegionRepository;
import com.derteuffel.repository.UserRepository;
import com.derteuffel.service.UserService;
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

    @Autowired
    private UserService userService;


    public List<Post> findAll() {
        return postRepository.findAll();
    }


    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
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
