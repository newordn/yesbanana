package com.derteuffel.controller;

import com.derteuffel.data.Lesson;
import com.derteuffel.data.Period;
import com.derteuffel.repository.LessonRepository;
import com.derteuffel.repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Controller
@RequestMapping("/period")
public class PeriodController {

    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping("/period/get/{periodId}")
    public String getPeriod(Model model, @PathVariable Long periodId, HttpSession session){
        Optional<Period> optional= periodRepository.findById(periodId);
        List<Lesson> lessons= new ArrayList<>();
        List<Lesson> lessons1= lessonRepository.findAllByStatusOrderByLessonIdDesc(true);
        List<Lesson> lessonList= lessonRepository.findAllByPeriod(optional.get().getPeriodId());
        for (Lesson lesson : lessonList){
            for (int i=0; i<lessons1.size();i++){
                if (lesson.getLessonId().equals(lessons1.get(i).getLessonId())){
                    lessons.add(lesson);
                }
            }
        }
        model.addAttribute("period", optional.get());
        model.addAttribute("lessons", lessons);
        return "period/period";
    }
}
