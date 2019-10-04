package com.derteuffel.restController;

import com.derteuffel.data.Bourse;
import com.derteuffel.repository.BourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by derteuffel on 10/06/2019.
 */
@RestController
@RequestMapping("/mobile/bourse")
public class BourseRestController {

    @Autowired
    private BourseRepository bourseRepository;


    @GetMapping("/bourses")
    public List<Bourse> bourses(){
        List<Bourse> bourses=bourseRepository.findByStatusAndSuprimeOrderByBourseIdDesc(true,false);
    return bourses;
    }
}
