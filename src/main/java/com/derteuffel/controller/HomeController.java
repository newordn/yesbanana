package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.*;
import com.derteuffel.service.MailService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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


    @GetMapping("/logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
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

    @GetMapping("/")
    public  String home(){
        return "index";
    }

    @GetMapping("/deals")
    public  String deals(){
        return "deals";
    }

    @GetMapping("/about")
    public  String about(){
        return "about";
    }

    @GetMapping("/contact")
    public  String contact(){
        return "contact";
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
        return "other";
    }

    @GetMapping("/school/detail/{postId}")
    public String findById(Model model,@PathVariable Long postId) {
        Optional<Post> optional=postRepository.findById(postId);
        int postLike=0;
                int i=optional.get().getLikes();
        i++;
        postLike =i;
        System.out.println(postLike);
        optional.get().setLikes(postLike);
        postRepository.save(optional.get());
        model.addAttribute("post", optional.get());
        return "region/post";
    }

    @GetMapping("/school/lesson/trial")
    public String oneLesson(){
        return "training/detail/one";
    }


    @GetMapping("/education")
    public  String education(Model model){
        List<Country> countries=countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "country/education";
    }
    @GetMapping("/education/region/{countryId}")
    public String region(Model model, @PathVariable Long countryId){
        Country country= countryRepository.getOne(countryId);
        List<Region> regions= regionRepository.findAllByCountry(country.getCountryId());
        model.addAttribute("regions", regions);
        return "region/education";
    }

    // civic education
    @GetMapping("/school/civic/{regionId}")
    public  String civic(Model model, @PathVariable Long regionId, HttpSession session){
        session.setAttribute("regionId", regionId);
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
        return "civic/civic";
    }

    //primary school courses
    @GetMapping("/school/primary/{regionId}")
    public String schoolprimary(Model model, HttpSession session, @PathVariable Long regionId){
        session.setAttribute("regionId", regionId);
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

        return "primary/courses";
    }


    //secondary school courses
    @GetMapping("/school/secondary/{regionId}")
    public String schoolsecondary(Model model, HttpSession session, @PathVariable Long regionId){
        session.setAttribute("regionId", regionId);
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

        return "secondary/courses";
    }



    // training finance and administration
    @GetMapping("/training/admin")
    public String training(){
        return "training/adminfinance";
    }



    @GetMapping("/other/services")
    public String other(Model model){
        List<Country> countries=countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "country/others";
    }

    @GetMapping("/other/services/region/{countryId}")
public String otherRegion(Model model, @PathVariable Long countryId){
    Country country= countryRepository.getOne(countryId);
    List<Region> regions= regionRepository.findAllByCountry(country.getCountryId());
    model.addAttribute("regions", regions);
    return "region/others";
}

    @GetMapping("/other/markets")
    public String market(){
        return "markets/market";
    }

    @GetMapping("/school/project")
    public String concour(){
        return "project/concour";
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

    @PostMapping("/visitor/post/save")
    public String save(Post post, @RequestParam("files") MultipartFile[] files) {
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
        if (post.getCategory().equals("Education civique")){
            post.setNiveau(3);
        }else if (post.getCategory().equals("Education primaire")){
            post.setNiveau(1);
        }else if (post.getCategory().equals("Education secondaire")){
            post.setNiveau(2);
        }else {
            post.setNiveau(4);
        }
        postRepository.save(post);
        MailService mailService = new MailService();
        mailService.sendSimpleMessage(
                /*"solutioneducationafrique@gmail.com"*/
                "derteuffel0@gmail.com",
                "YesBanana: Notification création d'une publication",
                "cette publication est encore en suspend veuillez bien vous connecter pour lui attribuer un status "+
        "ou veiller cliquer sur le lien pour etre rediriger vers la page "+"http:localhost:8080/school/detail/"+post.getPostId());
        return "redirect:/";
    }
    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
    }

    //get one course
    @GetMapping("/school/course/detail")
    public String getCourse(){
        return "training/detail/course";
    }

}