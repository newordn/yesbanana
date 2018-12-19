package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.*;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 03/12/2018.
 */
@Controller
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    private CountryRepository countryRepository;
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

}
