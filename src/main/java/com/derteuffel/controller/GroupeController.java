


package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.*;
import com.derteuffel.service.MailService;
import com.derteuffel.service.RoleService;
import com.derteuffel.service.TheseService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by derteuffel on 23/10/2018.
 */
@Controller
@RequestMapping("/groupe")
public class GroupeController {
    
    // attributes
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TheseRepository theseRepository;
    @Autowired
    private AddGroupeUserRepository addGroupeUserRepository;
    @Autowired
    private TheseService theseService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudentWorkRepository studentWorkRepository;
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    private BibliographyRepository bibliographyRepository;
    @Autowired
    private BibliothequeRepository bibliothequeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
            private OptionsRepository optionsRepository;
    @Autowired
    private LivreRepository livreRepository;
    @Autowired
            private PostRepository postRepository;
    @Autowired
    private SyllabusRepository syllabusRepository;
    @Autowired
    private RapportRepository rapportRepository;
    @Autowired
    private BourseRepository bourseRepository;

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

    private static int currentPage=1;
    private static int pageSize=6;

    
    // for listing all the crews
    @GetMapping("/groupes")
    public String findAllByParentOrderByGroupeIdDesc(Model model, HttpSession session, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session= request.getSession();
        session.setAttribute("userId",user.getUserId());
        session.setAttribute("avatar",user.getImg());
        session.setAttribute("name", user.getName());

            model.addAttribute("groupe", new Groupe());
            session.setAttribute("roles", roleRepository.findByUsers_UserId(user.getUserId()));
        model.addAttribute("roles",roleRepository.findByUsers_UserId(user.getUserId()));

        Role role= roleRepository.findByRole("VISITOR");
        Role role3= roleRepository.findByRole("LIVRE");
        if(user.getRoles().containsAll(new HashSet<Role>(Arrays.asList(role3)))){

            return "redirect:/groupe/livres";
        }else if (user.getRoles().containsAll(new HashSet<Role>(Arrays.asList(role)))) {
            System.out.println("je suis visiteur");
            System.out.println((String) session.getAttribute("lastUrl"));
            return "redirect:/backside";

        }else {
            System.out.println("je suis dedans" );
            model.addAttribute("countries", countries);
            //roleRepository.save(new Role("ECOLE"));
            Collection<User> users1 = userRepository.findByRoles_Role("ADMIN");
            List<Groupe> groupes = groupeRepository.findAllByStatus(true);
            List<Groupe> crews = groupeRepository.findByUsers_UserId(user.getUserId());
            Collection<User> users = new ArrayList<>();
            users1.addAll(userRepository.findByRoles_Role("ROOT"));
            users1.addAll(userRepository.findByRoles_Role("ROOT_MASTER"));
            users1.addAll(userRepository.findByRoles_Role("ADMIN_MASTER"));
            users.addAll(users1);
            List<User> users2 = new ArrayList<>();
            for (Groupe groupe : groupes) {
                if (!groupe.getGroupChief().isEmpty()) {
                    users2.add(userRepository.getOne(Long.parseLong(groupe.getGroupChief())));
                } else {
                    users2.add(null);
                }
            }
            System.out.println(users1);
            Role role1 =  roleRepository.findByRole("USER");
            Role role2 =  roleRepository.findByRole("ADMIN");
            Role role4 =  roleRepository.findByRole("ECOLE");
            if (user.getRoles().contains(role1) || user.getRoles().contains(role2)|| user.getRoles().contains(role4)){
                model.addAttribute("users2", users2);
                model.addAttribute("users", users);
                model.addAttribute("crews", crews);
            }else {
                model.addAttribute("users2", users2);
                model.addAttribute("crews", groupes);
                model.addAttribute("users", users);
            }
            return "crew/crews";
        }


    }

    //retrieves all books in database

    @GetMapping("/faculties")
    public  String faculties(Model model, HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        List<Faculty> faculties= facultyRepository.findAll();
        session.setAttribute("roles",user.getRoles());
        model.addAttribute("faculties",faculties);
        return "livres/faculties";
    }

    @PostMapping("/role/save")
    public String addRole(Role role){
        roleRepository.save(role);
        return "redirect:/groupe/groupes";
    }

    @GetMapping("/livres/{facultyId}")
    public String retrieve_book(Model model, @PathVariable Long facultyId, HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("roles",user.getRoles());
        Faculty faculty=facultyRepository.getOne(facultyId);
        session.setAttribute("facultyId",faculty.getFacultyId());
        List<Options> optionses=optionsRepository.findAllByFaculty(faculty.getFacultyId());
        List<These> theses=new ArrayList<>();
        for (Options options : optionses){
                List<These> theses1=theseRepository.findAll();
            for (These these : theses1){
                if (options.getOptionsName().equals(these.getOptions())) {
                    theses.add(these);
                }
            }
             }
        System.out.println(theses);
        System.out.println("je suis ici");
        List<Bibliography> bibliographies= new ArrayList<>();
        for (These these : theses){
            if (these.getStates() == true) {
                System.out.println("je suis ici");
                bibliographies.addAll(bibliographyRepository.findAllByThese(these.getTheseId(),Sort.by(Sort.Direction.DESC,"bibliographyId")));
            }
            System.out.println(bibliographies);
        }

        model.addAttribute("livres", bibliographies);
        return "livres/livres";
    }

