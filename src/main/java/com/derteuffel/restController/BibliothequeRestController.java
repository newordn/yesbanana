package com.derteuffel.restController;

import com.derteuffel.data.*;
import com.derteuffel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by derteuffel on 15/06/2019.
 */
@RestController
@RequestMapping("/mobile")
public class BibliothequeRestController {

    @Autowired
    private SyllabusRepository syllabusRepository;
    @Autowired
    private BibliographyRepository bibliographyRepository;
    @Autowired
    private StudentWorkRepository studentWorkRepository;
    @Autowired
    private TheseRepository theseRepository;

    @GetMapping("/syllabuses")
    public List<Syllabus> syllabuses(){
        List<Syllabus> syllabuses= syllabusRepository.findBySuprimeeAndStatusOrderBySyllabusIdDesc(true,true);
        return syllabuses;
    }

    @GetMapping("/livres")
    public List<Bibliography> livres(){
        List<Bibliography> bibliographies=bibliographyRepository.findAllByDisponibility(true);

        return bibliographies;
    }

    @GetMapping("/studentWorks")
    public List<StudentWork> studentWorks(){
        List<StudentWork> studentsWorks=studentWorkRepository.findByStatusOrderByStudentWorkIdDesc(true);
        return studentsWorks;
    }

    @GetMapping("/theses")
    public List<These> theses(){
        List<These>theses=theseRepository.findByStatesAndStatusOrderByTheseIdDesc(true,true);
        return theses;
    }

}
