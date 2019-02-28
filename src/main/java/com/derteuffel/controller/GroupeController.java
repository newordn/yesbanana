


package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.*;
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
        User user=userRepository.findByName(auth.getName());
        session.setAttribute("userId",user.getUserId());
        session.setAttribute("avatar",user.getImg());
        session.setAttribute("name", user.getName());
        model.addAttribute("groupe", new Groupe());
        model.addAttribute("countries", countries);
        Collection<User> users1 = userRepository.findByRoles_Role("ADMIN");
        List<Groupe> groupes= groupeRepository.findAll();
        List<Groupe> crews= new ArrayList<>();
        List<Groupe> crews1= new ArrayList<>();
        Collection<User> users= new ArrayList<>();
        users1.addAll(userRepository.findByRoles_Role("ROOT"));
        System.out.println(users1);
        int p=0;
        for(Role role : user.getRoles()){
            if (role.getRole().equals("ROOT")){
                users.addAll(users1);
                crews.addAll(groupes);
                    p=1;
            }else {
                crews1.addAll(groupeRepository.findByUsers_UserId(user.getUserId()));
            }
        }
        System.out.println(users);

        if (p==1){
            model.addAttribute("crews",crews);
            model.addAttribute("users",users);
            System.out.println(p);
            return "crew/crews";
        }else {
            model.addAttribute("crews1",crews1);
            model.addAttribute("users",users);
            System.out.println(p);
            return "crew/crews1";
        }


    }

    // for adding a user into one crew
    @GetMapping("/add1/{groupeId}/{userId}")
    public String add( @PathVariable Long groupeId, @PathVariable Long userId)
    {
        Groupe groupe = groupeRepository.getOne(groupeId);
        User user = userService.getById(userId);
        groupe.setUsers(user);
        groupeRepository.save(groupe);
        System.out.println("ddf");
        return "redirect:/groupe/groupes";
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
            Groupe groupe1 = groupeRepository.saveAndFlush(groupe);
            Long userId = Long.parseLong(groupe.getGroupChief());
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
       @GetMapping("/groupe/stats")
    public String stats(Model model, HttpSession session)
    {
        Long groupeId = (Long) session.getAttribute("groupeId");
        if (groupeId == null){
            return "redirect:/groupe/groupes";
        }else {
            List<User> users = userRepository.findByGroupes_GroupeId(groupeId);
            List<These> theses = theseRepository.findByGroupeOrderByTheseIdDesc(groupeId);
            model.addAttribute("usersSize", users.size());
            model.addAttribute("thesesSize", theses.size());
            model.addAttribute("groupe", groupeRepository.getOne(groupeId));
            return "crew/stats";
        }
    }
    
    // all the theses for a particular user
    @GetMapping("/groupe/all/user/these")
    public String findByUser(Model model, @RequestParam("pageSize") Optional<Integer> pageSize,
                             @RequestParam("page") Optional<Integer> page, HttpSession session){
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        Groupe groupe=groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        if ((Long)session.getAttribute("groupeId") == null){
            return "redirect:/groupe/groupes";
        }else {
            Page<These> by_groupe=theseRepository.findByUserOrderByTheseIdDesc(user.getUserId(),new PageRequest(evalPage,evalPageSize));
            PagerModel pager = new PagerModel(by_groupe.getTotalPages(),by_groupe.getNumber(),BUTTONS_TO_SHOW);// evaluate page size
            model.addAttribute("selectedPageSize", evalPageSize);
            // add pages size
            model.addAttribute("pageSizes", PAGE_SIZES);
            // add pager
            model.addAttribute("pager", pager);
            model.addAttribute("theses",by_groupe);

            }
            // transmitting the current page number to the view
            model.addAttribute("groupeName", groupe.getGroupeName());
            session.setAttribute("avatar", user.getImg());
            session.setAttribute("name", user.getName());
            model.addAttribute("these", new These());
            model.addAttribute("countries", countries);
            System.out.println();
            return "crew/theses";

    }


    // updating a crew
    @GetMapping("/update/{groupeId}")
    public String update(Model model,@PathVariable Long groupeId){
        Groupe groupe=groupeRepository.getOne(groupeId);
        model.addAttribute("groupeName", groupe.getGroupeName());
        model.addAttribute("groupe",groupe);
        model.addAttribute("countries", countries);
        Collection<User> users= userRepository.findByRoles_Role("user");
        System.out.println(users);
        model.addAttribute("users", users);

        return "crew/crew";
    }

    @PostMapping("/add/users")
    public  String addGroupUser(UsersGroupe usersGroupe, HttpSession session){
        Long groupeId = (Long)session.getAttribute("groupeId");
        Groupe groupe= groupeRepository.getOne(groupeId);
        String[] usersIds = usersGroupe.getUsersIds().split(",");
        ArrayList<Long> usersIdsLong = new ArrayList<>();

        for(int i=0;i<usersIds.length;i++)
        {
            if(!usersIds[i].isEmpty())
            usersIdsLong.add(Long.parseLong(usersIds[i]));
        }
        System.out.println(usersIdsLong);
        for ( Long id: usersIdsLong)
        {
            groupe.setUsers(userService.getById(id));
        }
        groupeRepository.save(groupe);
        return "redirect:/groupe/groupe/users/"+ groupeId;

    }

    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5,6,7,8};

    @GetMapping("/groupe/users/{groupeId}")
    public String getUsers(@PathVariable Long groupeId){
        return "redirect:/groupe/groupe/users";
    }
    @GetMapping("/groupe/users")
    public String groupeUser(Model model,@RequestParam("pageSize") Optional<Integer> pageSize,
                             @RequestParam("page") Optional<Integer> page, HttpSession session){

    //
    // Evaluate page size. If requested parameter is null, return initial
    // page size
    int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
    // Evaluate page. If requested parameter is null or less than 0 (to
    // prevent exception), return initial size. Otherwise, return value of
    // param. decreased by 1.
    int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
    // print repo

        List<User> users1=userService.listAll();
        Groupe groupe = groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        model.addAttribute("users1",users1);
        model.addAttribute("usersGroupe", new UsersGroupe());
        model.addAttribute("groupe",groupe);
        Page<User> users = userRepository.findByGroupes_GroupeId((Long)session.getAttribute("groupeId"),new PageRequest(evalPage,evalPageSize));
    PagerModel pager = new PagerModel(users.getTotalPages(),users.getNumber(),BUTTONS_TO_SHOW);// evaluate page size
    model.addAttribute("selectedPageSize", evalPageSize);
    // add pages size
    model.addAttribute("pageSizes", PAGE_SIZES);
    // add pager
    model.addAttribute("pager", pager);
    System.out.println(users);
        model.addAttribute("users",users);
        return "crew/users";

    }


    @GetMapping("/groupe/{groupeId}")
    public String get(@PathVariable Long groupeId, HttpSession session){
        session.setAttribute("groupeId", groupeId);
        return "redirect:/groupe/groupe";
    }

    @GetMapping("/groupe")
    public String findById(HttpSession session, Model model, @RequestParam("pageSize") Optional<Integer> pageSize,
                           @RequestParam("page") Optional<Integer> page) {
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        Long userId=(Long)session.getAttribute("userId");
        Groupe groupe = groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        session.setAttribute("groupeCountry",groupe.getGroupeCountry());
        session.setAttribute("groupeRegion",groupe.getGroupeRegion());
        User user=userService.getById(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("these",new These());
        model.addAttribute("countries", countries);
        model.addAttribute("groupeName",groupe.getGroupeName());
        Page<These> by_groupe=theseRepository.findByUserOrderByTheseIdDesc(user.getUserId(),new PageRequest(evalPage,evalPageSize));
        Page<These> groupThese= theseRepository.findByGroupeOrderByTheseIdDesc(groupe.getGroupeId(), new PageRequest(evalPage,evalPageSize));


        Collection<Role> roles= roleRepository.findByUsers_UserId(user.getUserId());
        int p=0;
        for (Role role : roles){
        if (!role.getRole().equals("USER")){
            p=1;
        }else {
            p=2;
        }
        }
        if (p==1){
            PagerModel pager = new PagerModel(groupThese.getTotalPages(),groupThese.getNumber(),BUTTONS_TO_SHOW);// evaluate page size
            model.addAttribute("selectedPageSize", evalPageSize);
            // add pages size
            model.addAttribute("pageSizes", PAGE_SIZES);
            // add pager
            model.addAttribute("pager", pager);
            model.addAttribute("theses",groupThese );

            return "crew/theses";

        }else {
            PagerModel pager = new PagerModel(by_groupe.getTotalPages(),by_groupe.getNumber(),BUTTONS_TO_SHOW);// evaluate page size
            model.addAttribute("selectedPageSize", evalPageSize);
            // add pages size
            model.addAttribute("pageSizes", PAGE_SIZES);
            // add pager
            model.addAttribute("pager", pager);
            model.addAttribute("theses",by_groupe);
            return "crew/theses";
        }



    }

    @DeleteMapping("/delete/{groupeId}")
    public String deleteById(@PathVariable Long groupeId) {
        groupeRepository.deleteById(groupeId);
        return "redirect:/groupe/groupes";
    }

    @GetMapping("/these/general/edit/{theseId}")
    public String getGeneral(Model model, @PathVariable Long theseId, HttpSession session) {
        Optional<These> optional = theseRepository.findById(theseId);
        session.setAttribute("userId", optional.get().getUser().getUserId());
        session.setAttribute("groupeId", optional.get().getGroupe().getGroupeId());
        session.setAttribute("resumes", optional.get().getResumes());
        model.addAttribute("countries", countries);
        model.addAttribute("these1", optional.get());
        return "crew/general";
    }

    @PostMapping("/these/general/edit")
    public String updateGeneral(These these, HttpSession session){
        these.setUser(userRepository.getOne((Long)session.getAttribute("userId")));
        these.setGroupe(groupeRepository.getOne((Long)session.getAttribute("groupeId")));
        these.setResumes((ArrayList<String>)session.getAttribute("resumes"));
        theseRepository.save(these);
        return "redirect:/groupe/groupe/"+(Long)session.getAttribute("groupeId");
    }
    @GetMapping("/")
    public String findAll(){
        return "crew/crews";
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
    public String encadrement(Model model, @PathVariable Long groupeId){
        Groupe groupe=groupeRepository.getOne(groupeId);
        model.addAttribute("groupe",groupe);
        return "crew/encadrement";
    }

    @GetMapping("/these/{theseId}")
    public String get(Model model, @PathVariable Long theseId, HttpSession session){

        Optional<These> optional= theseRepository.findById(theseId);
        model.addAttribute("these1",optional.get());
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
    @PostMapping("/add/update/somaire")
    public String update(These these, @RequestParam("files") MultipartFile[] files, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(auth.getName());
        List<FileUploadRespone> pieces = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArrayList<String> filesPaths = new ArrayList<String>();
        for (int i = 0; i < pieces.size(); i++) {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }
        Long groupeId = (Long) session.getAttribute("groupeId");
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
        theseRepository.save(these);
        return "redirect:/groupe/these/"+these.getTheseId();
    }

    // for saving a these
    @PostMapping("/add/update/equipe")
    public String updateEquipe(These these, @RequestParam("files") MultipartFile[] files, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
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
        theseRepository.save(these);
        return "redirect:/groupe/equipe/"+ these.getTheseId();

    }
    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
    }
    @GetMapping("/equipe/{theseId}")
    public String getEquipe(Model model, @PathVariable Long theseId, HttpSession session){
        Optional<These> optional= theseRepository.findById(theseId);
        model.addAttribute("these1",optional.get());
        session.setAttribute("theseId", optional.get().getTheseId());
        session.setAttribute("userId",optional.get().getUser().getUserId());
        session.setAttribute("groupeId", optional.get().getGroupe().getGroupeId());
        session.setAttribute("university", optional.get().getUniversity());
        session.setAttribute("faculty", optional.get().getFaculty());
        session.setAttribute("options", optional.get().getOptions());
        session.setAttribute("level", optional.get().getLevel());
        session.setAttribute("subject", optional.get().getSubject());
        session.setAttribute("theseDate", optional.get().getTheseDate());
        session.setAttribute("country", optional.get().getCountry());
        session.setAttribute("regions", optional.get().getRegions());
        session.setAttribute("resumes", optional.get().getResumes());

            return "crew/these1";

    }

    @GetMapping("/biblib/{theseId}")
    public String getBibLib(Model model, @PathVariable Long theseId, HttpSession session){
        session.setAttribute("theseId",theseId);
        System.out.println("sdfffsfghjdg");
        These these= theseRepository.getOne(theseId);
        session.setAttribute("theseId", these.getTheseId());
        model.addAttribute("bibliothequess",bibliothequeRepository.findAllByThese(these.getTheseId()));
        model.addAttribute("bibliotheque", new Bibliotheque());
        model.addAttribute("these1",these);
        model.addAttribute("bibliographies",bibliographyRepository.findAllByThese(these.getTheseId()));
        model.addAttribute("bibliography", new Bibliography());

        return "crew/theseBibLib";

    }

    @GetMapping("/delete/{groupeId}")
    public String delete(@PathVariable Long groupeId){
        groupeRepository.deleteById(groupeId);
        return "redirect:/groupe/groupes";
    }


}
