package com.derteuffel.controller;


import com.derteuffel.data.*;
import com.derteuffel.repository.*;
import com.derteuffel.service.CommentService;
import com.derteuffel.service.EventService;
import com.derteuffel.service.MailService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by derteuffel on 07/05/2019.
 */
@Controller
@RequestMapping("/visitor")
public class ExternController {
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

    @Autowired
    private TheseRepository theseRepository;
    @Autowired
    private BibliographyRepository bibliographyRepository;
    @Autowired
    private SyllabusRepository syllabusRepository;
    @Autowired
    private  CommandeRepository commandeRepository;
    @Autowired
    private BourseRepository bourseRepository;

    @Autowired
    private ColonieRepository colonieRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private StudentWorkRepository studentWorkRepository;
    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private PrimaireRepository primaireRepository;
    @Autowired
    private NiveauRepository niveauRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/catalogues")
    public String catalogues(){
        return "these_module/side/catalogues";
    }
    @GetMapping("/livres")
    public String livres(Model model){
        List<Bibliography> bibliographies=bibliographyRepository.findAllByDisponibility(true, Sort.by(Sort.Direction.DESC,"bibliographyId"));
        System.out.println(bibliographies.size());
        model.addAttribute("livres",bibliographies);
        return "these_module/side/search_livres";
    }
    @GetMapping("/syllabuses")
    public String syllabus(Model model){
        List<Syllabus> syllabuses=syllabusRepository.findBySuprimeeAndStatusOrderBySyllabusIdDesc(true,true);
        model.addAttribute("syllabuses",syllabuses);
        return "these_module/side/search_syllabus";
    }
    @GetMapping("/livre/{bibliographyId}")
    public String livreSide(Model model,@PathVariable Long bibliographyId, HttpSession session){
        session.setAttribute("bibliographyId",bibliographyId);
        Bibliography livre=bibliographyRepository.getOne(bibliographyId);
        System.out.println(livre);
        model.addAttribute("livre",livre);
        System.out.println(livre.getFichier());
        System.out.println("me voicis");
        return "these_module/side/livre";
    }
    @GetMapping("/syllabus/{syllabusId}")
    public String syllabusSide(Model model,@PathVariable Long syllabusId){
        Syllabus syllabus=syllabusRepository.getOne(syllabusId);
        model.addAttribute("syllabus",syllabus);
        return "these_module/side/syllabus";
    }
    @GetMapping("/magazines")
    public String magazines(){
        return "these_module/side/magazines";
    }
    @GetMapping("/bourses")
    public String bourses(Model model){
        List<Bourse> bourses= bourseRepository.findFirst12ByStatusAndSuprime(true,false, Sort.by(Sort.Direction.DESC,"bourseId"));
        model.addAttribute("bourses",bourses);
        return "these_module/side/bourses";
    }

    @GetMapping("/commande/livre/form/{bibliographyId}")
    public String livre_commande(Model model,@PathVariable Long bibliographyId){
        Commande commande= new Commande();
        Bibliography bibliography=bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("bibliography", bibliography);
        model.addAttribute("commande",commande);
        return "these_module/side/commande";
    }

    @PostMapping("/commande/livre/save")
    public String create_commande(Model model, Commande commande,  HttpSession session, HttpServletRequest request,String title, String price){

        String paiement = request.getParameter("paiement");
        commande.setTitle(title);
        commande.setMontant(Double.parseDouble(price));
        commande.setStatus(false);
        Long time = System.currentTimeMillis();
        commande.setDateReservation(new Date(time));
        User connectedUser = userRepository.findById((Long) session.getAttribute("userId")).get();
        commande.setUser(connectedUser);
        commandeRepository.save(commande);
        System.out.println("Commande" + commande);
        model.addAttribute("commande",commande);

            return "payment/panier";

    }

    // student work methods implementations
    @GetMapping("/students_work")
    public String students_work(Model model){
        List<These> studentsWorks=theseRepository.findByStatesAndStatusOrderByTheseIdDesc(true,true);
        System.out.println(studentsWorks);
        model.addAttribute("theses",studentsWorks);
        return "these_module/side/search_student_work";
    }

