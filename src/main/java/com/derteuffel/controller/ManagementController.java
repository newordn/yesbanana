package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.data.PagerModel;
import com.derteuffel.repository.*;
import com.derteuffel.service.MailService;
import com.derteuffel.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mult;
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

import javax.print.attribute.standard.MediaSize;
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
    private EventRepository eventRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private OtherRepository otherRepository;
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
        options.setOptionsName(options.getOptionsName());
        optionsRepository.save(options);
        return "redirect:/management/faculty/"+ faculty.getFacultyId();
    }
    @GetMapping("/options/{optionsId}")
    public String findById(Model model, @PathVariable Long optionsId) {
        Optional<Options> optionsOptional= optionsRepository.findById(optionsId);
        model.addAttribute("theses", theseRepository.findAllByOptionsOrderByTheseIdDesc(optionsOptional.get().getOptionsName()));
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
        return "redirect:/management/region/university/"+ region.getRegionId();
    }
    @GetMapping("/university/{universityId}")
    public String findOneUniversity(Model model, @PathVariable Long universityId, HttpSession session) {
        Optional<University> universityOptional=universityRepository.findById(universityId);
        List<These> theses=theseRepository.findAllByUniversityOrderByTheseIdDesc(universityOptional.get().getUniversityName());
        List<Faculty> faculties=facultyRepository.findAllByUnniversity(universityOptional.get().getUniversityId());
        model.addAttribute("theses", theses);
        session.setAttribute("universityId",universityId);
        model.addAttribute("university", universityOptional.get());
        model.addAttribute("faculties", faculties);
        model.addAttribute("faculty",new Faculty());
        return "management/university";
    }

    //visitor management functions

    @GetMapping("/chiefs")
    public  String chiefs(){
        return "user/chiefs";
    }


    @GetMapping("/student")
    public  String student(){
        return "user/student";
    }


    @GetMapping("/assistants")
    public  String assistants(){
        return "user/assistants";
    }


    @GetMapping("/professeurs")
    public  String professeurs(){
        return "user/professeurs";
    }


    @GetMapping("/primaire")
    public  String primary(){
        return "user/primaires";
    }


    @GetMapping("/secondaire")
    public  String secondaire(){
        return "user/secondaires";
    }

    @GetMapping("/master")
    public  String master(){
        return "user/master";
    }

    @GetMapping("/doctorat")
    public  String doctorat(){
        return "user/doctorat";
    }

    // region management methods
    @GetMapping("/region/university/{regionId}")
    public String university(Model model, @PathVariable Long regionId, HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        session.setAttribute("regionId", regionId);
        List<University> universities=universityRepository.findAllByRegion(regionOptional.get().getRegionId());
        List<These> theses=theseRepository.findAllByRegionsOrderByTheseIdDesc(regionOptional.get().getRegName());
        System.out.println(theses);
        System.out.println(universities);
        model.addAttribute("theses", theses);
        model.addAttribute("region",regionOptional.get());
        model.addAttribute("universities", universities);
        model.addAttribute("university", new University());
        return "management/region/region";
    }

    @GetMapping("/region/other/logement/{regionId}")
    public String other(Model model, @PathVariable Long regionId, HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        session.setAttribute("regionId", regionId);
        List<Other> others= otherRepository.findAllByRegion(regionOptional.get().getRegionId());
        List<Other> others1= otherRepository.findAllByType("Logement");
        List<Other> others2= new ArrayList<>();
        for (Other other : others){
            for (int i=0; i<others1.size();i++){
                if (other.getOtherId().equals(others1.get(i).getOtherId())){
                    others2.add(other);
                }
            }
        }
        model.addAttribute("region", regionOptional.get());
        model.addAttribute("logements", others2);
        return "management/region/other/logement";
    }

    @GetMapping("/region/other/approvisionnement/{regionId}")
    public String approvisionnement(Model model, @PathVariable Long regionId, HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        session.setAttribute("regionId", regionId);
        List<Other> others= otherRepository.findAllByRegion(regionOptional.get().getRegionId());
        List<Other> others1= otherRepository.findAllByType("Approvisionnement");
        List<Other> others2= new ArrayList<>();
        for (Other other : others){
            for (int i=0; i<others1.size();i++){
                if (other.getOtherId().equals(others1.get(i).getOtherId())){
                    others2.add(other);
                }
            }
        }
        model.addAttribute("region", regionOptional.get());
        model.addAttribute("approvisionnements", others2);
        return "management/region/other/approvisionnement";
    }

    @GetMapping("/region/other/transport/{regionId}")
    public String transport(Model model, @PathVariable Long regionId, HttpSession session) {
        Optional<Region> regionOptional=regionRepository.findById(regionId);
        session.setAttribute("regionId", regionId);
        List<Other> others= otherRepository.findAllByRegion(regionOptional.get().getRegionId());
        List<Other> others1= otherRepository.findAllByType("Transport");
        List<Other> others2= new ArrayList<>();
        for (Other other : others){
            for (int i=0; i<others1.size();i++){
                if (other.getOtherId().equals(others1.get(i).getOtherId())){
                    others2.add(other);
                }
            }
        }
        model.addAttribute("region", regionOptional.get());
        model.addAttribute("transports", others2);
        return "management/region/other/transport";
    }


    /** EDUCATION METHODS IMPLEMLENTS STARTS**/

    @GetMapping("/region/primaire/courses/{regionId}")
    public String courses(Model model, @PathVariable Long regionId, HttpSession session) {

        return "management/region/education/primary/course";
    }

    @GetMapping("/region/primaire/jeux/{regionId}")
    public String jeusx(Model model, @PathVariable Long regionId, HttpSession session) {

        return "management/region/education/primary/jeu";
    }
    @GetMapping("/region/primaire/colonie/{regionId}")
    public String colonie(Model model, @PathVariable Long regionId, HttpSession session) {

        return "management/region/education/primary/colonie";
    }
    @GetMapping("/region/primaire/transport/{regionId}")
    public String transportSecurise(Model model, @PathVariable Long regionId, HttpSession session) {

        return "management/region/education/primary/transport";
    }
    @GetMapping("/region/primaire/langue/{regionId}")
    public String langue(Model model, @PathVariable Long regionId, HttpSession session) {

        return "management/region/education/primary/langue";
    }
    @GetMapping("/region/primaire/bibliotheque/{regionId}")
    public String bibliotheque(Model model, @PathVariable Long regionId, HttpSession session) {

        return "management/region/education/primary/bibliotheque";
    }

    /** EDUCATION METHODS IMPLEMLENTS END**/


    @PostMapping("/region/form/save")
    public String saveRegion(Region region, HttpSession session) {
        Long countryId=(Long) session.getAttribute("countryId");
        region.setCountry(countryRepository.getOne(countryId));
        region.setRegName(region.getRegName());
        regionRepository.save(region);
        return "redirect:/management/country/university/"+ countryId;
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
        List<These> theses= theseRepository.findAllByFacultyOrderByTheseIdDesc(facultyOptional.get().getFacultyName());

        model.addAttribute("theses",theses);
        model.addAttribute("faculty", facultyOptional.get());
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
    @GetMapping("/countries/university")
    public String findAllCountry(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        session.setAttribute("userId", user.getUserId());
        List<Country> countries1= countryRepository.findAll();
        model.addAttribute("countries1", countries1);
        model.addAttribute("countries", countries);
        model.addAttribute("country", new Country());
        return "management/country/countries";
    }
    // country management methods
    @GetMapping("/countries/other")
    public String findAllCountry1(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        session.setAttribute("userId", user.getUserId());
        List<Country> countries= countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "management/country/countries1";
    }

    @GetMapping("/countries/education")
    public String findAllCountryForEducation(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        session.setAttribute("userId", user.getUserId());
        List<Country> countries= countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "management/country/countries2";
    }
    @PostMapping("/country/form/save")
    public String save(Country country) {
        System.out.println(country.getCountryId());
        countryRepository.save(country);
        return "redirect:/management/countries/university";
    }

    @GetMapping("/country/university/{countryId}")
    public String findparameter(Model model,@PathVariable Long countryId, HttpSession session) {
        session.setAttribute("countryId",countryId);
        Optional<Country> countryOptional=countryRepository.findById(countryId);
        List<Region> regions= regionRepository.findAllByCountry(countryOptional.get().getCountryId());
        List<These> theses=theseRepository.findAllByCountryOrderByTheseIdDesc(countryOptional.get().getCountryName());
        System.out.println(theses);
        model.addAttribute("theses", theses);
        model.addAttribute("country", countryOptional.get());
        model.addAttribute("regions", regions);
        model.addAttribute("region",new Region());
        return "management/country/country";
    }
    @GetMapping("/country/other/{countryId}")
    public String findvisitor(Model model,@PathVariable Long countryId, HttpSession session) {
        session.setAttribute("countryId",countryId);
        Optional<Country> countryOptional=countryRepository.findById(countryId);
        List<Region> regions= regionRepository.findAllByCountry(countryOptional.get().getCountryId());
        model.addAttribute("country", countryOptional.get());
        model.addAttribute("regions", regions);
        return "management/country/country1";
    }

    @GetMapping("/country/education/{countryId}")
    public String findCountryForEducation(Model model,@PathVariable Long countryId, HttpSession session) {
        session.setAttribute("countryId",countryId);
        Optional<Country> countryOptional=countryRepository.findById(countryId);
        List<Region> regions= regionRepository.findAllByCountry(countryOptional.get().getCountryId());
        model.addAttribute("country", countryOptional.get());
        model.addAttribute("regions", regions);
        return "management/country/country2";
    }
    @GetMapping("/country/delete/{countryId}")
    public String deleteCountry(@PathVariable Long countryId, HttpSession session) {

        session.setAttribute("userId", (Long)session.getAttribute("userId"));
        countryRepository.deleteById(countryId);

        return "redirect:/management/countries/university";
    }


    @GetMapping("/other/form")
    public String otherForm(Model model, HttpSession session){
        Long regionId= (Long)session.getAttribute("regionId");
        model.addAttribute("other",new Other());
        model.addAttribute("regionId", regionId);
        return "management/region/other/form";
    }

    @GetMapping("/other/update/{otherId}")
    public String otherUpdate(Model model,@PathVariable Long otherId){
        Other other= otherRepository.getOne(otherId);
        model.addAttribute("other", other);
        return "management/region/other/edit";
    }

    @PostMapping("/other/save")
    public String otherSave(HttpSession session, @RequestParam("files") MultipartFile[] files, Other other){

        List<FileUploadRespone> pieces= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArrayList<String> filesPaths = new ArrayList<String>();
        for(int i=0;i<pieces.size();i++)
        {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }
        other.setPieces(filesPaths);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        other.setUser(user);
        Region region=regionRepository.getOne((Long)session.getAttribute("regionId"));
        other.setRegion(region);
        Other other1=otherRepository.save(other);
        return "redirect:/management/region/other/"+other1.getType().toLowerCase()+"/"+(Long)session.getAttribute("regionId");
    }

    @PostMapping("/other/update/{otherId}")
    public String otherUpdate(HttpSession session, @RequestParam("files") MultipartFile[] files, Other other){

        List<FileUploadRespone> pieces= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArrayList<String> filesPaths = new ArrayList<String>();
        for(int i=0;i<pieces.size();i++)
        {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }
        other.setPieces(filesPaths);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        other.setUser(user);
        Region region=regionRepository.getOne((Long)session.getAttribute("regionId"));
        other.setRegion(region);
        Other other1=otherRepository.save(other);
        return "redirect:/management/region/other/"+other1.getType().toLowerCase()+"/"+(Long)session.getAttribute("regionId");
    }

    @DeleteMapping("/other/delete/{otherId}")
    public String otherRemove(@PathVariable Long otherId, HttpSession session){
        String type= otherRepository.getOne(otherId).getType();
        otherRepository.deleteById(otherId);

        return "redirect:/management/region/other/"+type.toLowerCase()+"/"+(Long)session.getAttribute("regionId");
    }

    //course management methods start
    @GetMapping("/course/form")
    public String addCourse(Model model){
        model.addAttribute("course", new Course());
        return "management/course/form";
    }

    @GetMapping("/event/language")
    public String languageEvents(@RequestParam("pageSize") Optional<Integer> pageSize,
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
        Page<Event> list1= eventRepository.findAllByType("anglais et/ou francais", new PageRequest(evalPage,evalPageSize));
        PagerModel pager1 = new PagerModel(list1.getTotalPages(),list1.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("pager", pager1);
        model.addAttribute("events", list1);
        return "management/event/course/one/languages";
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

    @GetMapping("/event/admin")
    public String adminEvents(@RequestParam("pageSize") Optional<Integer> pageSize,
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
        Page<Event> events= eventRepository.findAllByType("administration et finance", new PageRequest(evalPage, evalPageSize));
        PagerModel pager = new PagerModel(events.getTotalPages(),events.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("pager", pager);
        model.addAttribute("events", events);
        return "management/event/course/one/admin";
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

    @GetMapping("/event/it")
    public String itEvents(@RequestParam("pageSize") Optional<Integer> pageSize,
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
        Page<Event> events= eventRepository.findAllByType("it", new PageRequest(evalPage, evalPageSize));
        PagerModel pager2 = new PagerModel(events.getTotalPages(),events.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("pager", pager2);
        model.addAttribute("events", events);
        return "management/event/course/one/its";
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

    @GetMapping("/event/logistique")
    public String logistiqueEvents(@RequestParam("pageSize") Optional<Integer> pageSize,
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
        Page<Event> events= eventRepository.findAllByType("logistiques", new PageRequest(evalPage, evalPageSize));
        PagerModel pager3 = new PagerModel(events.getTotalPages(),events.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("events", events);
        model.addAttribute("pager", pager3);
        return "management/event/course/one/logistiques";
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

    @GetMapping("/event/protection")
    public String protectionEvents(@RequestParam("pageSize") Optional<Integer> pageSize,
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
        Page<Event> events= eventRepository.findAllByType("protection", new PageRequest(evalPage, evalPageSize));
        PagerModel pager4 = new PagerModel(events.getTotalPages(),events.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("events", events);
        model.addAttribute("pager", pager4);
        return "management/event/course/one/protections";
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
    @GetMapping("/event/resources")
    public String resourcesEvent(@RequestParam("pageSize") Optional<Integer> pageSize,
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
        Page<Event> events= eventRepository.findAllByType("resources humaines", new PageRequest(evalPage,evalPageSize));
        PagerModel pager5 = new PagerModel(events.getTotalPages(),events.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("events", events);
        model.addAttribute("pager", pager5);
        return "management/event/course/one/resources";
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

    @GetMapping("/event/leadership")
    public String leadershipEvents(@RequestParam("pageSize") Optional<Integer> pageSize,
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
        Page<Event> events= eventRepository.findAllByType("leadership", new PageRequest(evalPage, evalPageSize));
        PagerModel pager6 = new PagerModel(events.getTotalPages(),events.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("events", events);
        model.addAttribute("pager", pager6);
        return "management/event/course/one/leaderships";
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

    @GetMapping("/event/management")
    public String managementEvents(@RequestParam("pageSize") Optional<Integer> pageSize,
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
        Page<Event> events= eventRepository.findAllByType("management", new PageRequest(evalPage, evalPageSize));
        PagerModel pager7 = new PagerModel(events.getTotalPages(),events.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("events", events);
        model.addAttribute("pager", pager7);
        return "management/event/course/one/managements";
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

    @GetMapping("/event/wash")
    public String washEvents(@RequestParam("pageSize") Optional<Integer> pageSize,
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
        Page<Event> events= eventRepository.findAllByType("wash", new PageRequest(evalPage, evalPageSize));
        PagerModel pager8 = new PagerModel(events.getTotalPages(),events.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("events", events);
        model.addAttribute("pager", pager8);
        return "management/event/course/one/washs";
    }

    @GetMapping("/course/administration")
    public String courseAdmin(Model model, @PageableDefault(size = 6) Pageable pageable, @RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){

        Page<Course> courses1= courseRepository.findAllByDomainOrderByCourseIdDesc("administration et finance",pageable);
        model.addAttribute("courses1", courses1);
        model.addAttribute("courses1Size", courses1.getTotalElements());
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
        model.addAttribute("pageSizes", 3);
        Page<Event> list= eventRepository.findAllByType("administration et finance", new PageRequest(evalPage, evalPageSize));
        PagerModel pager1 = new PagerModel(list.getTotalPages(),list.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("lists", list);
        model.addAttribute("pager", pager1);
        model.addAttribute("listSize", list.getTotalElements());
        return "management/course/administration";

    }

    @GetMapping("/course/languages")
    public String courseLanguage(Model model, @PageableDefault(size = 6) Pageable pageable, @RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){

//language
        Page<Course> courses2= courseRepository.findAllByDomainOrderByCourseIdDesc("anglais et/ou francais",pageable);
        model.addAttribute("courses2", courses2);
        model.addAttribute("courses2Size", courses2.getTotalElements());
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
        model.addAttribute("pageSizes", 3);
        Page<Event> list1= eventRepository.findAllByType("anglais et/ou francais", new PageRequest(evalPage, evalPageSize));
        PagerModel pager2 = new PagerModel(list1.getTotalPages(),list1.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("lists1", list1);
        model.addAttribute("pager", pager2);
        model.addAttribute("listSize1", list1.getTotalElements());
        return "management/course/language";

    }

    @GetMapping("/course/its")
    public String courseIt(Model model, @PageableDefault(size = 6) Pageable pageable, @RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){

//templates.course it
        Page<Course> courses3= courseRepository.findAllByDomainOrderByCourseIdDesc("it",pageable);
        model.addAttribute("courses3", courses3);
        model.addAttribute("courses3Size", courses3.getTotalElements());
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
        model.addAttribute("pageSizes", 3);
        Page<Event> list2= eventRepository.findAllByType("it", new PageRequest(evalPage, evalPageSize));
        PagerModel pager3 = new PagerModel(list2.getTotalPages(),list2.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("lists2", list2);
        model.addAttribute("pager", pager3);
        model.addAttribute("listSize2", list2.getTotalElements());
        return "management/course/its";

    }

    @GetMapping("/course/logistiques")
    public String courseLogistique(Model model, @PageableDefault(size = 6) Pageable pageable, @RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){

// templates.course logistique
        Page<Course> courses4= courseRepository.findAllByDomainOrderByCourseIdDesc("logistiques", pageable);
        model.addAttribute("courses4", courses4);
        model.addAttribute("courses4Size", courses4.getTotalElements());
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
        model.addAttribute("pageSizes", 3);
        Page<Event> list3= eventRepository.findAllByType("logistiques", new PageRequest(evalPage, evalPageSize));
        PagerModel pager4 = new PagerModel(list3.getTotalPages(),list3.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("lists3", list3);
        model.addAttribute("pager", pager4);
        model.addAttribute("listSize3", list3.getTotalElements());
        return "management/course/logistique";

    }

    @GetMapping("/course/protections")
    public String courseProtection(Model model, @PageableDefault(size = 6) Pageable pageable, @RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){
// courses protection
        Page<Course> courses5= courseRepository.findAllByDomainOrderByCourseIdDesc("protection", pageable);
        model.addAttribute("courses5", courses5);
        model.addAttribute("courses5Size", courses5.getTotalElements());
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
        model.addAttribute("pageSizes", 3);

        Page<Event> list4= eventRepository.findAllByType("protection", new PageRequest(evalPage, evalPageSize));
        PagerModel pager5 = new PagerModel(list4.getTotalPages(),list4.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("lists4", list4);
        model.addAttribute("pager", pager5);
        model.addAttribute("listSize4", list4.getTotalElements());
        return "management/course/protection";

    }

    @GetMapping("/course/resource")
    public String courseResource(Model model, @PageableDefault(size = 6) Pageable pageable, @RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){

        // courses resource humaine
        Page<Course> courses6= courseRepository.findAllByDomainOrderByCourseIdDesc("resources humaines", pageable);

        model.addAttribute("courses6", courses6);
        model.addAttribute("courses6Size", courses6.getTotalElements());
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
        model.addAttribute("pageSizes", 3);

        Page<Event> list5= eventRepository.findAllByType("resources humaines", new PageRequest(evalPage, evalPageSize));
        PagerModel pager6 = new PagerModel(list5.getTotalPages(),list5.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("lists5", list5);
        model.addAttribute("pager", pager6);
        model.addAttribute("listSize5", list5.getTotalElements());
        return "management/course/resource";

    }

    @GetMapping("/course/leaderships")
    public String courseLeadership(Model model, @PageableDefault(size = 6) Pageable pageable, @RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){

        // courses leadership
        Page<Course> courses7= courseRepository.findAllByDomainOrderByCourseIdDesc("leadership", pageable);

        model.addAttribute("courses7", courses7);
        model.addAttribute("courses7Size", courses7.getTotalElements());

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
        model.addAttribute("pageSizes", 3);

        Page<Event> list6= eventRepository.findAllByType("leadership", new PageRequest(evalPage, evalPageSize));
        PagerModel pager7 = new PagerModel(list6.getTotalPages(),list6.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("lists6", list6);
        model.addAttribute("pager", pager7);
        model.addAttribute("listSize6", list6.getTotalElements());
        return "management/course/leadership";

    }

    @GetMapping("/course/managements")
    public String courseManagement(Model model, @PageableDefault(size = 6) Pageable pageable, @RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page){

        // courses managements
        Page<Course> courses8= courseRepository.findAllByDomainOrderByCourseIdDesc("management", pageable);

        model.addAttribute("courses8", courses8);
        model.addAttribute("courses8Size", courses8.getTotalElements());

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
        model.addAttribute("pageSizes", 3);

        Page<Event> list7= eventRepository.findAllByType("management", new PageRequest(evalPage, evalPageSize));
        PagerModel pager8 = new PagerModel(list7.getTotalPages(),list7.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("lists7", list7);
        model.addAttribute("pager", pager8);
        model.addAttribute("listSize7", list7.getTotalElements());
        return "management/course/management";

    }


    @GetMapping("/course/washs")
    public String findList( Model model, @PageableDefault(size = 6) Pageable pageable, @RequestParam("pageSize") Optional<Integer> pageSize,
                            @RequestParam("page") Optional<Integer> page) {
        // courses wash
        Page<Course> courses9= courseRepository.findAllByDomainOrderByCourseIdDesc("wash", pageable);

        model.addAttribute("courses9", courses9);
        model.addAttribute("courses9Size", courses9.getTotalElements());
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
        model.addAttribute("pageSizes", 3);
        Page<Event> list8= eventRepository.findAllByType("wash", new PageRequest(evalPage, evalPageSize));
        PagerModel pager9 = new PagerModel(list8.getTotalPages(),list8.getNumber(),BUTTONS_TO_SHOW);
        model.addAttribute("lists8", list8);
        model.addAttribute("pager", pager9);
        model.addAttribute("listSize8", list8.getTotalElements());
        return "management/course/wash";
    }


    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
    }

    @PostMapping(value = "/course/save")
    public String saveCourse(Course course){
        course.setDomain(course.getDomain().toLowerCase());
        Course course1=courseRepository.save(course);
        if (course1.getDomain().equals("administration et finance")){
            return "redirect:/management/course/administration";
        }else if (course1.getDomain().equals("wash")){
            return "redirect:/management/course/washs";
        }else if (course1.getDomain().equals("logistiques")){
            return "redirect:/management/course/logistiques";
        }else if (course1.getDomain().equals("leadership")){
            return "redirect:/management/course/leaderships";
        }else if (course1.getDomain().equals("management")){
            return "redirect:/management/course/managements";
        }else if (course1.getDomain().equals("it")){
            return "redirect:/management/course/its";
        }else if (course1.getDomain().equals("anglais et/ou francais")){
            return "redirect:/management/course/languages";
        }else if (course1.getDomain().equals("resources humaines")){
            return "redirect:/management/course/resource";
        }else{
            return "redirect:/management/course/protections";
        }

    }

    @GetMapping("/course/delete/{courseId}")
    public String deleteCourse( @PathVariable Long courseId){
        courseRepository.deleteById(courseId);
        return "redirect:/management/course/all";
    }

    @GetMapping("/course/get/{courseId}")
    public String findOneCourse(Model model,@PathVariable Long courseId, HttpSession session) {
        session.setAttribute("courseId",courseId);
        Optional<Course> optional= courseRepository.findById(courseId);
        List<Period> periods= periodRepository.findAllByCourses(optional.get().getCourseId());
        List<Lesson> lessons= new ArrayList<>();
        for (Period period: periods){
            List<Lesson> list= period.getLessons();
            lessons.addAll(list);
        }
        System.out.println( lessons);
        model.addAttribute("course", optional.get());
        model.addAttribute("periods", periods);
        return "management/course/course";
    }


    //course management methods end

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
    public String periodForm(Model model, HttpSession session){
        Long courseId= (Long)session.getAttribute("courseId");
        model.addAttribute("courseId", courseId);
        model.addAttribute("period",new Period());
        return "management/period/form";
    }
    @PostMapping("/period/save")
    public String periodSaved(Period period, HttpSession session, String adresse, String contenue){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        Long courseId= (Long)session.getAttribute("courseId");
        Optional<Course> course= courseRepository.findById(courseId);
        period.setCourse(course.get());
        periodRepository.save(period);
        System.out.println(adresse);
        System.out.println(contenue);
        MailService mail= new MailService();
        mail.sendSimpleMessage(
                adresse,
                "Notification de enregistrement d'une periode de formation",
                user.getName()+" vous notifi celon le contenue suivant :"+ contenue+" veuillez bien prendre connaissance du message et apporter des modifications souligner"
        );
        return "redirect:/management/course/get/"+ courseId;
    }

    @GetMapping("/period/publish/{periodId}")
    public String publishPeriod(@PathVariable Long periodId, HttpSession session){
        Period period= periodRepository.getOne(periodId);
        period.setStatus(true);
        periodRepository.save(period);
        return "redirect:/management/course/get/"+(Long)session.getAttribute("courseId");
    }

    @GetMapping("/period/draft/{periodId}")
    public String draftPeriod(@PathVariable Long periodId, HttpSession session){
        Period period= periodRepository.getOne(periodId);
        period.setStatus(false);
        periodRepository.save(period);
        return "redirect:/management/course/get/"+(Long)session.getAttribute("courseId");
    }

    @GetMapping("/period/unPublish/form/{periodId}")
    public String unPublishForm(@PathVariable Long periodId, Model model){
        Period period= periodRepository.getOne(periodId);
        model.addAttribute("period", period);
        return "management/period/correction";
    }
    @PostMapping("/period/unPublish/{periodId}")
    public String unPublishPeriod(Period period, HttpSession session, String adresse, String contenue){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        period.setStatus(null);
        periodRepository.save(period);
        MailService backMessage= new MailService();
        backMessage.sendSimpleMessage(
                adresse,
                "Notification de correction du contenu de cette periode",
                user.getName()+" vous notifi celon le contenue suivant :"+contenue+" veuillez bien prendre connaissance du message et apporter des modifications souligner"
        );
        return "redirect:/management/course/get/"+(Long)session.getAttribute("courseId");
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
    public String lessonSave(Lesson lesson, HttpSession session, @RequestParam("files") MultipartFile files,  String adresse, String contenue){
        List<FileUploadRespone> pieces= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArrayList<String> filesPaths = new ArrayList<String>();
        for(int i=0;i<pieces.size();i++)
        {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        System.out.println(filesPaths);
        lesson.setPieces(filesPaths);

        Period period= periodRepository.getOne((Long)session.getAttribute("periodId"));
        lesson.setPeriod(period);
        lessonRepository.save(lesson);
        MailService mail= new MailService();
        mail.sendSimpleMessage(
                adresse,
                "Notification de enregistrement d'une periode de formation",
                user.getName()+" vous notifi celon le contenue suivant :"+ contenue+" veuillez bien prendre connaissance du message et apporter des modifications souligner"
        );
        return "redirect:/management/period/get/"+(Long)session.getAttribute("periodId");
    }
    @GetMapping("/lesson/publish/{lessonId}")
    public String publishLesson(@PathVariable Long lessonId, HttpSession session){
        Lesson lesson= lessonRepository.getOne(lessonId);
        lesson.setStatus(true);
        System.out.println(lesson.getStatus());
        lessonRepository.save(lesson);
        return "redirect:/management/period/get/"+(Long)session.getAttribute("periodId");
    }

    @GetMapping("/lesson/draft/{lessonId}")
    public String draftLesson(@PathVariable Long lessonId, HttpSession session){

        Lesson lesson= lessonRepository.getOne(lessonId);
        lesson.setStatus(false);
        lessonRepository.save(lesson);
        return "redirect:/management/period/get/"+(Long)session.getAttribute("periodId");

    }

    @GetMapping("/lesson/unPublish/form/{lessonId}")
    public String unPublishLesson(@PathVariable Long lessonId, Model model){
        Lesson lesson= lessonRepository.getOne(lessonId);
        model.addAttribute("lesson", lesson);
        return "management/lesson/correction";
    }
    @PostMapping("/lesson/unPublish/{lessonId}")
    public String unPublishLesson(Lesson lesson, HttpSession session, String adresse, String contenue){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        lesson.setStatus(null);
        lessonRepository.save(lesson);
        MailService backMessage= new MailService();
        backMessage.sendSimpleMessage(
                adresse,
                "Notification de correction du contenu de cette lesson",
                user.getName()+" vous notifi celon le contenue suivant :"+contenue+" veuillez bien prendre connaissance du message et apporter des modifications souligner"
        );
        return "redirect:/management/period/get/"+(Long)session.getAttribute("periodId");
    }

    @DeleteMapping("/lesson/delete/{lessonId}")
    public String lessonDelete(@PathVariable Long lessonId, HttpSession session){
        lessonRepository.deleteById(lessonId);
        return "redirect:/management/period/get/"+(Long)session.getAttribute("periodId");
    }
    //Lesson management methods End

    // Event management methods start

    @GetMapping("/event/get/{eventId}")
    public String event(Model model, @PathVariable Long eventId){

        Optional<Event> optional= eventRepository.findById(eventId);
        model.addAttribute("event", optional.get());
        return "management/event/course/event";
    }

    @GetMapping("/event/course/form")
    public String evenetForm(Model model){
        List<Region> regions= regionRepository.findAll();
        model.addAttribute("regions", regions);
        model.addAttribute("event", new Event());
        return "management/event/course/form";
    }

    @PostMapping("/event/course/save")
    public  String eventSave(Event event, @RequestParam("file") MultipartFile file){
        String fileName = fileUploadService.storeFile(file);

        event.setImage("/downloadFile/"+fileName);
        event.setType(event.getType().toLowerCase());
        eventRepository.save(event);
        if (event.getType().equals("administration et finance")) {
            return "redirect:/management/event/admin";
        }else if (event.getType().equals("protection")){
            return "redirect:/management/event/protection";

        }else if (event.getType().equals("resources humaines")){
            return "redirect:/management/event/resources";

        }else if (event.getType().equals("anglais et/ou francais")){
            return "redirect:/management/event/language";

        }else if (event.getType().equals("it")){
            return "redirect:/management/event/it";

        }else if (event.getType().equals("leadership")){
            return "redirect:/management/event/leadership";

        }else if (event.getType().equals("logistiques")){
            return "redirect:/management/event/logistique";

        }else if (event.getType().equals("management")){
            return "redirect:/management/event/management";

        }else {
            return "redirect:/management/event/wash";

        }
    }

    @GetMapping("/event/primaire/form")
    public String evenetPrimaireForm(Model model){
        model.addAttribute("event", new Event());
        return "management/event/primaire/form";
    }

    @GetMapping("/event/secondary/form")
    public String evenetSecondaryForm(Model model){
        model.addAttribute("event", new Event());
        return "management/event/secondary/form";
    }


    @PostMapping("/event/primaire/save")
    public  String eventSavePrimaire(Event event, @RequestParam("file") MultipartFile file){
        String fileName = fileUploadService.storeFile(file);

        event.setImage("/downloadFile/"+fileName);
        eventRepository.save(event);
        return "redirect:/management/primaire/events";
    }

    @PostMapping("/event/secondary/save")
    public  String eventSaveSecondary(Event event, @RequestParam("file") MultipartFile file){
        String fileName = fileUploadService.storeFile(file);

        event.setImage("/downloadFile/"+fileName);
        eventRepository.save(event);
        return "redirect:/management/secondary/events";
    }

    @PostMapping("/event/civic/save")
    public  String eventSaveEducation(Event event, @RequestParam("files") MultipartFile files){
        String fileName = fileUploadService.storeFile(files);

        event.setImage("/downloadFile/"+fileName);
        eventRepository.save(event);
        return "redirect:/management/civic/events";
    }


    // Event management method end
}











