package com.derteuffel.controller;

import com.derteuffel.data.Course;
import com.derteuffel.data.Lesson;
import com.derteuffel.data.Period;
import com.derteuffel.repository.CourseRepository;
import com.derteuffel.repository.LessonRepository;
import com.derteuffel.repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping("/all")
    public String findAll(Model model) {
        List<Course> admin_fin= courseRepository.findAllByDomainOrderByCourseIdDesc("administration et finance");
        List<Course> ang_fran= courseRepository.findAllByDomainOrderByCourseIdDesc("anglais et/ou francais");
        List<Course> it= courseRepository.findAllByDomainOrderByCourseIdDesc("it");
        List<Course> logistiques= courseRepository.findAllByDomainOrderByCourseIdDesc("logistiques");
        List<Course> protections= courseRepository.findAllByDomainOrderByCourseIdDesc("protection");
        List<Course> res_hum= courseRepository.findAllByDomainOrderByCourseIdDesc("resources humaines");
        List<Course> leaderships= courseRepository.findAllByDomainOrderByCourseIdDesc("leadership");
        List<Course> managements= courseRepository.findAllByDomainOrderByCourseIdDesc("management");
        List<Course> wash= courseRepository.findAllByDomainOrderByCourseIdDesc("wash");
        model.addAttribute("courses1", admin_fin);
        model.addAttribute("courses2", ang_fran);
        model.addAttribute("courses3", it);
        model.addAttribute("courses4", logistiques);
        model.addAttribute("courses5", protections);
        model.addAttribute("courses6", res_hum);
        model.addAttribute("courses7", leaderships);
        model.addAttribute("courses8", managements);
        model.addAttribute("courses9", wash);
        return "course/courses";
    }


    @GetMapping("/get/{courseId}")
    public String findById(Model model, @PathVariable Long courseId, HttpSession session) {
        session.setAttribute("courseId", courseId);
        Optional<Course> optional= courseRepository.findById(courseId);
        List<Period> periods= periodRepository.findAllByCourses(optional.get().getCourseId());
        List<Lesson> lessons= new ArrayList<>();
        for (Period period: periods){
            List<Lesson>lessonList= lessonRepository.findAllByPeriod(period.getPeriodId());
            for (int i=0; i<lessonList.size(); i++){
                lessons.add(lessonList.get(i));
            }
        }
        model.addAttribute("lessons",lessons);
        model.addAttribute("course", optional.get());
        model.addAttribute("periods", periods);
        return "course/course";
    }

}
