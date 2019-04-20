


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
    FileUploadService fileUploadService;
    @Autowired
    private BibliographyRepository bibliographyRepository;
    @Autowired
    private BibliothequeRepository bibliothequeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PanierRepository panierRepository;

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
    public String findAllByParentOrderByGroupeIdDesc(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        session.setAttribute("userId",user.getUserId());
        session.setAttribute("avatar",user.getImg());
        session.setAttribute("name", user.getName());

            model.addAttribute("groupe", new Groupe());
            session.setAttribute("roles", roleRepository.findByUsers_UserId(user.getUserId()));
        model.addAttribute("roles",roleRepository.findByUsers_UserId(user.getUserId()));

        Role role= roleRepository.findByRole("VISITOR");

        if (!user.getRoles().containsAll(new HashSet<Role>(Arrays.asList(role)))) {
            System.out.println("je suis dedans" );
            model.addAttribute("countries", countries);
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
            model.addAttribute("users2", users2);
            model.addAttribute("crews", groupes);
            model.addAttribute("users", users);
            model.addAttribute("crews1", crews);
            return "crew/crews";
        }else {
            System.out.println("je suis visiteur");
            return "redirect:"+session.getAttribute("lastUrl");
        }


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

    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5,6,7,8};

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

    @DeleteMapping("/delete/{groupeId}")
    public String deleteById(@PathVariable Long groupeId) {
        Groupe groupe= groupeRepository.getOne(groupeId);
        groupe.setStatus(false);
        groupeRepository.save(groupe);
        return "redirect:/groupe/groupes";
    }

    @GetMapping("/groupe/these/general/edit/{theseId}/{groupeId}")
    public String getGeneral(Model model, @PathVariable Long theseId,@PathVariable Long groupeId, HttpSession session) {
        Optional<These> optional = theseRepository.findById(theseId);
        session.setAttribute("userId", optional.get().getUser().getUserId());
        Groupe groupe= groupeRepository.getOne(groupeId);
        session.setAttribute("resumes", optional.get().getResumes());
        session.setAttribute("anotherSommaire", optional.get().getAnotherSommaire());
        model.addAttribute("groupe", groupe);
        model.addAttribute("countries", countries);
        model.addAttribute("these1", optional.get());
        return "crew/general";
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

        Groupe groupe= groupeRepository.getOne(groupeId);
        model.addAttribute("groupe", groupe);
        return "crew/user/chiefs";
    }

    @GetMapping("/encadrement/assistant/{groupeId}")
    public String findAllByGroupeAndCategory2(Model model, @PathVariable Long groupeId){
        Groupe groupe= groupeRepository.getOne(groupeId);
        model.addAttribute("groupe", groupe);
      return "crew/user/assistants";
    }

    @GetMapping("/encadrement/encadreur/{groupeId}")
    public String findAllByGroupeAndCategory3(Model model, @PathVariable Long groupeId){
        Groupe groupe= groupeRepository.getOne(groupeId);
        model.addAttribute("groupe", groupe);
        return "crew/user/encadreurs";
    }

    @GetMapping("/encadrement/etudiant/{groupeId}")
    public String findAllByGroupeAndCategory4(Model model, @PathVariable Long groupeId){

        Groupe groupe= groupeRepository.getOne(groupeId);
        model.addAttribute("groupe", groupe);
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
        session.setAttribute("userId",optional.get().getUser().getUserId());
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

    // for saving a these
    @PostMapping("/groupe/add/update/somaire")
    public String update(These these, @RequestParam("files") MultipartFile[] files, HttpSession session, Long groupeId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        List<FileUploadRespone> pieces = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArrayList<String> filesPaths = new ArrayList<String>();
        for (int i = 0; i < pieces.size(); i++) {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }
        these.setResumes(filesPaths);
        Groupe groupe = groupeRepository.getOne(groupeId);
        these.setGroupe(groupe);
        these.setUser(userRepository.getOne((Long) session.getAttribute("userId")));
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
    public String updateEquipe(These these, @RequestParam("files") MultipartFile[] files, HttpSession session) {
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
        Long groupeId = (Long) session.getAttribute("groupeId");
        these.setResumes(filesPaths);
        Groupe groupe= groupeRepository.getOne(groupeId);
        these.setGroupe(groupe);
        these.setUser(userRepository.getOne((Long)session.getAttribute("userId")));
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
        session.setAttribute("userId",optional.get().getUser().getUserId());
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
        List<Bibliography>bibliographies=bibliographyRepository.findAllByThese(these.getTheseId());
        List<Bibliography> bibliographiesDispo= bibliographyRepository.findAllByDisponibility(true);
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
        model.addAttribute("bibliotheque", new Bibliotheque());
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
        return "redirect:/groupe/groupe/these/" + theseId+"/"+ groupe.getGroupeId();
    }

    @GetMapping("/these/unPublish/form/{theseId}/{groupeId}")
    public String unPublishForm(@PathVariable Long theseId,@PathVariable Long groupeId, Model model, HttpSession session) {
        These these = theseRepository.getOne(theseId);
        Groupe groupe= groupeRepository.getOne(groupeId);

        model.addAttribute("groupe",groupe);
        model.addAttribute("countries", countries);
        model.addAttribute("these", these);
        session.setAttribute("userId", these.getUser().getUserId());
        session.setAttribute("country", these.getCountry());
        return "crew/correction";
    }

    @PostMapping("/these/unPublish/{theseId}")
    public String unPublishPeriod(These these, HttpSession session, String adresse, String contenue, @PathVariable Long theseId, Long groupeId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        these.setStatus(false);
        these.setUser(userRepository.getOne((Long) session.getAttribute("userId")));
        these.setGroupe(groupeRepository.getOne(groupeId));
        these.setCountry((String) session.getAttribute("country"));
        these.setStates(true);
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


}
