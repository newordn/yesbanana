package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.data.PagerModel;
import com.derteuffel.repository.*;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by derteuffel on 03/12/2018.
 */
@Controller
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private OptionsRepository optionsRepository;
    @Autowired
    private TheseRepository theseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private PostRepository postRepository;
    List<String> countries= Arrays.asList(
            "Afghanistan",
            "Albania",
            "Algeria",
            "Andorra",
            "Angola",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bhutan",
            "Bolivia",
            "Bosnia and Herzegovina",
            "Botswana",
            "Brazil",
            "Brunei",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Cabo Verde",
            "Cambodia",
            "Cameroon",
            "Canada",
            "Central African Republic (CAR)",
            "Chad",
            "Chile",
            "China",
            "Colombia",
            "Comoros",
            " Democratic Republic of the Congo",
            "Republic of the Congo",
            "Costa Rica",
            "Cote d'Ivoire",
            "Croatia",
            "Cuba",
            "Cyprus",
            "Czech Republic",
            "Denmark",
            "Djibouti",
            "Dominica",
            "Dominican Republic",
            "Ecuador",
            "Egypt",
            "El Salvador",
            "Equatorial Guinea",
            "Eritrea",
            "Estonia",
            "Eswatini (formerly Swaziland)",
            "Ethiopia",
            "Fiji",
            "Finland",
            "France",
            "Gabon",
            "Gambia",
            "Georgia",
            "Germany",
            "Ghana",
            "Greece",
            "Grenada",
            "Guatemala",
            "Guinea",
            "Guinea-Bissau",
            "Guyana",
            "Haiti",
            "Honduras",
            "Hungary",
            "Iceland",
            "India",
            "Indonesia",
            "Iran",
            "Iraq",
            "Ireland",
            "Israel",
            "Italy",
            "Jamaica",
            "Japan",
            "Jordan",
            "Kazakhstan",
            "Kenya",
            "Kiribati",
            "Kosovo",
            "Kuwait",
            "Kyrgyzstan",
            "Laos",
            "Latvia",
            "Lebanon",
            "Lesotho",
            "Liberia",
            "Libya",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Macedonia (FYROM)",
            "Madagascar",
            "Malawi",
            "Malaysia",
            "Maldives",
            "Mali",
            "Malta",
            "Marshall Islands",
            "Mauritania",
            "Mauritius",
            "Mexico",
            "Micronesia",
            "Moldova",
            "Monaco",
            "Mongolia",
            "Montenegro",
            "Morocco",
            "Mozambique",
            "Myanmar (formerly Burma)",
            "Namibia",
            "Nauru",
            "Nepal",
            "Netherlands",
            "New Zealand",
            "Nicaragua",
            "Niger",
            "Nigeria",
            "North Korea",
            "Norway",
            "Oman",
            "Pakistan",
            "Palau",
            "Palestine",
            "Panama",
            "Papua New Guinea",
            "Paraguay",
            "Peru",
            "Philippines",
            "Poland",
            "Portugal",
            "Qatar",
            "Romania",
            "Russia",
            "Rwanda",
            "Saint Kitts and Nevis",
            "Saint Lucia",
            "Saint Vincent and the Grenadines",
            "Samoa",
            "San Marino",
            "Sao Tome",
            "Saudi Arabia",
            "Senegal",
            "Serbia",
            "Seychelles",
            "Sierra Leone",
            "Singapore",
            "Slovakia",
            "Slovenia",
            "Solomon Islands",
            "Somalia",
            "South Africa",
            "South Korea",
            "South Sudan",
            "Spain",
            "Sri Lanka",
            "Sudan",
            "Suriname",
            "Swaziland",
            "Sweden",
            "Switzerland",
            "Syria",
            "Taiwan",
            "Tajikistan",
            "Tanzania",
            "Thailand",
            "Timor-Leste",
            "Togo",
            "Tonga",
            "Trinidad and Tobago",
            "Tunisia",
            "Turkey",
            "Turkmenistan",
            "Tuvalu",
            "Uganda",
            "Ukraine",
            "United Arab Emirates",
            "United Kingdom",
            "United States of America",
            "Uruguay",
            "Uzbekistan",
            "Vanuatu",
            "Vatican City (Holy See)",
            "Venezuela",
            "Vietnam",
            "Yemen",
            "Zambia",
            "Zimbabwe"

    );


    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5,6,7,8};
    // options management methods
    @PostMapping("/options/form/save")
    public String save(Options options, Long facultyId) {
        Faculty faculty=facultyRepository.getOne(facultyId);
        options.setFaculty(faculty);
        options.setOptionsName(options.getOptionsName().toUpperCase());
        optionsRepository.save(options);
        return "redirect:/management/faculty/"+ faculty.getFacultyId();
    }
    @GetMapping("/options/{optionsId}")
    public String findById(Model model, @PathVariable Long optionsId) {
        Optional<Options> optionsOptional= optionsRepository.findById(optionsId);
        model.addAttribute("theses", theseRepository.findAllByOptionsOrderByTheseIdDesc(optionsOptional.get().getOptionsName().toLowerCase()));
        return "management/options";
    }

    @GetMapping("/options/delete/{optionsId}")
    public String deleteById(@PathVariable Long optionsId, HttpSession session) {
        Long facultyId= (Long)session.getAttribute("facultyId");
        session.setAttribute("userId", (Long)session.getAttribute("userId"));
        optionsRepository.deleteById(optionsId);
        return "redirect:/management/faculty/"+facultyId;
    }

    // university management methods
    @GetMapping("/delete/university/{universityId}")
    public String delete(@PathVariable Long universityId, HttpSession session) {
        Long regionId=(Long)session.getAttribute("regionId");
        session.setAttribute("userId", (Long)session.getAttribute("userId"));
        universityRepository.deleteById(universityId);
        return "redirect:/management/region/"+regionId;
    }

    @PostMapping("/university/form/save")
    public String save(University university, Long regionId) {
        Region region= regionRepository.getOne(regionId);
        university.setRegion(region);
        university.setUniversityName(university.getUniversityName().toUpperCase());
        universityRepository.save(university);
        return "redirect:/management/region/"+ region.getRegionId();
    }
    @GetMapping("/university/{universityId}")
    public String findOneUniversity(Model model, @PathVariable Long universityId, HttpSession session) {
        Optional<University> universityOptional=universityRepository.findById(universityId);
        List<These> theses=theseRepository.findAllByUniversityOrderByTheseIdDesc(universityOptional.get().getUniversityName());
        List<These> theses1=new ArrayList<>();
        List<Faculty> faculties=facultyRepository.findAllByUnniversity(universityOptional.get().getUniversityId());

        for (int i=0; i<faculties.size();i++){
            for (These these:theses){
                if (!these.getFaculty().toUpperCase().equals(faculties.get(i).getFacultyName().toUpperCase())){
                 theses1.add(these);
                }
            }
        }
        session.setAttribute("universityId",universityId);
        model.addAttribute("university", universityOptional.get());
        model.addAttribute("faculties", faculties);
        model.addAttribute("theses", theses1);
        model.addAttribute("faculty",new Faculty());
        return "management/university";
    }


    // region management methods
    @GetMapping("/region/parameter/{regionId}")
    public String findOne(Model model, @PathVariable Long regionId, HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        session.setAttribute("regionId", regionId);
        List<University> universities=universityRepository.findAllByRegion(regionOptional.get().getRegionId());
        List<These> theses=theseRepository.findAllByRegionsOrderByTheseIdDesc(regionOptional.get().getRegName().toUpperCase());
        List<These> theses1=new ArrayList<>();
        for (int i=0; i<universities.size();i++){
            for (These these:theses){
                if (!these.getUniversity().toUpperCase().equals(universities.get(i).getUniversityName().toUpperCase())){
                    theses1.add(these);
                }
            }
        }
        model.addAttribute("region",regionOptional.get());
        model.addAttribute("theses", theses1);
        model.addAttribute("universities", universities);
        model.addAttribute("university", new University());
        return "management/region";
    }
    // region management methods
    @GetMapping("/region/visitor/{regionId}")
    public String find(Model model, @PathVariable Long regionId, HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        session.setAttribute("regionId", regionId);
        List<User> nulls= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> visitors_region= userRepository.findAllByRegionOrderByUserIdDesc(regionOptional.get().getRegName());
        List<User> visitors= new ArrayList<>();
        for (User user: nulls){
            for (int a=0; a< visitors_region.size(); a++){
                if (user.getUserId().equals(visitors_region.get(a).getUserId())){
                    visitors.add(user);
                }
            }
        }
        System.out.println(visitors);
        List<User> users=userRepository.findAllByCategory("Chef des travaux");
        List<User> users1=userRepository.findAllByCategory("Assistant");
        List<User> users2=userRepository.findAllByCategory("Professeur");
        List<User> users5=userRepository.findAllByCategory("Primaire");
        List<User> users6=userRepository.findAllByCategory("Secondaire");
        List<User> users7=userRepository.findAllByCategory("Etudiant");
        List<User> users3=userRepository.findAllByDiplomOrderByUserIdDesc("Master1&2");
        List<User> users4=userRepository.findAllByDiplomOrderByUserIdDesc("Phd/Doctorat");
        List<User> chiefs=new ArrayList<>(), assistants=new ArrayList<>(),professors=new ArrayList<>(),
                masters=new ArrayList<>(), doctorats=new ArrayList<>(), primaries=new ArrayList<>(), secondaries=new ArrayList<>(),
                students=new ArrayList<>();
        // adding user in chiefs
        for (User user:visitors){
            for (int i=0;i<users.size();i++){
                if (user.getUserId().equals(users.get(i).getUserId())){
                    chiefs.add(user);
                }
            }
        }
        //adding user in assistants
        for (User user1:visitors){
            for (int p=0;p<users1.size();p++){
                if (user1.getUserId().equals(users1.get(p).getUserId())){
                    assistants.add(user1);
                }
            }
        }
        //adding user in professors
        for (User user2:visitors){
            for (int p=0;p<users2.size();p++){
                if (user2.getUserId().equals(users2.get(p).getUserId())){
                    professors.add(user2);
                }
            }
        }
        // adding user in masters
        for (User user3:visitors){
            for (int p=0;p<users3.size();p++){
                if (user3.getUserId().equals(users3.get(p).getUserId())){
                    masters.add(user3);
                }
            }
        }
        //adding user in phd/doctorat
        for (User user4:visitors){
            for (int p=0;p<users4.size();p++){
                if (user4.getUserId().equals(users4.get(p).getUserId())){
                    doctorats.add(user4);
                }
            }
        }
        //adding user in secondaries
        for (User user6:visitors){
            for (int p=0;p<users6.size();p++){
                if (user6.getUserId().equals(users6.get(p).getUserId())){
                    secondaries.add(user6);
                }
            }
        }
        //adding user in primaries
        for (User user5:visitors){
            for (int p=0;p<users5.size();p++){
                if (user5.getUserId().equals(users5.get(p).getUserId())){
                    primaries.add(user5);
                }
            }
        }
        //adding user in students
        for (User user7:visitors){
            for (int p=0;p<users7.size();p++){
                if (user7.getUserId().equals(users7.get(p).getUserId())){
                    students.add(user7);
                }
            }
        }

        model.addAttribute("chiefs", chiefs);
        model.addAttribute("assistants", assistants);
        model.addAttribute("primaries", primaries);
        model.addAttribute("secondaries", secondaries);
        model.addAttribute("students", students);
        model.addAttribute("professors", professors);
        model.addAttribute("masters", masters);
        model.addAttribute("doctorats", doctorats);

        model.addAttribute("region",regionOptional.get());
        return "management/visitors";
    }

    @PostMapping("/region/form/save")
    public String saveRegion(Region region, HttpSession session) {
        Long countryId=(Long) session.getAttribute("countryId");
        region.setCountry(countryRepository.getOne(countryId));
        region.setRegName(region.getRegName().toUpperCase());
        regionRepository.save(region);
        return "redirect:/management/country/parameter/"+ countryId;
    }
    @GetMapping("/delete/region/{regionId}")
    public String deleteRegion(@PathVariable Long regionId, HttpSession session) {
        Long countryId= (Long)session.getAttribute("countryId");
        session.setAttribute("userId",(Long)session.getAttribute("userId"));
        regionRepository.deleteById(regionId);

        return "redirect:/management/country/"+countryId;
    }

    // faculty management methods
    @GetMapping("/faculty/{facultyId}")
    public String findOneFaculty(Model model, @PathVariable Long facultyId, HttpSession session) {
        session.setAttribute("facultyId", facultyId);
        Optional<Faculty> facultyOptional= facultyRepository.findById(facultyId);
        List<Options> optionses=optionsRepository.findAllByFaculty(facultyOptional.get().getFacultyId());
        List<These> theses= theseRepository.findAllByFacultyOrderByTheseIdDesc(facultyOptional.get().getFacultyName().toUpperCase());
        List<These> theses1= new ArrayList<>();
        for (int i=0; i<optionses.size();i++){
            for (These these:theses){
                if (!these.getOptions().toUpperCase().equals(optionses.get(i).getOptionsName().toUpperCase())){
                    theses1.add(these);
                }
            }
        }
        model.addAttribute("faculty", facultyOptional.get());
        model.addAttribute("theses",theses1);
        model.addAttribute("optionses", optionses);
        model.addAttribute("options",new Options());
        return "management/faculty";
    }

    @PostMapping("/faculty/form/save")
    public String save(Faculty faculty, Long universityId) {
        University university= universityRepository.getOne(universityId);
        faculty.setUniversity(university);
        facultyRepository.save(faculty);
        return "redirect:/management/university/"+ university.getUniversityId();
    }

    @GetMapping("/delete/faculty/{facultyId}")
    public String deletefaculty(@PathVariable Long facultyId, HttpSession session) {
        Long universityId= (Long)session.getAttribute("universityId");
        session.setAttribute("userId", (Long)session.getAttribute("userId"));
        facultyRepository.deleteById(facultyId);
        return "redirect:/management/university/"+ universityId;
    }

    // country management methods
    @GetMapping("/countries/parameter")
    public String findAllCountry(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        session.setAttribute("userId", user.getUserId());
        List<Country> countries1= countryRepository.findAll();
        model.addAttribute("countries1", countries1);
        model.addAttribute("countries", countries);
        model.addAttribute("country", new Country());
        return "management/countries";
    }
    // country management methods
    @GetMapping("/countries/visitor")
    public String findAllCountry1(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        session.setAttribute("userId", user.getUserId());
        List<Country> countries= countryRepository.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("country", new Country());
        return "management/countries1";
    }
    @PostMapping("/country/form/save")
    public String save(Country country) {
        System.out.println(country.getCountryId());
        countryRepository.save(country);
        return "redirect:/management/countries/parameter";
    }

    @GetMapping("/country/parameter/{countryId}")
    public String findparameter(Model model,@PathVariable Long countryId, HttpSession session) {
        session.setAttribute("countryId",countryId);
        Optional<Country> countryOptional=countryRepository.findById(countryId);
        List<Region> regions= regionRepository.findAllByCountry(countryOptional.get().getCountryId());
        List<These> theses=theseRepository.findAllByCountryOrderByTheseIdDesc(countryOptional.get().getCountryName().toUpperCase());
        System.out.println(theses);
        model.addAttribute("theses", theses);
        model.addAttribute("country", countryOptional.get());
        model.addAttribute("regions", regions);
        model.addAttribute("region",new Region());

        return "management/country";
    }
    @GetMapping("/country/visitor/{countryId}")
    public String findvisitor(Model model,@PathVariable Long countryId, HttpSession session) {
        session.setAttribute("countryId",countryId);
        Optional<Country> countryOptional=countryRepository.findById(countryId);
        List<Region> regions= regionRepository.findAllByCountry(countryOptional.get().getCountryId());
        List<These> theses=theseRepository.findAllByCountryOrderByTheseIdDesc(countryOptional.get().getCountryName().toUpperCase());
        List<These> theses1=new ArrayList<>();
        for (int i=0; i<regions.size();i++){
            for (These these:theses){
                if (!these.getRegions().toUpperCase().equals(regions.get(i).getRegName().toUpperCase())){
                    theses1.add(these);
                }
            }
        }
        model.addAttribute("theses", theses1);
        model.addAttribute("country", countryOptional.get());
        model.addAttribute("regions", regions);
        model.addAttribute("region",new Region());

        return "management/country1";
    }
    @GetMapping("/country/delete/{countryId}")
    public String deleteCountry(@PathVariable Long countryId, HttpSession session) {

        session.setAttribute("userId", (Long)session.getAttribute("userId"));
        countryRepository.deleteById(countryId);

        return "redirect:/management/countries";
    }


    @GetMapping("/visitor/detail/{userId}")
    public String visitor(Model model, @PathVariable Long userId){
        User user= userService.getById(userId);
        model.addAttribute("user", user);
        return "management/visitor";
    }

    @GetMapping("/school/primary/{regionId}")
    public String education(Model model, HttpSession session){
         List<Post> post_by_region= postRepository.findAllByRegion((Long)session.getAttribute("regionId"));
        List<Post> post_by_level= postRepository.findAllByNiveauOrderByPostIdDesc(1);
        List<Post> post_region_by_niveau= new ArrayList<>();
        for (Post post:post_by_region){
            for (int i=0; i< post_by_level.size();i++ ){
                if (post.getPostId().equals(post_by_level.get(i).getPostId())){
                    post_region_by_niveau.add(post);
                }
            }

        }
        List<Post> posts_by_courses= new ArrayList<>(),posts_by_hollidays= new ArrayList<>(), posts_by_games= new ArrayList<>(),
                posts_by_languages= new ArrayList<>(),posts_by_transports= new ArrayList<>(), posts_by_libraries= new ArrayList<>();

        List<Post> coursesPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Cours d'appui");
        List<Post> librariesPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Bibliotheque en ligne");
        List<Post> languagesPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Cours de langue");
        List<Post> hollidaysPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Colonie des vacances");
        List<Post> gamesPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Jeux educatif");
        List<Post> transportPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Transport securise");


        for (Post post:post_region_by_niveau){
            for (int c=0; c< coursesPosts.size();c++ ){
                if (post.getPostId().equals(coursesPosts.get(c).getPostId())){
                    posts_by_courses.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int l=0; l< librariesPosts.size();l++ ){
                if (post.getPostId().equals(librariesPosts.get(l).getPostId())){
                    posts_by_libraries.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int g=0; g< gamesPosts.size();g++ ){
                if (post.getPostId().equals(gamesPosts.get(g).getPostId())){
                    posts_by_games.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int h=0; h< hollidaysPosts.size();h++ ){
                if (post.getPostId().equals(hollidaysPosts.get(h).getPostId())){
                    posts_by_hollidays.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int la=0; la< languagesPosts.size();la++ ){
                if (post.getPostId().equals(languagesPosts.get(la).getPostId())){
                    posts_by_languages.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int t=0; t< transportPosts.size();t++ ){
                if (post.getPostId().equals(transportPosts.get(t).getPostId())){
                    posts_by_transports.add(post);
                }
            }

        }

        model.addAttribute("courses_posts", posts_by_courses);
        model.addAttribute("courses_transports", posts_by_transports);
        model.addAttribute("courses_hollidays", posts_by_hollidays);
        model.addAttribute("courses_games", posts_by_games);
        model.addAttribute("courses_languages", posts_by_languages);
        model.addAttribute("courses_libraries", posts_by_libraries);

        return "management/coursesAddingPrimary";
    }

    @GetMapping("/school/secondary/{regionId}")
    public String education1(Model model, HttpSession session){
        List<Post> post_by_region= postRepository.findAllByRegion((Long)session.getAttribute("regionId"));
        List<Post> post_by_level= postRepository.findAllByNiveauOrderByPostIdDesc(2);
        List<Post> post_region_by_niveau= new ArrayList<>();
        for (Post post:post_by_region){
            for (int i=0; i< post_by_level.size();i++ ){
                if (post.getPostId().equals(post_by_level.get(i).getPostId())){
                    post_region_by_niveau.add(post);
                }
            }

        }
        List<Post> posts_by_courses= new ArrayList<>(),posts_by_framings= new ArrayList<>(),posts_by_exams= new ArrayList<>(),
                posts_by_games= new ArrayList<>(), posts_by_languages= new ArrayList<>(),posts_by_transports= new ArrayList<>(),
                posts_by_libraries= new ArrayList<>();

        List<Post> coursesPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Cours d'appui");
        List<Post> librariesPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Bibliotheque en ligne");
        List<Post> languagesPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Cours de langue");
        List<Post> framingsPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Loisirs");
        List<Post> examsPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Preparation au baccalaureat");
        List<Post> gamesPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Jeux educatif");
        List<Post> transportPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Transport securise");


        for (Post post:post_region_by_niveau){
            for (int c=0; c< coursesPosts.size();c++ ){
                if (post.getPostId().equals(coursesPosts.get(c).getPostId())){
                    posts_by_courses.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int l=0; l< librariesPosts.size();l++ ){
                if (post.getPostId().equals(librariesPosts.get(l).getPostId())){
                    posts_by_libraries.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int g=0; g< gamesPosts.size();g++ ){
                if (post.getPostId().equals(gamesPosts.get(g).getPostId())){
                    posts_by_games.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int h=0; h< framingsPosts.size();h++ ){
                if (post.getPostId().equals(framingsPosts.get(h).getPostId())){
                    posts_by_framings.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int ex=0; ex< examsPosts.size();ex++ ){
                if (post.getPostId().equals(examsPosts.get(ex).getPostId())){
                    posts_by_exams.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int la=0; la< languagesPosts.size();la++ ){
                if (post.getPostId().equals(languagesPosts.get(la).getPostId())){
                    posts_by_languages.add(post);
                }
            }

        }
        for (Post post:post_region_by_niveau){
            for (int t=0; t< transportPosts.size();t++ ){
                if (post.getPostId().equals(transportPosts.get(t).getPostId())){
                    posts_by_transports.add(post);
                }
            }

        }

        model.addAttribute("courses_posts", posts_by_courses);
        model.addAttribute("courses_transports", posts_by_transports);
        model.addAttribute("courses_framings", posts_by_framings);
        model.addAttribute("courses_exams", posts_by_exams);
        model.addAttribute("courses_games", posts_by_games);
        model.addAttribute("courses_languages", posts_by_languages);
        model.addAttribute("courses_libraries", posts_by_libraries);

        return "management/coursesAddingSecondary";
    }

    @GetMapping("/school/civic/{regionId}")
    public String education2(Model model, HttpSession session){
        List<Post> post_by_region= postRepository.findAllByRegion((Long)session.getAttribute("regionId"));
        List<Post> post_by_level= postRepository.findAllByNiveauOrderByPostIdDesc(3);
        List<Post> post_region_by_niveau= new ArrayList<>();
        for (Post post:post_by_region){
            for (int i=0; i< post_by_level.size();i++ ){
                if (post.getPostId().equals(post_by_level.get(i).getPostId())){
                    post_region_by_niveau.add(post);
                }
            }

        }
        List<Post> posts_by_civics= new ArrayList<>();
        List<Post> civicPosts=postRepository.findAllByCategoryOrderByPostIdDesc("Education civique");

        for (Post post:post_region_by_niveau){
            for (int c=0; c< civicPosts.size();c++ ){
                if (post.getPostId().equals(civicPosts.get(c).getPostId())){
                    posts_by_civics.add(post);
                }
            }

        }

        model.addAttribute("civics_posts", posts_by_civics);
        return "management/civic";
    }

    @GetMapping("/other/{regionId}")
    public String other(Model model, HttpSession session){
        List<Post> post_by_region= postRepository.findAllByRegion((Long)session.getAttribute("regionId"));
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

        model.addAttribute("posts_housings", posts_by_housings);
        model.addAttribute("posts_transports", posts_by_transport);
        model.addAttribute("posts_procurements", posts_by_procurements);
        return "management/other";
    }

    //templates.course management methods start
    @GetMapping("/course/form")
    public String addCourse(Model model){
        model.addAttribute("course", new Course());
        return "management/course/form";
    }

    @GetMapping("/course/language")
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
        return "management/course/one/languages";
    }

    @GetMapping("/course/admin")
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
        return "management/course/one/admin";
    }

    @GetMapping("/course/it")
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
        return "management/course/one/its";
    }

    @GetMapping("/course/logistique")
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
        return "management/course/one/logistiques";
    }

    @GetMapping("/course/protection")
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
        return "management/course/one/protections";
    }

    @GetMapping("/course/resources")
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
        model.addAttribute("courses", res_hum);
        model.addAttribute("pager", pager5);
        return "management/course/one/resources";
    }

    @GetMapping("/course/leadership")
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
        return "management/course/one/leaderships";
    }

    @GetMapping("/course/management")
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
        return "management/course/one/managements";
    }

    @GetMapping("/course/wash")
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
        return "management/course/one/washs";
    }

    @GetMapping("/course/all")
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
        return "management/course/courses";
    }

    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
    }

    @PostMapping(value = "/course/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveCourse(Course course, @RequestParam("files") MultipartFile files){

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
        course.setPieces(filesPaths);

        Course course1=courseRepository.save(course);
        return "redirect:/management/course/get/"+ course1.getCourseId();
    }

    @GetMapping("/delete/{courseId}")
    public String deleteCourse( @PathVariable Long courseId){
        courseRepository.deleteById(courseId);
        return "redirect:/management/course/all";
    }

    @GetMapping("/course/get/{courseId}")
    public String findOneCourse(Model model,@PathVariable Long courseId, HttpSession session) {
        session.setAttribute("courseId",courseId);
        Optional<Course> optional= courseRepository.findById(courseId);
        List<Period> periods= periodRepository.findAllByCourses(optional.get().getCourseId());
        model.addAttribute("templates/course", optional.get());
        model.addAttribute("periods", periods);
        return "management/course/course";
    }

    //templates.course management methods end

    // period management methods start
    @GetMapping("/period/all")
    public String findAllPeriodByCourse(Model model, @PathVariable Long courseId){

        List<Period> periods= periodRepository.findAllByCourses(courseId);
        model.addAttribute("periods", periods);
        return "management/period/periods";
    }

    @GetMapping("/period/get/{periodId}")
    public String getOnePeriod(Model model, @PathVariable Long periodId, HttpSession session){
        session.setAttribute("periodId", periodId);
        Optional<Period> optional= periodRepository.findById(periodId);
        List<Lesson> lessonList=lessonRepository.findAllByPeriod(optional.get().getPeriodId());
        model.addAttribute("lessons", lessonList);
        model.addAttribute("period", optional.get());
        return "management/period/period";
    }

    @GetMapping("/period/form")
    public String periodForm(Model model){
        model.addAttribute("period",new Period());
        return "management/period/form";
    }
    @PostMapping("/period/save")
    public String periodSaved(Period period, HttpSession session){
        Long courseId= (Long)session.getAttribute("courseId");
        Optional<Course> course= courseRepository.findById(courseId);
        period.setCourse(course.get());
        Period period1=periodRepository.save(period);
        return "redirect:/management/period/get/"+ period1.getPeriodId();
    }

    @DeleteMapping("/period/delete/{periodId}")
    public String periodDelete(@PathVariable Long periodId, HttpSession session){
        periodRepository.deleteById(periodId);
        return "redirect:/management/course/get/"+(Long)session.getAttribute("courseId");
    }
    // period management methods end

    //Lesson management methods Start
    @GetMapping("/lesson/get/{lessonId}")
    public String getOneLesson(Model model, @PathVariable Long lessonId){
        Optional<Lesson> optional= lessonRepository.findById(lessonId);
        model.addAttribute("lesson", optional.get());
        return "management/lesson/lesson";
    }

    @GetMapping("/lesson/form")
    public String lessonForm(Model model){
        model.addAttribute("lesson", new Lesson());
        return "management/lesson/form";
    }

    @PostMapping(value = "/lesson/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String lessonSave(Lesson lesson, HttpSession session, @RequestParam("files") MultipartFile files){
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
        lesson.setPieces(filesPaths);
        Period period= periodRepository.getOne((Long)session.getAttribute("periodId"));
        lesson.setPeriod(period);

        Lesson lesson1= lessonRepository.save(lesson);
        return "redirect:/management/period/get/"+lesson1.getLessonId();
    }

    @DeleteMapping("/lesson/delete/{lessonId}")
    public String lessonDelete(@PathVariable Long lessonId, HttpSession session){
        lessonRepository.deleteById(lessonId);
        return "redirect:/management/period/get/"+(Long)session.getAttribute("periodId");
    }
    //Lesson management methods End
}