    @GetMapping("/livre/delete/{bibliographyId}")
    public String delete_lvre(@PathVariable Long bibliographyId){
        bibliographyRepository.deleteById(bibliographyId);
        return "redirect:/groupe/livres";
    }
    @GetMapping("/livres")
    public String all_livres(Model model, HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
       /* for (Bibliography bibliography : bibliographyRepository.findByFaculte("Sciences Economiques et Gestion")){
            bibliography.setFaculte("Sciences économiques et gestion");
            bibliographyRepository.save(bibliography);
        }*/
        List<Bibliography> bibliographies= bibliographyRepository.findAll(Sort.by(Sort.Direction.DESC,"bibliographyId"));
        List<Bibliography> bibliographies1= bibliographyRepository.findAllByUser(user.getUserId());
        List<Bibliography> bibli_user=new ArrayList<>();
        List<Bibliography> bibli_these=new ArrayList<>();
        for (Bibliography bibliography: bibliographies1){
            if (bibliography.getThese()== null){
                bibli_user.add(bibliography);
            }
        }

        for (Bibliography bibliography: bibliographies){
            if (bibliography.getThese()== null){
                bibli_these.add(bibliography);
            }
        }
        Role role1=roleRepository.findByRole("LIVRE");
        model.addAttribute("roles", user.getRoles());
        session.setAttribute("roles",user.getRoles());
        System.out.println(bibli_user);
        System.out.println(bibliographies);
        if (user.getRoles().contains(role1)){
            model.addAttribute("bibliographies", bibli_user );

        }else{
            model.addAttribute("bibliographies", bibli_these);

        }
        model.addAttribute("bibliography", new Bibliography());
        return "livres/all/livres";
    }
    @GetMapping("/rapports/{groupeId}")
    public String get_rapports(@PathVariable Long groupeId, HttpSession session, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("roles", roleRepository.findByUsers_UserId(user.getUserId()));
        Groupe groupe = groupeRepository.getOne(groupeId);
        model.addAttribute("roles",roleRepository.findByUsers_UserId(user.getUserId()));
        session.setAttribute("groupeId", groupeId);
        model.addAttribute("groupe",groupe);
        model.addAttribute("user",user);
        session.setAttribute("groupeCountry",groupe.getGroupeCountry());
        session.setAttribute("groupeRegion",groupe.getGroupeRegion());
        model.addAttribute("rapport",new Rapport());

        Collection<Role> roles= roleRepository.findByUsers_UserId(user.getUserId());
        List<Rapport> rapports= rapportRepository.findAll();
        List<Rapport> allRapports= new ArrayList<>();
        int p=0;

        for (Role role : roles){
            if (!role.getRole().equals("ROOT") || !role.getRole().equals("ROOT_MASTER")){
                p=1;
            }else {
                p=2;
            }
        }
        if (p==1){

            List<Rapport> rapportGroupe =rapportRepository.findByGroupe(groupe.getGroupeId());
            List<Rapport> rapportUser=rapportRepository.findByUser(user.getUserId());
            for (Rapport rapport : rapportGroupe){
                for (int i=0; i<rapportUser.size();i++){
                    if (rapport.getRapportId().equals(rapportUser.get(i).getRapportId())){
                        allRapports.add(rapport);
                    }
                }
            }
            model.addAttribute("rapports", allRapports);
        }else {
            model.addAttribute("rapports",rapports);
        }

        return "crew/rapports";
    }

    @GetMapping("/livres/livre/{bibliographyId}")
    public String show_livre(@PathVariable Long bibliographyId, Model model){
        Bibliography bibliography=bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("bibliography", bibliography);
        return "livres/livre";
    }

    @GetMapping("/livre/{bibliographyId}")
    public String view_livre(@PathVariable Long bibliographyId, Model model){
        Bibliography bibliography=bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("bibliography", bibliography);
        return "livres/all/livre";
    }

    // for adding a user into one crew
    @GetMapping("/add1/{groupeId}/{userId}")
    public String add( @PathVariable Long groupeId, @PathVariable String userId)
    {
        Groupe groupe = groupeRepository.getOne(groupeId);
        if (userId.equals("null")){
        }else {
            User user = userService.getById(Long.parseLong(userId));
            groupe.setUsers(user);
        }
        groupeRepository.save(groupe);
        System.out.println("ddf");
        return "redirect:/groupe/groupes";
    }
    
    // for update a crew
    @PostMapping("/update/{groupeId}")
    public String update(Groupe groupe,@PathVariable Long groupeId){

        Groupe groupe2=groupeRepository.getOne(groupeId);
        groupe.saveUsers(groupe2.getUsers());
            Groupe groupe1 = groupeRepository.saveAndFlush(groupe);
        Long userId;
        if (groupe1.getGroupChief() == ""){
            userId=null;
        }else {
            userId = Long.parseLong(groupe.getGroupChief());
        }
            Long groupeId1 = groupe1.getGroupeId();
            return "redirect:/groupe/add1/"+ groupeId1 + "/"+ userId ;

    }