    @GetMapping("/students_work/faculties")
    public  String student_work_faculties(Model model){
        List<Faculty> faculties=facultyRepository.findAll();
        model.addAttribute("faculties",faculties);
        return "these_module/side/student_work_by_faculties";
    }

    @GetMapping("/students_work/options/{facultyId}")
    public String student_work_option(@PathVariable Long facultyId, Model model){
        Faculty faculty=facultyRepository.getOne(facultyId);
        List<Options> optionses=optionsRepository.findAllByFaculty(faculty.getFacultyId());
        model.addAttribute("optionses",optionses);
        return "these_module/side/student_work_optionses";
    }

    @GetMapping("/student_work/theses/{optionsId}")
    public String student_work_by_options(@PathVariable Long optionsId, Model model){
        Options options=optionsRepository.getOne(optionsId);
        List<These>theses= theseRepository.findAllByOptionsAndStatusOrderByTheseIdDesc(options.getOptionsName(),true);
        model.addAttribute("theses", theses);
        return "these_module/side/search_student_work";
    }



    @GetMapping("/professeurs")
    public String help(){
        return "these_module/encadrement_travaux/professeurs";
    }
    @GetMapping("/what_are/encadreur")
    public String what(){
        return "these_module/encadrement_travaux/what";
    }
    @GetMapping("/chef/travaux")
    public String about_us(){
        return "these_module/encadrement_travaux/chef_travaux";
    }
    @GetMapping("/enseignants/primaire")
    public String question(){
        return "these_module/encadrement_travaux/primaire";
    }
    @GetMapping("/enseignants/secondaire")
    public String question1(){
        return "these_module/encadrement_travaux/secondaire";
    }
    @GetMapping("/expert/yesb")
    public String work_with_us(){
        return "these_module/encadrement_travaux/expert_yesb";
    }

    @GetMapping("/why_work/with_us")
    public String search_theme(Model model){
        return "these_module/side/why";
    }
    @GetMapping("/theme/{theseId}")
    public String theme(Model model, @PathVariable Long theseId, HttpSession session){
        These these=theseRepository.getOne(theseId);
        session.setAttribute("theseId", these.getTheseId());
        model.addAttribute("these", these);

        List<Bibliography> allThesesBibliographies=bibliographyRepository.findAllByThese(these.getTheseId(),Sort.by(Sort.Direction.DESC,"bibliographyId"));
        List<Bibliography> livres= new ArrayList<>();
        for (Bibliography bibliography : allThesesBibliographies){
            if (bibliography.getDisponibility() == true){
                livres.add(bibliography);
            }
        }
        model.addAttribute("livres",livres);
        return "these_module/advanced/theme";
    }
    @GetMapping("/livre/detail/{bibliographyId}")
    public String livre(Model model, @PathVariable Long bibliographyId){
        Bibliography bibliography=bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("livre", bibliography);
        return "these_module/advanced/livre";
    }

