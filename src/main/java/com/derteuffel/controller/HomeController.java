package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.*;
import com.derteuffel.service.MailService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by derteuffel on 20/10/2018.
 */
@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private TheseRepository theseRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private PanierRepository panierRepository;

    @Autowired
            private LivreRepository livreRepository;
    @Autowired
            private SyllabusRepository syllabusRepository;


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

    @GetMapping("/login")
    public String login()
    {
            return "connection";
        }

    @GetMapping("/login/visitor")
    public String loginVisitor(HttpServletRequest request, HttpSession session)
    {
        session.setAttribute("lastUrl", request.getHeader("referer"));
        return "loginVisitor";
    }


    @GetMapping("/perform_logout")
    public String logout(HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        List<Panier> paniers = user.getPaniers();
        Panier theLast = paniers.get(paniers.size()-1);
        if(theLast.getCount()==0.0) panierRepository.delete(theLast);
        Panier panier =null;
        for(Panier panier1 : paniers)
        {
            if(panier1.getStatus())
            {
                panier= panier1;
            }
        }
        if(panier!=null) {
            panier.setStatus(false);
            panierRepository.save(panier);
        }
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/logout";
    }

    @GetMapping("/stats")
    public String stats (Model model){
        List<User> users = userService.listAll();
        List<Groupe> groupes = groupeRepository.findAll();
        List<These> theses = theseRepository.findAll();
        model.addAttribute("usersSize",users.size());
        model.addAttribute("groupes",groupes);
        model.addAttribute("thesesSize",theses.size());
        return "stats/stats";
    }

    @GetMapping("/validate/{userId}")
    public String validate(@PathVariable Long userId){
        User user=userService.getById(userId);

        System.out.println(user.getUserId());
        user.setActive(true);
        userRepository.save(user);
        return "redirect:/login";

    }

    @GetMapping("/about")
    public  String home(){
        return "index";
    }

    @GetMapping("/deals")
    public  String deals(){
        return "deals";
    }

    @GetMapping("/")
    public  String about(){
        return "these_module/index";
    }

    @GetMapping("/contact")
    public  String contact(){
        return "contact";
    }
    @GetMapping("/other/{regionId}")
    public String other(Model model, HttpSession session){

        return "other";
    }

    @GetMapping("/school/lesson/trial")
    public String oneLesson(){
        return "training/detail/one";
    }


    @GetMapping("/education")
    public  String education(Model model){
        List<Country> countries=countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "country/educations";
    }
    @GetMapping("/education/region/{countryId}")
    public String region(Model model, @PathVariable Long countryId){
        Country country= countryRepository.getOne(countryId);
        List<Region> regions= regionRepository.findAllByCountry(country.getCountryId());
        model.addAttribute("regions", regions);
        return "region/education";
    }








    @GetMapping("/visitor/student")
    public String student(Model model)
    {
        model.addAttribute("countries", countries);
        model.addAttribute("user", new User());
        return "visitor/student";
    }

    @GetMapping("/visitor/expert/universitory")
    public String expertu(Model model){
        model.addAttribute("countries", countries);
        model.addAttribute("user", new User());
        return "visitor/expertu";
    }
    @GetMapping("/visitor/expert/professional")
    public String expertp(Model model){
        model.addAttribute("countries", countries);
        model.addAttribute("user", new User());
        return "visitor/expertp";
    }

    @GetMapping("/visitor/teacher/primaire")
    public String teacherp(Model model){
        model.addAttribute("countries", countries);
        model.addAttribute("user", new User());
        return "visitor/teacher";
    }
    @GetMapping("/visitor/teacher/secondaire")
    public String teachers(Model model){
        model.addAttribute("countries", countries);
        model.addAttribute("user", new User());
        return "visitor/teachers";
    }

    @GetMapping("/visitor/publish/book/form")
    public String bookForm(Model model){
        Livre livre=new Livre();
        model.addAttribute("livre",livre);
        return "visitor/book";
    }



    @GetMapping("/visitor/publish/sylabus/form")
    public String sylabusForm(Model model){
        Syllabus syllabus=new Syllabus();
        model.addAttribute("syllabus",syllabus);
        return "visitor/sylabus";
    }

    @PostMapping("/visitor/livre/save")
    public String saveLivre(Livre post, @RequestParam("files") MultipartFile[] files, String publishPrice) {
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
        post.setPieces(filesPaths);
        post.setPublishPrice(Double.parseDouble(publishPrice));
        post.setStatus(false);
        post.setSuprimee(true);
        post.setType("livre");
        livreRepository.save(post);
        MailService mailService = new MailService();
        mailService.sendSimpleMessage(
                "solutioneducationafrique@gmail.com",
               // "derteuffel0@gmail.com",
                "YesBanana: Notification d'une publication d'un livre",
                "cette publication est encore en suspend veuillez bien vous connecter pour lui attribuer un status "+
        " veiller cliquer sur le lien pour etre rediriger vers la page "+"http:localhost:8080/school/detail/"+post.getLivreId());
        return "redirect:/about";
    }

    @PostMapping("/visitor/syllabus/save")
    public String save(Syllabus syllabus,@RequestParam("files") MultipartFile[] files, String publishPrice){
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
        syllabus.setPieces(filesPaths);
        syllabus.setPublishPrice(Double.parseDouble(publishPrice));
        syllabus.setStatus(false);
        syllabus.setSuprimee(true);
        syllabusRepository.save(syllabus);
        MailService mailService = new MailService();
        mailService.sendSimpleMessage(
                "solutioneducationafrique@gmail.com",
                // "derteuffel0@gmail.com",
                "YesBanana: Notification d'une publication d'un livre",
                "cette publication est encore en suspend veuillez bien vous connecter pour lui attribuer un status "+
                        " veiller cliquer sur le lien pour etre rediriger vers la page "+"http:localhost:8080/school/detail/"+syllabus.getSyllabusId());
        return "redirect:/about";

    }
    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
    }


}