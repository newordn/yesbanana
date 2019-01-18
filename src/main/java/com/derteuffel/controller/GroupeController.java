


package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.RoleRepository;
import com.derteuffel.repository.TheseRepository;
import com.derteuffel.repository.UserRepository;
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
        User user=userRepository.findByEmail(auth.getName());
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
    public String save(Groupe groupe){
        Groupe groupe1 = groupeRepository.saveAndFlush(groupe);
        Long userId = Long.parseLong(groupe.getGroupChief());
        Long groupeId = groupe1.getGroupeId();
        return "redirect:/groupe/add1/"+ groupeId + "/"+ userId ;
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
    public String findByUser(Model model, @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size")Optional<Integer>size, HttpSession session){
        page.ifPresent(p->currentPage=p);
        size.ifPresent(s->pageSize=s);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        List<These> by_groupe=theseRepository.findByUserOrderByTheseIdDesc(user.getUserId());
        Groupe groupe=groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        if ((Long)session.getAttribute("groupeId") == null){
            return "redirect:/groupe/groupes";
        }else {
            Page<These> thesePage = theseService.findAllByUser(PageRequest.of(currentPage - 1, pageSize));
                for (These these:by_groupe){
                    if (these.getGroupe().getGroupeId().equals((Long)session.getAttribute("groupeId"))){
                        model.addAttribute("theses", thesePage);
                    }
                }
            int totalPages = thesePage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);

            }
            // transmitting the current page number to the view
            model.addAttribute("groupeName", groupe.getGroupeName());
            model.addAttribute("currentPage", currentPage);
            session.setAttribute("avatar", user.getImg());
            session.setAttribute("name", user.getName());
            model.addAttribute("these", new These());
            model.addAttribute("countries", countries);
            System.out.println();
            return "crew/theses";
        }
    }

    // all the theses for a crew
    @GetMapping("/groupe/all/these")
    public String findByGroupe(Model model, @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size")Optional<Integer>size, HttpSession session){
        page.ifPresent(p->currentPage=p);
        size.ifPresent(s->pageSize=s);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        Groupe groupe=groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        if ((Long)session.getAttribute("groupeId") == null){
            return "redirect:/groupe/groupes";
        }else {
            Page<These> thesePage = theseService.findAllByGroupe(PageRequest.of(currentPage - 1, pageSize), groupe.getGroupeId());
            model.addAttribute("theses", thesePage);
            int totalPages = thesePage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);

            }

            // transmitting the current page number to the view
            model.addAttribute("groupe", groupe);
            model.addAttribute("currentPage", currentPage);
            session.setAttribute("avatar", user.getImg());
            session.setAttribute("name", user.getName());
            System.out.println();
            return "crew/theses";
        }
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
/*
    //updating user role in groupe
    @GetMapping("/updateRole")
    public String updateRole(@RequestParam("userId") Long userId, @RequestParam("role") String role, HttpSession session) {
        User user = userService.getById(userId);
        Long groupeId = (Long) session.getAttribute("groupeId");
        Role role1 = roleService.getById(user.getRole().getRoleId());
        role1.setRole(role);
        roleService.saveOrUpdate(role1);
        user.setRole(role1);
        userRepository.save(user);
            return "redirect:/groupe/groupe/users/" + groupeId;


    }
    */

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
    public String groupeUser(Model model, @PathVariable Long groupeId,@RequestParam("pageSize") Optional<Integer> pageSize,
                             @RequestParam("page") Optional<Integer> page){

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
        Groupe groupe = groupeRepository.getOne(groupeId);
        model.addAttribute("users1",users1);
        model.addAttribute("usersGroupe", new UsersGroupe());
        model.addAttribute("groupe",groupe);
        Page<User> users = userRepository.findByGroupes_GroupeId(groupeId,new PageRequest(evalPage,evalPageSize));
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
    public String findById(@PathVariable Long groupeId,HttpSession session, Model model) {
        session.setAttribute("groupeId", groupeId);
        Long userId=(Long)session.getAttribute("userId");
        Groupe groupe = groupeRepository.getOne(groupeId);
        User user=userService.getById(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("these",new These());
        model.addAttribute("countries", countries);
        model.addAttribute("theses", theseRepository.findByGroupeOrderByTheseIdDesc(groupeId));
        model.addAttribute("groupeName",groupe.getGroupeName());
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
            return "redirect:/groupe/groupe/all/these";
        }else {
            return "redirect:/groupe/groupe/all/user/these";
        }



    }

    @DeleteMapping("/delete/{groupeId}")
    public String deleteById(@PathVariable Long groupeId) {
        groupeRepository.deleteById(groupeId);
        return "redirect:/groupe/groupes";
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
    public String get(Model model, @PathVariable Long theseId){

        Optional<These> optional= theseRepository.findById(theseId);
        model.addAttribute("these1",optional.get());

            return "crew/these";

    }

    @GetMapping("/equipe/{theseId}")
    public String getEquipe(Model model, @PathVariable Long theseId){
        Optional<These> optional= theseRepository.findById(theseId);
        model.addAttribute("these1",optional.get());

            return "crew/these1";

    }

    @GetMapping("/biblib/{theseId}")
    public String getBibLib(Model model, @PathVariable Long theseId){
        System.out.println("sdfffsfghjdg");
        These these= theseRepository.getOne(theseId);
        ArrayList<String> librairies= these.getLibraries();
        ArrayList<String> bibliographies = these.getBibliographies();
        System.out.println(bibliographies);
        String[] bibliography_attributes= null;
        ArrayList<String> authors= new ArrayList<>();
        for (int p=0; p< bibliographies.size();p++){
            String[] attribute= bibliographies.get(p).split(":");
            authors.add(attribute[0]);

        }
        System.out.println("sdfffsfghjdg");

        System.out.println(bibliographies);
        //System.out.println(librairies);
        System.out.println(authors);
        for(int i=0; i<bibliographies.size();i++){
            String[] attribute=bibliographies.get(i).split(":");
            bibliography_attributes=attribute;
        }
        System.out.println(bibliography_attributes);
        model.addAttribute("bibliography_attributes", bibliography_attributes);
        model.addAttribute("librairies",librairies);
        model.addAttribute("authors",authors);
        model.addAttribute("these1",these);

        return "crew/theseBibLib";

    }


}