    public List<String> removeDuplicates(List<String> list)
    {
        if (list == null){
            return new ArrayList<>();
        }

        // Create a new ArrayList
        List<String> newList = new ArrayList<String>();
        // Traverse through the first list
        for (String element : list) {

            // If this element is not present in newList
            // then add it

            if (element !=null && !newList.contains(element) && !element.isEmpty()) {

                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }
    public List<Integer> removeDuplicatesNumber(List<Integer> list)
    {
        if (list == null){
            return new ArrayList<>();
        }

        // Create a new ArrayList
        List<Integer> newList = new ArrayList<>();
        // Traverse through the first list
        for (Integer element : list) {

            // If this element is not present in newList
            // then add it

            if (element !=null && !newList.contains(element)) {

                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }

    public List<List<Object>> removeDuplicatesList(List<List<Object>> list)
    {
        if (list == null){
            return new ArrayList<List<Object>>();
        }

        // Create a new ArrayList
        List<List<Object>> newList = new ArrayList<List<Object>>();
        // Traverse through the first list
        for (List<Object> element : list) {

            // If this element is not present in newList
            // then add it

            if (element !=null && !newList.contains(element)) {

                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }


    public List<String> categorieses= Arrays.asList("petit","cadet");
    public List<String> types= Arrays.asList("vip","standart");


    //colonie methods implements

    @GetMapping("/colonies")
    public String all_colonies(Model model){
        List<Colonie> colonies=colonieRepository.findFirst12ByActive(true, Sort.by(Sort.Direction.DESC,"colonieId"));
         List<Colonie> colonies1=colonieRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries1=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        for (Colonie colonie : colonies1){

            countries1.add(colonie.getPays());
            saisons.add(colonie.getSaison());
            sites.add(colonie.getSite());
        }
        model.addAttribute("saisons",removeDuplicates(saisons));
        model.addAttribute("sites",removeDuplicates(sites));
        model.addAttribute("categories", categorieses);
        model.addAttribute("types",types);
        model.addAttribute("colonies", colonies);
        model.addAttribute("countries", removeDuplicates(countries1));
        return "these_module/colonie/colonies";
    }

    @GetMapping("/colonie/country/{pays}")
    public String search_colonie_country(@PathVariable String pays, Model model){

        List<Colonie> colonies=colonieRepository.findFirst12ByPaysAndActive(pays,true,Sort.by(Sort.Direction.DESC,"colonieId"));
        List<Colonie> colonies1=colonieRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries1=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        for (Colonie colonie : colonies1){

            countries1.add(colonie.getPays());
            saisons.add(colonie.getSaison());
            sites.add(colonie.getSite());
        }
        model.addAttribute("saisons",removeDuplicates(saisons));
        model.addAttribute("sites",removeDuplicates(sites));
        model.addAttribute("categories", categorieses);
        model.addAttribute("types",types);
        model.addAttribute("countries", removeDuplicates(countries1));
        model.addAttribute("colonies", colonies);
        return "these_module/colonie/colonies";
    }

    @GetMapping("/colonie/site/{site}")
    public String search_colonie_site(@PathVariable String site, Model model){

        List<Colonie> colonies=colonieRepository.findFirst12BySiteAndActive(site,true,Sort.by(Sort.Direction.DESC,"colonieId"));
        List<Colonie> colonies1=colonieRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries1=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        for (Colonie colonie : colonies1){

            countries1.add(colonie.getPays());
            saisons.add(colonie.getSaison());
            sites.add(colonie.getSite());
        }
        model.addAttribute("saisons",removeDuplicates(saisons));
        model.addAttribute("sites",removeDuplicates(sites));
        model.addAttribute("categories", categorieses);
        model.addAttribute("types",types);
        model.addAttribute("countries", removeDuplicates(countries1));
        model.addAttribute("colonies", colonies);
        return "these_module/colonie/colonies";
    }

    @GetMapping("/colonie/saison/{saison}")
    public String search_colonie_saison(@PathVariable String saison, Model model){

        List<Colonie> colonies=colonieRepository.findFirst12BySaisonAndActive(saison,true,Sort.by(Sort.Direction.DESC,"colonieId"));
        List<Colonie> colonies1=colonieRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries1=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        for (Colonie colonie : colonies1){

            countries1.add(colonie.getPays());
            saisons.add(colonie.getSaison());
            sites.add(colonie.getSite());
        }
        model.addAttribute("saisons",removeDuplicates(saisons));
        model.addAttribute("sites",removeDuplicates(sites));
        model.addAttribute("categories", categorieses);
        model.addAttribute("types",types);
        model.addAttribute("countries", removeDuplicates(countries1));
        model.addAttribute("colonies", colonies);
        return "these_module/colonie/colonies";
    }

    @GetMapping("/colonie/type/{type}")
    public String search_colonie_type(@PathVariable String type, Model model){

        List<Colonie> colonies=colonieRepository.findFirst12ByTypeAndActive(type,true,Sort.by(Sort.Direction.DESC,"colonieId"));
        List<Colonie> colonies1=colonieRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries1=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        for (Colonie colonie : colonies1){

            countries1.add(colonie.getPays());
            saisons.add(colonie.getSaison());
            sites.add(colonie.getSite());
        }
        model.addAttribute("saisons",removeDuplicates(saisons));
        model.addAttribute("sites",removeDuplicates(sites));
        model.addAttribute("categories", categorieses);
        model.addAttribute("types",types);
        model.addAttribute("countries", removeDuplicates(countries1));
        model.addAttribute("colonies", colonies);
        return "these_module/colonie/colonies";
    }

    @GetMapping("/colonie/category/{category}")
    public String search_colonie_category(@PathVariable String category, Model model){

        List<Colonie> colonies=colonieRepository.findFirst12ByCategoryAndActive(category,true,Sort.by(Sort.Direction.DESC,"colonieId"));
        List<Colonie> colonies1=colonieRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries1=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        for (Colonie colonie : colonies1){

            countries1.add(colonie.getPays());
            saisons.add(colonie.getSaison());
            sites.add(colonie.getSite());
        }
        model.addAttribute("saisons",removeDuplicates(saisons));
        model.addAttribute("sites",removeDuplicates(sites));
        model.addAttribute("categories", categorieses);
        model.addAttribute("types",types);
        model.addAttribute("countries", removeDuplicates(countries1));
        model.addAttribute("colonies", colonies);
        return "these_module/colonie/colonies";
    }


    @GetMapping("/colonie/{colonieId}")
    public String one_colonie(@PathVariable Long colonieId, Model model){
        List<Colonie> colonies=colonieRepository.findFirst3ByActive(true,Sort.by(Sort.Direction.DESC,"colonieId"));
        Colonie colonie=colonieRepository.getOne(colonieId);
        model.addAttribute("colonies", colonies);
        model.addAttribute("countries",countries);
        model.addAttribute("user", new User());
        model.addAttribute("colonie", colonie);
        return "these_module/colonie/colonie";
    }

    //education primaire methods implementations

    @GetMapping("/education/primaire")
    public String gets_all_livre(Model model){
        List<Primaire> primaires=primaireRepository.findAllByStatus(true);
        List<Niveau> classes=niveauRepository.findAll();
        List<Primaire> livres=new ArrayList<>();
        List<List<Matiere>> matieres=new ArrayList<List<Matiere>>();
        List<Matiere> matieres_par_niveau= new ArrayList<>();
        List<String> elements= new ArrayList<>();

        for (Niveau niveau : classes){

            List<Primaire> primaires1=primaireRepository.findByClasse(niveau.getNiveau());
            for (Primaire primaire : primaires1){
                elements.add(primaire.getType());
                for (String element : elements){
                    if (matiereRepository.findByNameAndClasse(element,niveau.getNiveau())==null) {
                        Matiere matiere = new Matiere();
                        matiere.setClasse(niveau.getNiveau());
                        matiere.setName(element);
                        matiereRepository.save(matiere);
                    }else {
                        System.out.println("j'existe deja");
                    }
                }
                elements = new ArrayList<>();
            }

            matieres_par_niveau.addAll(matiereRepository.findByClasse(niveau.getNiveau()));
            System.out.println(matieres_par_niveau);
            matieres.add(matieres_par_niveau);
            matieres_par_niveau= new ArrayList<>();

        }


        System.out.println(elements);
        System.out.println(matieres);



        model.addAttribute("niveaux",classes);
        return "these_module/primaire/primaires";
    }


    @GetMapping("/primaire/{educationId}/{type}")
    public String getLivre_by_matiere(@PathVariable Long educationId, Model model){
        Primaire primaire = primaireRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "these_module/primaire/primaire";
    }

    @GetMapping("/primaire/livres/{matiereId}")
    public String find_all_by_type( Model model, @PathVariable Long matiereId){
        List<Primaire> primaires=primaireRepository.findAllByMatiere_MatiereIdAndStatus(matiereId,true);
        Matiere matiere=matiereRepository.getOne(matiereId);
        System.out.println(primaires);
        model.addAttribute("matiere",matiere.getName());
        model.addAttribute("livres",primaires);
        return "these_module/primaire/livres";
    }

    @GetMapping("/primaire/livre/{educationId}")
    public  String get_one_livre(Model model , @PathVariable Long educationId){
        Primaire primaire=primaireRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "these_module/primaire/livre";
    }

    @Autowired
    private EventService eventService;

    @GetMapping("/event/events")
    public String news(Model model){

        List<Event> events=eventService.findByTypeAndCategory("magazine","photo");
        List<Event> events2=eventService.findByTypeAndCategory("magazine","look");
        List<Event> events3=eventService.findByTypeAndCategory("magazine","miss");
        List<Event> events4=eventService.findByTypeAndCategory("magazine","sport");
        List<Event> events8=eventService.findByTypeAndCategory("magazine","autre");

        System.out.println(events4);

        List<Event> events1=eventService.findAll();
        List<Event> events5=eventService.findFirst6("magazine","photo");
        List<Event> events6=eventService.findFirst3("magazine","look");
        List<Event> events7=eventService.findFirst3("magazine","miss");
        events6.addAll(events7);
        events4.addAll(events8);
        model.addAttribute("events1",events1);
        model.addAttribute("events2",events2);
        model.addAttribute("events3",events3);
        model.addAttribute("events4",events4);
        model.addAttribute("events",events);
        model.addAttribute("photos",events5);
        model.addAttribute("events6",events6);
        return "these_module/event/events";
    }


    @GetMapping("/event/{eventId}")
    public String event(Model model, @PathVariable Long eventId){

        Event event= eventService.getOne(eventId);
        event.setLikes(event.getLikes()+1);
        eventService.save(event);
        List<Event> events=eventService.findFirst3(event.getType(),event.getCategory());
        model.addAttribute("event",event);
        model.addAttribute("comment",new Comment());
        model.addAttribute("events",events);

        return  "these_module/event/event";
    }


    @GetMapping("/event/like/{eventId}")
    public String like(@PathVariable Long eventId){
        Event event=eventService.getOne(eventId);
        event.setLikes(event.getLikes()+1);
        eventService.save(event);
        return "redirect:/visitor/event/events";
    }

    @GetMapping("/events/{type}")
    public String events(Model model, @PathVariable String type){
        List<Event> events=eventService.findByType(type);
        model.addAttribute("type",type);
        model.addAttribute("events",events);
        model.addAttribute("comment",new Comment());
        return "these_module/event/infos";
    }

    @GetMapping("/events/{type}/{category}")
    public String eventsByCategory(Model model, @PathVariable String category, @PathVariable String type){
        System.out.println(category);
        System.out.println(type);
        List<Event> events=eventService.findByTypeAndCategory(type,category);

        if (category.contains("sport")){
            List<Event> events1= eventService.findByTypeAndCategory(type,"autre");
            events1.addAll(events);
            System.out.println(events1);
            model.addAttribute("events", events1);
        }else {
            System.out.println(events);
            model.addAttribute("events",events);
        }
        List<String> elements= new ArrayList<>(Arrays.asList("sport","miss","photo","look"));
        model.addAttribute("elements",elements);
        model.addAttribute("category",category);
        model.addAttribute("comment",new Comment());
        model.addAttribute("type",type);
        return "these_module/event/list";
    }


    @PostMapping("/livres/specifiques")
    public String specifics_book(String name,String telephone, String theme, String amount, String email){
        MailService mailService = new MailService();
        mailService.sendSimpleMessage(
                "info@yesbanana.org",
                name + " souhaite avoir une documentation specifique ",
                name + " souhaite avoir une documentation specifique des livres"+ "sur le theme : "+ theme+" pour un montant de :"+amount+"  Bien vouloir le contacter au : " + telephone + " et/ou a l'adresse e-mail suivante :"+email+ ", pour suivres sa commande"
        );

        return "these_module/side/redirect_after_specifique";
    }


    @GetMapping("/read/pdf/{bibliographyId}")
    public  String readPdf(@PathVariable Long bibliographyId, Model model){
        Bibliography livre= bibliographyRepository.getOne(bibliographyId);

        model.addAttribute("livre",livre);
        return "these_module/side/search_catalogues";
    }


    @Autowired
    private CommentService commentService;


    @PostMapping("/events/add/comment/{eventId}")
    public String addComments(Comment comment,@PathVariable Long eventId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
         Event event=eventService.getOne(eventId);

        if (comment.getPublisher().isEmpty()){
            comment.setPublisher(user.getName());
        }
        comment.setEvent(event);
        commentService.save(comment);
        return "redirect:/visitor/events/"+event.getType();
    }

    @PostMapping("/event/add/comment/{eventId}")
    public String addComment(Comment comment,@PathVariable Long eventId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        Event event=eventService.getOne(eventId);

        if (comment.getPublisher().isEmpty()){
            comment.setPublisher(user.getName());
        }
        comment.setEvent(event);
        commentService.save(comment);
        return "redirect:/visitor/event/"+event.getEventId();
    }
}
