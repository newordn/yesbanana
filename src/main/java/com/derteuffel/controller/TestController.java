package com.derteuffel.controller;

import com.derteuffel.data.Test;
import com.derteuffel.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 07/12/2018.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @GetMapping("/all")
    public String findAll(Model model) {
        model.addAttribute("tests",testRepository.findAll());
        return "test/tests";
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("test", new Test());
        return "test/form";
    }

    @PostMapping("/save")
    public String save(Test test) {
          testRepository.save(test);
        return "redirect:/test/all";
    }

    public Optional<Test> findById(Long id) {
        return testRepository.findById(id);
    }

    public void deleteById(Long id) {
        testRepository.deleteById(id);
    }
}
