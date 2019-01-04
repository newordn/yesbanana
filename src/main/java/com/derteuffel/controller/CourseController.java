package com.derteuffel.controller;

import com.derteuffel.data.Course;
import com.derteuffel.data.PagerModel;
import com.derteuffel.data.Period;
import com.derteuffel.repository.CourseRepository;
import com.derteuffel.repository.LessonRepository;
import com.derteuffel.repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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

    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5,6,7,8};

    @GetMapping("/get/{courseId}")
    public String findOneCourse(Model model,@PathVariable Long courseId, HttpSession session) {
        session.setAttribute("courseId",courseId);
        Optional<Course> optional= courseRepository.findById(courseId);
        List<Period> periods= periodRepository.findAllByCourses(optional.get().getCourseId());
        model.addAttribute("course", optional.get());
        model.addAttribute("periods", periods);
        return "course/course";
    }

    @GetMapping("/language")
    public String language(@RequestParam("pageSize") Optional<Integer> pageSize,
                           @RequestParam("page") Optional<Integer> page, Model model){
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        // evaluate page size
        model.addAttribute("selectedPageSize", evalPageSize);
        // add pages size
        model.addAttribute("pageSizes", PAGE_SIZES);
        //language
        Page<Course> ang_fran= courseRepository.findAllByDomainOrderByCourseIdDesc("anglais et/ou francais", new PageRequest(evalPage,evalPageSize));
        PagerModel pager1 = new PagerModel(ang_fran.getTotalPages(),ang_fran.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("pager", pager1);
        model.addAttribute("courses", ang_fran);
        return "course/one/languages";
    }

    @GetMapping("/admin")
    public String admin(@RequestParam("pageSize") Optional<Integer> pageSize,
                        @RequestParam("page") Optional<Integer> page, Model model){
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        // evaluate page size
        model.addAttribute("selectedPageSize", evalPageSize);
        // add pages size
        model.addAttribute("pageSizes", PAGE_SIZES);
        //language
        //templates.course admin finance
        Page<Course> admin_fin= courseRepository.findAllByDomainOrderByCourseIdDesc("administration et finance", new PageRequest(evalPage, evalPageSize));
        PagerModel pager = new PagerModel(admin_fin.getTotalPages(),admin_fin.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("pager", pager);
        model.addAttribute("courses", admin_fin);
        return "course/one/admin";
    }

    @GetMapping("/it")
    public String it(@RequestParam("pageSize") Optional<Integer> pageSize,
                     @RequestParam("page") Optional<Integer> page, Model model){
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        // evaluate page size
        model.addAttribute("selectedPageSize", evalPageSize);
        // add pages size
        model.addAttribute("pageSizes", PAGE_SIZES);
        //language
        //templates.course it
        Page<Course> it= courseRepository.findAllByDomainOrderByCourseIdDesc("it", new PageRequest(evalPage, evalPageSize));
        PagerModel pager2 = new PagerModel(it.getTotalPages(),it.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("pager", pager2);
        model.addAttribute("courses", it);
        return "course/one/its";
    }

    @GetMapping("/logistique")
    public String logistique(@RequestParam("pageSize") Optional<Integer> pageSize,
                             @RequestParam("page") Optional<Integer> page, Model model){
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        // evaluate page size
        model.addAttribute("selectedPageSize", evalPageSize);
        // add pages size
        model.addAttribute("pageSizes", PAGE_SIZES);
        // templates.course logistique
        Page<Course> logistiques= courseRepository.findAllByDomainOrderByCourseIdDesc("logistiques", new PageRequest(evalPage, evalPageSize));
        PagerModel pager3 = new PagerModel(logistiques.getTotalPages(),logistiques.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("courses", logistiques);
        model.addAttribute("pager", pager3);
        return "course/one/logistiques";
    }

    @GetMapping("/protection")
    public String protection(@RequestParam("pageSize") Optional<Integer> pageSize,
                             @RequestParam("page") Optional<Integer> page, Model model){
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        // evaluate page size
        model.addAttribute("selectedPageSize", evalPageSize);
        // add pages size
        model.addAttribute("pageSizes", PAGE_SIZES);
        //language
        // courses protection
        Page<Course> protections= courseRepository.findAllByDomainOrderByCourseIdDesc("protection", new PageRequest(evalPage, evalPageSize));
        PagerModel pager4 = new PagerModel(protections.getTotalPages(),protections.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("courses", protections);
        model.addAttribute("pager", pager4);
        return "course/one/protections";
    }

    @GetMapping("/resources")
    public String resources(@RequestParam("pageSize") Optional<Integer> pageSize,
                            @RequestParam("page") Optional<Integer> page, Model model){
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        // evaluate page size
        model.addAttribute("selectedPageSize", evalPageSize);
        // add pages size
        model.addAttribute("pageSizes", PAGE_SIZES);
        // courses resource humaine
        Page<Course> res_hum= courseRepository.findAllByDomainOrderByCourseIdDesc("resources humaines", new PageRequest(evalPage,evalPageSize));
        PagerModel pager5 = new PagerModel(res_hum.getTotalPages(),res_hum.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("courses2", res_hum);
        model.addAttribute("pager", pager5);
        return "course/one/resources";
    }

    @GetMapping("/leadership")
    public String leadership(@RequestParam("pageSize") Optional<Integer> pageSize,
                             @RequestParam("page") Optional<Integer> page, Model model){
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        // evaluate page size
        model.addAttribute("selectedPageSize", evalPageSize);
        // add pages size
        model.addAttribute("pageSizes", PAGE_SIZES);
        // courses leadership
        Page<Course> leaderships= courseRepository.findAllByDomainOrderByCourseIdDesc("leadership", new PageRequest(evalPage, evalPageSize));
        PagerModel pager6 = new PagerModel(leaderships.getTotalPages(),leaderships.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("courses", leaderships);
        model.addAttribute("pager", pager6);
        return "course/one/leaderships";
    }

    @GetMapping("/management")
    public String management(@RequestParam("pageSize") Optional<Integer> pageSize,
                             @RequestParam("page") Optional<Integer> page, Model model){
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        // evaluate page size
        model.addAttribute("selectedPageSize", evalPageSize);
        // add pages size
        model.addAttribute("pageSizes", PAGE_SIZES);
        // courses managements
        Page<Course> managements= courseRepository.findAllByDomainOrderByCourseIdDesc("management", new PageRequest(evalPage, evalPageSize));
        PagerModel pager7 = new PagerModel(managements.getTotalPages(),managements.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("courses", managements);
        model.addAttribute("pager", pager7);
        return "course/one/managements";
    }

    @GetMapping("/wash")
    public String wash(@RequestParam("pageSize") Optional<Integer> pageSize,
                       @RequestParam("page") Optional<Integer> page, Model model){
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        // evaluate page size
        model.addAttribute("selectedPageSize", evalPageSize);
        // add pages size
        model.addAttribute("pageSizes", PAGE_SIZES);
        // courses wash
        Page<Course> wash= courseRepository.findAllByDomainOrderByCourseIdDesc("wash", new PageRequest(evalPage, evalPageSize));
        PagerModel pager8 = new PagerModel(wash.getTotalPages(),wash.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("courses", wash);
        model.addAttribute("pager", pager8);
        return "course/one/washs";
    }

    @GetMapping("/all")
    public String findList( Model model, @PageableDefault(size = 6) Pageable pageable) {
        Page<Course> courses1= courseRepository.findAllByDomainOrderByCourseIdDesc("administration et finance",pageable);
        //language
        Page<Course> courses2= courseRepository.findAllByDomainOrderByCourseIdDesc("anglais et/ou francais",pageable);
        //templates.course it
        Page<Course> courses3= courseRepository.findAllByDomainOrderByCourseIdDesc("it",pageable);
        // templates.course logistique
        Page<Course> courses4= courseRepository.findAllByDomainOrderByCourseIdDesc("logistiques", pageable);
        // courses protection
        Page<Course> courses5= courseRepository.findAllByDomainOrderByCourseIdDesc("protection", pageable);
        // courses resource humaine
        Page<Course> courses6= courseRepository.findAllByDomainOrderByCourseIdDesc("resources humaines", pageable);
        // courses leadership
        Page<Course> courses7= courseRepository.findAllByDomainOrderByCourseIdDesc("leadership", pageable);
        // courses managements
        Page<Course> courses8= courseRepository.findAllByDomainOrderByCourseIdDesc("management", pageable);
        // courses wash
        Page<Course> courses9= courseRepository.findAllByDomainOrderByCourseIdDesc("wash", pageable);

        model.addAttribute("courses1", courses1);
        model.addAttribute("courses1Size", courses1.getTotalElements());
        System.out.println(courses1.getTotalElements());
        System.out.println(courses1.getSize());
        System.out.println(courses1.getNumberOfElements());
        System.out.println(courses1.getNumber());
        model.addAttribute("courses2", courses2);
        model.addAttribute("courses2Size", courses2.getTotalElements());
        System.out.println(courses2.getNumberOfElements());
        model.addAttribute("courses3", courses3);
        model.addAttribute("courses3Size", courses3.getTotalElements());
        model.addAttribute("courses4", courses4);
        model.addAttribute("courses4Size", courses4.getTotalElements());
        model.addAttribute("courses5", courses5);
        model.addAttribute("courses5Size", courses5.getTotalElements());
        model.addAttribute("courses6", courses6);
        model.addAttribute("courses6Size", courses6.getTotalElements());
        model.addAttribute("courses7", courses7);
        model.addAttribute("courses7Size", courses7.getTotalElements());
        model.addAttribute("courses8", courses8);
        model.addAttribute("courses8Size", courses8.getTotalElements());
        model.addAttribute("courses9", courses9);
        model.addAttribute("courses9Size", courses9.getTotalElements());
        return "course/courses";
    }

}