    @GetMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable Long userId,HttpSession session) {

        User user=userRepository.getOne(userId);
        user.setStatus(false);
        user.setActive(false);
        userRepository.save(user);
        return "redirect:/groupe/groupe/users/"+ (Long)session.getAttribute("groupeId");
    }

    // for saving a crew
    @PostMapping("/save")
    public String save(Groupe groupe, Errors errors){
        Groupe groupe2= groupeRepository.findByGroupeName(groupe.getGroupeName());
        if (groupe2 != null){
            errors.rejectValue("groupeName","groupe.error","il existe deja une reference avec ce titre");
        }
        if (errors.hasErrors()){
            return "redirect:/groupe/groupes";
        }else {
            groupe.setStatus(true);
            Groupe groupe1 = groupeRepository.saveAndFlush(groupe);
            Long userId;
            if (groupe1.getGroupChief()== ""){
                 userId=null;
            }else {
                userId = Long.parseLong(groupe.getGroupChief());
            }
            Long groupeId = groupe1.getGroupeId();
            return "redirect:/groupe/add1/"+ groupeId + "/"+ userId ;
        }

    }

    // just to use it in the model for retrieving users ids
       public class UsersGroupe {
        private String usersIds;

        public String getUsersIds() {
            return usersIds;
        }

        public void setUsersIds(String usersIds) {
            this.usersIds = usersIds;
        }

    }
    // insight for a crew
       @GetMapping("/stats")
    public String stats(Model model, HttpSession session)
    {
        Long groupeId = (Long) session.getAttribute("groupeId");
        if (groupeId == null){
            return "redirect:/groupe/groupes";
        }else {
            List<User> users = userRepository.findByGroupes_GroupeId(groupeId);
            List<These> theses = theseRepository.findByGroupeOrderByTheseIdDesc(groupeId);
            Groupe groupe= groupeRepository.getOne(groupeId);
            model.addAttribute("groupeName",groupe.getGroupeName());
            model.addAttribute("usersSize", users.size());
            model.addAttribute("thesesSize", theses.size());
            model.addAttribute("groupe", groupe);
            return "crew/stats";
        }
    }
    
    // all the theses for a particular user
    @GetMapping("/groupe/all/user/these")
    public String findByUser( Model model, HttpSession session){

        Groupe groupe=groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        if ((Long)session.getAttribute("groupeId") == null){
            return "redirect:/groupe/groupes";
        }else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByName(auth.getName());

            List<These> theses=theseRepository.findByUserOrderByTheseIdDesc(user.getUserId());
            // transmitting the current page number to the view
            model.addAttribute("groupeName", groupe.getGroupeName());
            session.setAttribute("avatar", user.getImg());
            session.setAttribute("name", user.getName());
            model.addAttribute("theses",theses);
            model.addAttribute("these", new These());
            model.addAttribute("countries", countries);
            System.out.println();
        }
        return "crew/theses";
    }


    // updating a crew
    @GetMapping("/update/{groupeId}")
    public String update(Model model,@PathVariable Long groupeId){
        Groupe groupe=groupeRepository.getOne(groupeId);
        model.addAttribute("groupeName", groupe.getGroupeName());
        model.addAttribute("groupe",groupe);
        model.addAttribute("countries", countries);
        Collection<User> users= userRepository.findByRoles_Role("ADMIN");
        users.addAll(userRepository.findByRoles_Role("ROOT"));
        System.out.println(users);
        model.addAttribute("users", users);

        return "crew/crew";
    }
   /* // updating a crew
    @PostMapping("/update/{groupeId}")
    public String edit(Groupe groupe){
        groupeRepository.save(groupe);
        return "redirect:/groupe/groupes";
    }*/

    @PostMapping("/add/users")
    public  String addGroupUser(UsersGroupe usersGroupe, HttpSession session){
        System.out.println("jesuis la");
        Long groupeId = (Long)session.getAttribute("groupeId");
        Groupe groupe= groupeRepository.getOne(groupeId);
        Collection<Groupe> groupes= groupeRepository.findAll();
        String[] usersIds = usersGroupe.getUsersIds().split(",");
        System.out.println(usersIds[0]);
        ArrayList<Long> usersIdsLong = new ArrayList<>();
        AddGroupeUser addGroupeUser=new AddGroupeUser();

        for(int i=0;i<usersIds.length;i++)
        {
            if(!usersIds[i].isEmpty())
            usersIdsLong.add(Long.parseLong(usersIds[i]));
        }
        User tmp;
        for(Long id : usersIdsLong )
        {
            tmp=userRepository.getOne(id);
            System.out.println(tmp.getName());
            List<Groupe> crews= groupeRepository.findByUsers_UserId(tmp.getUserId());
            System.out.println(crews);
            if (!(crews.size() <= 0)) {
                for (Groupe crew : crews) {
                    System.out.println("je contiens des elements");
                    crew.removeUser(tmp);


                }
            }else {
                System.out.println("je suis vide");
                groupe.setUsers(tmp);
            }
            System.out.println(groupe.getUsers());
        }
        System.out.println(groupe.getUsers());
        System.out.println(usersIdsLong);
        groupeRepository.save(groupe);
        return "redirect:/groupe/groupe/users/"+ groupeId;

    }

    @GetMapping("/groupe/users/{groupeId}")
    public String getUsers(Model model, @PathVariable Long groupeId,HttpSession session){
        session.setAttribute("groupeId",groupeId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("name",user.getName());
        List<User> users1=userService.listAll();
        List<User> users= new ArrayList<>();
        for (int i=0;i<users1.size();i++){
            if (users1.get(i).getStatus()== true & users1.get(i).getActive()!= null){
                users.add(users1.get(i));
            }
        }
        System.out.println(users);
        Groupe groupe = groupeRepository.getOne(groupeId);
        List<User> userGroup= userRepository.findByGroupes_GroupeId(groupe.getGroupeId());
        List<User> usersGroup= new ArrayList<>();
        for (int j=0;j<userGroup.size();j++){
            if (userGroup.get(j).getStatus()== true & userGroup.get(j).getActive()!= null){
                usersGroup.add(userGroup.get(j));
            }
        }
        session.setAttribute("roles", roleRepository.findByUsers_UserId(user.getUserId()));
        model.addAttribute("users1",users);
        model.addAttribute("users", usersGroup);
        System.out.println(usersGroup);
        model.addAttribute("usersGroupe", new UsersGroupe());
        model.addAttribute("groupeName", groupe.getGroupeName());
        model.addAttribute("groupe",groupe);
        return "crew/users";
    }
    @GetMapping("/groupe/{groupeId}")
    public String get(@PathVariable Long groupeId, HttpSession session, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("roles", roleRepository.findByUsers_UserId(user.getUserId()));
        Groupe groupe = groupeRepository.getOne(groupeId);
        model.addAttribute("roles",roleRepository.findByUsers_UserId(user.getUserId()));
        session.setAttribute("groupeId", groupeId);
        model.addAttribute("groupe",groupe);
        session.setAttribute("groupeCountry",groupe.getGroupeCountry());
        session.setAttribute("groupeRegion",groupe.getGroupeRegion());
        model.addAttribute("these",new These());
        model.addAttribute("countries", countries);

        Collection<Role> roles= roleRepository.findByUsers_UserId(user.getUserId());
        List<These> theses= theseRepository.findAllByStates(true);
        List<These> allTheses= new ArrayList<>();
        int p=0;

        for (Role role : roles){
            if (!role.getRole().equals("USER")){
                p=1;
            }else {
                p=2;
            }
        }
        if (p==1){

            List<These> groupeTheses =theseRepository.findByGroupeOrderByTheseIdDesc(groupe.getGroupeId());
            for (These these : theses){
                for (int i=0; i<groupeTheses.size();i++){
                    if (these.getTheseId().equals(groupeTheses.get(i).getTheseId())){
                        allTheses.add(these);
                    }
                }
            }
            model.addAttribute("theses", allTheses);
        }else {

            List<These> userTheses= theseRepository.findByUserOrderByTheseIdDesc(user.getUserId());
            for (These these : theses){
                for (int j=0; j<userTheses.size();j++){
                    if (these.getTheseId().equals(userTheses.get(j).getTheseId())){
                        allTheses.add(these);
                    }
                }
            }
            model.addAttribute("theses",allTheses);
        }

        return "crew/theses";
    }

    @GetMapping("/delete/these/{theseId}")
    public String deleteThese(@PathVariable Long theseId, HttpSession session){

        These these=theseRepository.getOne(theseId);
        these.setStates(false);
        theseRepository.save(these);
        return "redirect:/groupe/groupe/"+ (Long)session.getAttribute("groupeId");
    }

    @GetMapping("/delete/rapport/{rapportId}")
    public String deleteRapport(@PathVariable Long rapportId, HttpSession session){

        Rapport rapport=rapportRepository.getOne(rapportId);
        rapport.setSupprime(false);
        rapportRepository.save(rapport);
        return "redirect:/groupe/rapports/"+ (Long)session.getAttribute("groupeId");
    }

    @GetMapping("/rapport/active/{rapportId}")
    public String activeRapport(@PathVariable Long rapportId, HttpSession session){

        Rapport rapport=rapportRepository.getOne(rapportId);
        if (rapport.getStatus()==true){
            rapport.setStatus(false);
        }else {
            rapport.setStatus(true);
        }
        rapportRepository.save(rapport);
        return "redirect:/groupe/rapports/"+ (Long)session.getAttribute("groupeId");
    }

    @DeleteMapping("/delete/{groupeId}")
    public String deleteById(@PathVariable Long groupeId) {
        Groupe groupe= groupeRepository.getOne(groupeId);
        groupe.setStatus(false);
        groupeRepository.save(groupe);
        return "redirect:/groupe/groupes";
    }

    @GetMapping("/groupe/these/general/edit/{theseId}/{groupeId}")
    public String getGeneral(Model model, @PathVariable Long theseId,@PathVariable Long groupeId, HttpSession session) {
        These these=theseRepository.getOne(theseId);
        session.setAttribute("userId", these.getUser().getUserId());
        Groupe groupe= groupeRepository.getOne(groupeId);
        session.setAttribute("resumes", these.getResumes());
        session.setAttribute("anotherSommaire", these.getAnotherSommaire());
        model.addAttribute("groupe", groupe);
        model.addAttribute("countries", countries);
        model.addAttribute("these1", these);
        return "crew/general";
    }

    @GetMapping("/rapport/edit/{rapportId}/{groupeId}")
    public String getGeneral_edit_rapport(Model model, @PathVariable Long rapportId,@PathVariable Long groupeId, HttpSession session) {
        Rapport rapport=rapportRepository.getOne(rapportId);
        User user= userRepository.getOne(rapport.getUser().getUserId());
        Groupe groupe= groupeRepository.getOne(groupeId);
        model.addAttribute("groupe", groupe);
        model.addAttribute("user",user);
        model.addAttribute("rapport", rapport);
        session.setAttribute("pieces",rapport.getPieces());
        session.setAttribute("supprime",rapport.getSupprime());
        session.setAttribute("status",rapport.getStatus());
        return "crew/general_rapport";
    }

    @PostMapping("/rapport/edit")
    public String save_edit_rapport(Rapport rapport, HttpSession session,Long groupeId,Long userId, @RequestParam("files") MultipartFile[] files){
        System.out.println(rapport.getPieces());
        List<FileUploadRespone> pieces= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        if (pieces.size()<=1){
            rapport.setPieces((ArrayList<String>)session.getAttribute("pieces"));
        }else {
        ArrayList<String> filesPaths = new ArrayList<String>();
        for(int i=0;i<pieces.size();i++)
        {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }
            rapport.setPieces(filesPaths);

        }
        rapport.setSupprime((Boolean) session.getAttribute("supprime"));
        rapport.setStatus((Boolean) session.getAttribute("status"));
        rapport.setGroupe(groupeRepository.getOne(groupeId));
        rapport.setUser(userRepository.getOne(userId));
        rapport.setCreated_date(new java.util.Date());
        rapportRepository.save(rapport);
        return "redirect:/groupe/rapports/"+groupeId;
    }

    @PostMapping("/rapport/save")
    public String save__rapport(Rapport rapport,Long groupeId,Long userId, @RequestParam("files") MultipartFile[] files){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        List<FileUploadRespone> pieces= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArrayList<String> filesPaths = new ArrayList<String>();
        for(int i=0;i<pieces.size();i++)
        {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }
        rapport.setPieces(filesPaths);
        rapport.setUserName(user.getName());
        rapport.setSupprime(false);
        rapport.setStatus(false);
        rapport.setGroupe(groupeRepository.getOne(groupeId));
        rapport.setUser(userRepository.getOne(userId));
        rapport.setCreated_date(new java.util.Date());
        rapportRepository.save(rapport);
        return "redirect:/groupe/rapports/"+groupeId;
    }

    @PostMapping("/groupe/these/general/edit")
    public String updateGeneral(These these, HttpSession session, Long groupeId){
        these.setUser(userRepository.getOne((Long)session.getAttribute("userId")));
        these.setGroupe(groupeRepository.getOne(groupeId));
        these.setResumes((ArrayList<String>)session.getAttribute("resumes"));
        these.setAnotherSommaire((String)session.getAttribute("anotherSommaire"));
        these.setStates(true);
        these.setStatus(false);
        theseRepository.save(these);
        return "redirect:/groupe/groupe/"+groupeId;
    }

    @GetMapping("/encadrement/chef/{groupeId}")
    public String findAllByGroupeAndCategory1(Model model, @PathVariable Long groupeId){
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByCategory("Chef des travaux");
        List<User> chiefs= new ArrayList<>();
        for (User user : users){
            for (int i=0; i<users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    chiefs.add(user);
                }
            }
        }
        Groupe groupe= groupeRepository.getOne(groupeId);
        model.addAttribute("groupe", groupe);
        model.addAttribute("users", chiefs);
        return "crew/user/chiefs";
    }

    @GetMapping("/encadrement/assistant/{groupeId}")
    public String findAllByGroupeAndCategory2(Model model, @PathVariable Long groupeId){
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByCategory("Assistant");
        List<User> assistants= new ArrayList<>();
        for (User user : users){
            for (int i=0; i<users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    assistants.add(user);
                }
            }
        }
        Groupe groupe= groupeRepository.getOne(groupeId);
        model.addAttribute("groupe", groupe);
        model.addAttribute("users", assistants);
      return "crew/user/assistants";
    }

    @GetMapping("/encadrement/encadreur/{groupeId}")
    public String findAllByGroupeAndCategory3(Model model, @PathVariable Long groupeId){
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByCategory("Encadreur");
        List<User> assistants= new ArrayList<>();
        for (User user : users){
            for (int i=0; i<users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    assistants.add(user);
                }
            }
        }
        Groupe groupe= groupeRepository.getOne(groupeId);
        model.addAttribute("groupe", groupe);
        model.addAttribute("users", assistants);
        return "crew/user/encadreurs";
    }

    @GetMapping("/encadrement/etudiant/{groupeId}")
    public String findAllByGroupeAndCategory4(Model model, @PathVariable Long groupeId){
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByCategory("Etudiant");
        List<User> students= new ArrayList<>();
        for (User user : users){
            for (int i=0; i<users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    students.add(user);
                }
            }
        }
        Groupe groupe= groupeRepository.getOne(groupeId);
        model.addAttribute("groupe", groupe);
        model.addAttribute("users", students);
        return "crew/user/etudiants";
    }

    @GetMapping("/training/{groupeId}")
    public String encadrement(Model model, @PathVariable Long groupeId, HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("roles", roleRepository.findByUsers_UserId(user.getUserId()));
        Groupe groupe=groupeRepository.getOne(groupeId);
        model.addAttribute("groupeName", groupe.getGroupeName());
        model.addAttribute("groupe",groupe);
        return "crew/encadrement";
    }

    @GetMapping("/groupe/these/{theseId}/{groupeId}")
    public String get(Model model, @PathVariable Long theseId,@PathVariable Long groupeId, HttpSession session){

        Optional<These> optional= theseRepository.findById(theseId);
        model.addAttribute("these1",optional.get());
        model.addAttribute("groupe",groupeRepository.getOne(groupeId));
        session.setAttribute("theseId", optional.get().getTheseId());
        session.setAttribute("groupeId", optional.get().getGroupe().getGroupeId());
        session.setAttribute("university", optional.get().getUniversity());
        session.setAttribute("faculty", optional.get().getFaculty());
        session.setAttribute("options", optional.get().getOptions());
        session.setAttribute("level", optional.get().getLevel());
        session.setAttribute("subject", optional.get().getSubject());
        session.setAttribute("theseDate", optional.get().getTheseDate());
        session.setAttribute("country", optional.get().getCountry());
        session.setAttribute("regions", optional.get().getRegions());
        session.setAttribute("assistant", optional.get().getAssistant());
        session.setAttribute("student", optional.get().getStudent());
        session.setAttribute("professor", optional.get().getProfesor());
        session.setAttribute("workChief", optional.get().getWorkChief());
            return "crew/these";

    }

    @GetMapping("/rapport/{rapportId}/{groupeId}")
    public String get_rapport(Model model, @PathVariable Long rapportId,@PathVariable Long groupeId, HttpSession session){

        Rapport rapport=rapportRepository.getOne(rapportId);
        model.addAttribute("rapport",rapport);
        model.addAttribute("groupe",groupeRepository.getOne(groupeId));

        return "crew/rapport";

    }

    // for saving a these
    @PostMapping("/groupe/add/update/somaire")
    public String update(These these, @RequestParam("files") MultipartFile[] files,Long userId, HttpSession session, Long groupeId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        List<FileUploadRespone> pieces = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        if (pieces.size()<=1){
            these.setResumes(these.getResumes());
        }else {
            ArrayList<String> filesPaths = new ArrayList<String>();
            for (int i = 0; i < pieces.size(); i++) {
                filesPaths.add(pieces.get(i).getFileDownloadUri());
            }
            these.setResumes(filesPaths);
        }
        Groupe groupe = groupeRepository.getOne(groupeId);
        these.setGroupe(groupe);
        if (userId != null) {
            these.setUser(userRepository.getOne(userId));
        }else {
            these.setUser(null);
        }
        these.setUniversity((String) session.getAttribute("university"));
        these.setFaculty((String) session.getAttribute("faculty"));
        these.setOptions((String) session.getAttribute("options"));
        these.setLevel((String) session.getAttribute("level"));
        these.setSubject((String) session.getAttribute("subject"));
        these.setTheseDate((String) session.getAttribute("theseDate"));
        these.setCountry((String) session.getAttribute("country"));
        these.setRegions((String) session.getAttribute("regions"));
        these.setAssistant((String) session.getAttribute("assistant"));
        these.setStudent((String) session.getAttribute("student"));
        these.setProfesor((String) session.getAttribute("professor"));
        these.setWorkChief((String) session.getAttribute("workChief"));
        these.setStates(true);
        these.setStatus(false);
        theseRepository.save(these);
        return "redirect:/groupe/groupe/these/"+these.getTheseId()+"/"+groupe.getGroupeId();
    }

    // for saving a these
    @PostMapping("/groupe/add/update/equipe")
    public String updateEquipe(These these, Long userId, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        Long groupeId = (Long) session.getAttribute("groupeId");
        Groupe groupe= groupeRepository.getOne(groupeId);
        these.setGroupe(groupe);
        if (userId != null) {
            these.setUser(userRepository.getOne(userId));
        }else {
            these.setUser(null);
        }
        these.setUniversity((String)session.getAttribute("university"));
        these.setFaculty((String)session.getAttribute("faculty"));
        these.setOptions((String)session.getAttribute("options"));
        these.setLevel((String)session.getAttribute("level"));
        these.setSubject((String)session.getAttribute("subject"));
        these.setTheseDate((String)session.getAttribute("theseDate"));
        these.setCountry((String)session.getAttribute("country"));
        these.setRegions((String)session.getAttribute("regions"));
        these.setResumes((ArrayList<String>)session.getAttribute("resumes"));
        these.setAnotherSommaire((String)session.getAttribute("anotherSommaire"));
        these.setStates(true);
        these.setStatus(false);
        theseRepository.save(these);
        return "redirect:/groupe/groupe/equipe/"+ these.getTheseId()+"/"+groupe.getGroupeId();

    }
    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
    }
    @GetMapping("/groupe/equipe/{theseId}/{groupeId}")
    public String getEquipe(Model model, @PathVariable Long theseId,@PathVariable Long groupeId, HttpSession session){
        Optional<These> optional= theseRepository.findById(theseId);
        model.addAttribute("these1",optional.get());
        session.setAttribute("theseId", optional.get().getTheseId());
        model.addAttribute("groupe", groupeRepository.getOne(groupeId));
        session.setAttribute("university", optional.get().getUniversity());
        session.setAttribute("faculty", optional.get().getFaculty());
        session.setAttribute("options", optional.get().getOptions());
        session.setAttribute("level", optional.get().getLevel());
        session.setAttribute("subject", optional.get().getSubject());
        session.setAttribute("theseDate", optional.get().getTheseDate());
        session.setAttribute("country", optional.get().getCountry());
        session.setAttribute("regions", optional.get().getRegions());
        session.setAttribute("resumes", optional.get().getResumes());
        session.setAttribute("anotherSommaire", optional.get().getAnotherSommaire());

            return "crew/these1";

    }

    @GetMapping("/groupe/biblib/{theseId}/{groupeId}")
    public String getBibLib(Model model, @PathVariable Long theseId,@PathVariable Long groupeId, HttpSession session){
        System.out.println("sdfffsfghjdg");
        Groupe groupe= groupeRepository.getOne(groupeId);
        These these= theseRepository.getOne(theseId);
        List<Bibliography>bibliographies=bibliographyRepository.findAllByThese(these.getTheseId(),Sort.by(Sort.Direction.DESC,"bibliographyId"));
        List<Bibliography> bibliographiesDispo= bibliographyRepository.findAllByDisponibility(true, Sort.by(Sort.Direction.DESC, "bibliographyId"));
        List<Bibliography> bibliographies1=new ArrayList<>();
        for (Bibliography  bibliography : bibliographies){
            for (int i=0;i<bibliographiesDispo.size();i++){
                if (bibliography.getBibliographyId().equals(bibliographiesDispo.get(i).getBibliographyId())){
                    bibliographies1.add(bibliography);
                }
            }
        }
        model.addAttribute("disponibles", bibliographies1);
        model.addAttribute("groupe",groupe);
        session.setAttribute("theseId", these.getTheseId());
        model.addAttribute("bibliothequess",bibliothequeRepository.findAllByThese(these.getTheseId()));
        model.addAttribute("travaux",studentWorkRepository.findByThese(these.getTheseId()));
        model.addAttribute("bibliotheque", new Bibliotheque());
        model.addAttribute("studentWork", new StudentWork());
        model.addAttribute("these1",these);
        model.addAttribute("bibliographies",bibliographies);
        model.addAttribute("bibliography", new Bibliography());

        return "crew/theseBibLib";

    }

    @GetMapping("/these/publish/{theseId}/{groupeId}")
    public String publishThese(@PathVariable Long theseId,@PathVariable Long groupeId, HttpSession session) {
        These these = theseRepository.getOne(theseId);
        Groupe groupe= groupeRepository.getOne(groupeId);
        these.setStatus(true);
        these.setStates(true);
        System.out.print(these.getStatus());
        System.out.print("blablabla");
        theseRepository.save(these);
        return "redirect:/groupe/groupe/these/" + theseId + "/"+groupe.getGroupeId();
    }

    @GetMapping("/these/draft/{theseId}/{groupeId}")
    public String draftThese(@PathVariable Long theseId,@PathVariable Long groupeId, HttpSession session) {
        These these = theseRepository.getOne(theseId);
        Groupe groupe= groupeRepository.getOne(groupeId);
        these.setStatus(false);
        these.setStates(true);
        theseRepository.save(these);
        return "redirect:/groupe/groupe/these/" + these.getTheseId()+"/"+ groupe.getGroupeId();
    }

    @GetMapping("/these/unPublish/form/{theseId}/{groupeId}")
    public String unPublishForm(@PathVariable Long theseId,@PathVariable Long groupeId, Model model, HttpSession session) {
        These these = theseRepository.getOne(theseId);
        Groupe groupe= groupeRepository.getOne(groupeId);

        model.addAttribute("groupe",groupe);
        model.addAttribute("countries", countries);
        model.addAttribute("these", these);
        session.setAttribute("country", these.getCountry());
        session.setAttribute("pieces",these.getResumes());
        return "crew/correction";
    }

    @PostMapping("/these/unPublish/{theseId}")
    public String unPublishPeriod(These these, HttpSession session, String adresse, Long userId, String contenue, @PathVariable Long theseId, Long groupeId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        these.setStatus(false);
        if (userId != null) {
            these.setUser(userRepository.getOne(userId));
        }else {
            these.setUser(null);
        }
        these.setGroupe(groupeRepository.getOne(groupeId));
        these.setCountry((String) session.getAttribute("country"));
        these.setStates(true);
        these.setResumes((ArrayList<String>)session.getAttribute("pieces"));
        theseRepository.save(these);
        MailService backMessage = new MailService();
        backMessage.sendSimpleMessage(
                adresse,
                "Notification de correction du contenu de cette Thèse",
                user.getName() + " vous notifi celon le contenue suivant :" + contenue + " veuillez bien prendre connaissance du message et apporter des modifications souligner"
        );
        return "redirect:/groupe/groupe/these/" + theseId+"/"+groupeId;
    }


    @GetMapping("/delete/{groupeId}")
    public String delete(@PathVariable Long groupeId){
        Groupe groupe= groupeRepository.getOne(groupeId);
        groupe.setStatus(false);
        groupeRepository.save(groupe);
        return "redirect:/groupe/groupes";
    }

    // publications management methods

    @GetMapping("/publications/livres")
    public String livre_publier(Model model, HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("roles",user.getRoles());
        model.addAttribute("roles",user.getRoles());
        List<Livre> livres= livreRepository.findAllByTypeAndSuprimeeOrderByLivreIdDesc("livre",true);
        model.addAttribute("publications",livres);
        return "publication/publications_livres";
    }

    @PostMapping("/syllabus/save")
    public String save(Syllabus syllabus,@RequestParam("file") MultipartFile file,@RequestParam("photo")MultipartFile photo, String publishPrice){
        String fileName= fileUploadService.storeFile(file);
        String fileName1= fileUploadService.storeFile(photo);

        syllabus.setPieces("/downloadFile/"+fileName);
        syllabus.setCouverture("/downloadFile/"+fileName1);
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
                        " veiller cliquer sur le lien pour etre rediriger vers la page "+"yesbanana.org/groupe/publications/syllabus");
        return "redirect:/groupe/publications/syllabus";

    }

    @GetMapping("/publications/syllabus")
    public String syllabus_publier(Model model, HttpSession session){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("roles",user.getRoles());
        model.addAttribute("roles",user.getRoles());
        List<Syllabus> syllasbus=syllabusRepository.findBySuprimeeOrderBySyllabusIdDesc(true);
        model.addAttribute("syllabus",syllasbus);
        return "publication/publications_syllabus";
    }

    @GetMapping("/publications/livre/detail/{livreId}")
    public String detail_livre(Model model, @PathVariable Long livreId,HttpSession session){
        Livre livre=livreRepository.getOne(livreId);
        session.setAttribute("livreId",livre.getLivreId());
        model.addAttribute("bibliography", new Bibliography());
        model.addAttribute("livre",livre);
        return "publication/livre";
    }

    @GetMapping("/publications/syllabus/detail/{syllabusId}")
    public String detail_syllabus(Model model, @PathVariable Long syllabusId,HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("roles",user.getRoles());
        model.addAttribute("roles",user.getRoles());
        Syllabus syllabus=syllabusRepository.getOne(syllabusId);
        session.setAttribute("syllabusId",syllabus.getSyllabusId());
        model.addAttribute("livre",syllabus);
        return "publication/syllabus";
    }

    @GetMapping("/publication/delete/{livreId}")
    public String delete_bibliography(@PathVariable Long livreId, HttpSession session){
        Livre livre=livreRepository.getOne(livreId);
        livre.setSuprimee(false);
        livreRepository.save(livre);
        return "redirect:/groupe/publications/livres";
    }

    @GetMapping("/livre/publier/{bibliographyId}")
    public String livre_publication(@PathVariable Long bibliographyId, HttpSession session){
        Bibliography bibliography=bibliographyRepository.getOne(bibliographyId);
        if (bibliography.getDisponibility()== true){
            bibliography.setDisponibility(false);
        }else {
            bibliography.setDisponibility(true);
        }

        bibliographyRepository.save(bibliography);

        return "redirect:/groupe/livres/"+session.getAttribute("facultyId");
    }
    @GetMapping("/livre/publication/{bibliographyId}")
    public String livre_publier(@PathVariable Long bibliographyId, HttpSession session){
        Bibliography bibliography=bibliographyRepository.getOne(bibliographyId);
        if (bibliography.getDisponibility()== true){
            bibliography.setDisponibility(false);
        }else {
            bibliography.setDisponibility(true);
        }

        bibliographyRepository.save(bibliography);

        return "redirect:/groupe/livres";
    }

    @GetMapping("/syllabus/publier/{syllabusId}")
    public String syllabus_publication(@PathVariable Long syllabusId){
        Syllabus syllabus=syllabusRepository.getOne(syllabusId);
        if (syllabus.getStatus()== true){
            syllabus.setStatus(false);
        }else {
            syllabus.setStatus(true);
        }

        syllabusRepository.save(syllabus);

        return "redirect:/groupe/publications/syllabus/detail/"+syllabus.getSyllabusId();
    }

    @GetMapping("/livre/update/{bibliographyId}")
    public String editer_livre(@PathVariable Long bibliographyId, Model model,HttpSession session){
        Bibliography bibliography=bibliographyRepository.getOne(bibliographyId);
        session.setAttribute("userId",bibliography.getUser().getUserId());
        session.setAttribute("disponibility",bibliography.getDisponibility());
        session.setAttribute("price",bibliography.getPrice());
        model.addAttribute("bibliography",bibliography);
        return "crew/bibliography_edit";
    }

    @PostMapping("/livre/edit")
    public String sauvegarder_modification(Bibliography bibliography, String prix,HttpSession session, @RequestParam("file")MultipartFile file, @RequestParam("document") MultipartFile document){

        String fileName = fileUploadService.storeFile(file);
        String fileName1 = fileUploadService.storeFile(document);
        if (prix.isEmpty()){
            bibliography.setPrice((Double)session.getAttribute("price"));
        }else {
            bibliography.setPrice(Double.parseDouble(prix));
        }
        bibliography.setUser(userRepository.getOne((Long)session.getAttribute("userId")));
        if (file.isEmpty()){
            bibliography.setCouverture(bibliography.getCouverture());
        }else {
            bibliography.setCouverture("/downloadFile/"+fileName);
        }

        if (document.isEmpty()){
            bibliography.setFichier(bibliography.getFichier());
        }else {
            bibliography.setFichier("/downloadFile/"+fileName1);
        }

        bibliography.setPagePrice(0.0);
        bibliography.setDisponibility((Boolean)session.getAttribute("disponibility"));

        bibliographyRepository.save(bibliography);
        return "redirect:/groupe/livres";

    }
    @PostMapping("/bibliography/save")
    public String save(Bibliography bibliography, String price, Errors errors, Model model, HttpSession session, @RequestParam("file") MultipartFile file, @RequestParam("document") MultipartFile document){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        String fileName = fileUploadService.storeFile(file);
        String fileName1 = fileUploadService.storeFile(document);
        Bibliography bibliography1= bibliographyRepository.findByTitle(bibliography.getTitle());
        Bibliography bibliography2=bibliographyRepository.findByAuteur(bibliography.getAuteur());
        if (bibliography1 != null && bibliography2!=null){
            errors.rejectValue("title","bibliography.error","il existe deja une reference avec ce titre");
        }
        if (errors.hasErrors()){
            model.addAttribute("error","il existe deja une reference avec ce titre");
            List<Bibliography> livres=new ArrayList<>();
            livres.add(bibliography1);
            model.addAttribute("bibliographies",livres);
            model.addAttribute("roles",user.getRoles());
            return "publication/publications_livres";
        }else {
            bibliography.setCouverture("/downloadFile/"+fileName);
            bibliography.setFichier("/downloadFile/"+fileName1);
            if (!price.isEmpty()){
                bibliography.setPrice(Double.parseDouble(price));
            }else {
                bibliography.setPrice(0.0);
            }
            bibliography.setPagePrice(0.0);
            bibliography.setDisponibility(false);
            bibliography.setUser(user);
            bibliographyRepository.save(bibliography);
        }
        return "redirect:/groupe/publications/livre/detail/"+ (Long)session.getAttribute("livreId");
    }

    @PostMapping("/livre/save")
    public String save_book(Bibliography bibliography, String price, Errors errors, Model model, HttpSession session, @RequestParam("file") MultipartFile file, @RequestParam("document") MultipartFile document){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        String fileName = fileUploadService.storeFile(file);
        String fileName1= fileUploadService.storeFile(document);
        List<Bibliography> bibliographies1= bibliographyRepository.findAllByTitle(bibliography.getTitle());
        List<Bibliography> bibliographies2= bibliographyRepository.findAllByAuteur(bibliography.getAuteur());

        if (!bibliographies1.isEmpty() && !bibliographies2.isEmpty()){
            System.out.println("je suis la dedans");
            errors.rejectValue("title","bibliography.error","il existe deja une reference avec ce titre");
        }
        if (errors.hasErrors()){
            model.addAttribute("error","il existe deja une reference avec ce titre");
            bibliographies1.addAll(bibliographies2);
            model.addAttribute("bibliographies",bibliographies1);
            model.addAttribute("roles",user.getRoles());
            return "livres/all/livres";
        }else {
            bibliography.setFichier("/downloadFile/"+fileName1);
            bibliography.setCouverture("/downloadFile/"+fileName);
            if (!price.isEmpty()){
                bibliography.setPrice(Double.parseDouble(price));
            }else {
                bibliography.setPrice(0.0);
            }
            bibliography.setPagePrice(0.0);
            bibliography.setDisponibility(false);
            bibliography.setUser(user);
            bibliographyRepository.save(bibliography);
        }
        return "redirect:/groupe/livres";
    }

    // groupe bourses methods
    @GetMapping("/bourses")
    public String getBourses(Model model, HttpSession session){
        List<Bourse> bourses=bourseRepository.findBySuprime(false);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        model.addAttribute("roles",user.getRoles());
        session.setAttribute("roles",user.getRoles());
        model.addAttribute("bourse",new Bourse());
        model.addAttribute("bourses",bourses);
        return "crew/bourses";
    }

    @PostMapping("/bourse/save")
    public String saveBourse(Bourse bourse, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        model.addAttribute("roles",user.getRoles());
        bourse.setStatus(false);
        bourse.setSuprime(false);
        bourseRepository.save(bourse);
        return "redirect:/groupe/bourses";
    }

    @GetMapping("/bourse/active/{bourseId}")
    public String activeBourse(@PathVariable Long bourseId){

        Bourse bourse=bourseRepository.getOne(bourseId);
        if (bourse.getStatus()== true){
            bourse.setStatus(false);
        }else {
            bourse.setStatus(true);
        }
        bourseRepository.save(bourse);
        return "redirect:/groupe/bourses";
    }

    @GetMapping("/active/syllabus/{syllabusId}")
    public String activeSyllabus(@PathVariable Long syllabusId){
        Syllabus syllabus=syllabusRepository.getOne(syllabusId);

        if (syllabus.getStatus()==true){
            syllabus.setStatus(false);
        }else {
            syllabus.setStatus(true);
        }

        syllabusRepository.save(syllabus);
        return "redirect:/groupe/publications/syllabus";
    }

    @PostMapping("/bourse/edit/{bourseId}")
    public String editBourse(@PathVariable Long bourseId, String status, Bourse bourse){

        bourse.setStatus(Boolean.parseBoolean(status));
        bourse.setSuprime(false);
        bourseRepository.save(bourse);
        return "redirect:/groupe/bourses";
    }

    @GetMapping("/bourse/delete/{bourseId}")
    public String deleteBourse(@PathVariable Long bourseId ){
        Bourse bourse= bourseRepository.getOne(bourseId);
        bourse.setSuprime(true);
        bourseRepository.save(bourse);
        return "redirect:/groupe/bourses";
    }

    @GetMapping("/bourse/edit/{bourseId}")
    public String modifier_bourse(@PathVariable Long bourseId, Model model){
        Bourse bourse=bourseRepository.getOne(bourseId);
        model.addAttribute("bourse",bourse);
        return "crew/bourse_edit";
    }

    // University information

    public String university_info(Model model){
        return "crew/university/info";
    }


}
