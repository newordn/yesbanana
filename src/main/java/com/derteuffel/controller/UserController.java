package com.derteuffel.controller;


import com.derteuffel.data.*;
import com.derteuffel.repository.*;
import com.derteuffel.service.MailService;
import com.derteuffel.service.RoleService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by derteuffel on 06/10/2018.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileUploadServices fileUploadServices;
    @Autowired FileUploadService fileUploadService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AddUserRoleRepository addUserRoleRepository;

    @Autowired
    private TheseRepository theseRepository;
    @Autowired
    private BibliothequeRepository bibliothequeRepository;
    @Autowired
    private BibliographyRepository bibliographyRepository;

    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private UserService userService;

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
    @GetMapping("")
    public String allUsers(Model model) {
        List<User> users = userRepository.findAllByActiveOrderByUserIdDesc(true);
        List<User> users1= new ArrayList<>();
        for (int i=0;i<users.size();i++){
            if (users.get(i).getStatus() == true){
                users1.add(users.get(i));
            }
        }
        model.addAttribute("users", users1);
        String avatar = "";
        for (User user : users) {
            avatar = user.getImg();
            int d = avatar.indexOf("d");
            user.setImg(avatar.substring(d));
        }

        return "user/users";
    }

    @GetMapping("/detail/{userId}")
    public String user(Model model, @PathVariable Long userId, HttpSession session){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        model.addAttribute("user",user);
        AddUserRole form= new AddUserRole();

        session.setAttribute("userId", user.getUserId());
        AddUserRole editForm= addUserRoleRepository.findByUserId(user.getUserId());
        Set<Role> roles= roleService.findByGroupe(userId);
        List<Role> roles1= roleRepository.findAll();
        model.addAttribute("form",form);
        model.addAttribute("update",editForm);
        model.addAttribute("roles", roles);
        model.addAttribute("roles1", roles1);
        return "user/detail";
    }

    @GetMapping("/staffs/{userId}")
    public String getUser(Model model, @PathVariable Long userId, HttpSession session){
        session.setAttribute("userId",userId);
        User user= userService.getById(userId);
        model.addAttribute("user",user);
        AddUserRole form= new AddUserRole();
        AddUserRole editForm= addUserRoleRepository.findByUserId(user.getUserId());
        Set<Role> roles= roleService.findByGroupe(userId);
        List<Role> roles1= roleRepository.findAll();
        model.addAttribute("form",form);
        model.addAttribute("update",editForm);
        model.addAttribute("roles", roles);
        model.addAttribute("roles1", roles1);
        return "user/staffs";
    }

    @GetMapping("/view/{userId}")
    public String view(Model model, @PathVariable Long userId, HttpSession session){
        session.setAttribute("userId",userId);
        User user= userService.getById(userId);
        model.addAttribute("user",user);
        return "user/view";
    }

    @PostMapping("/user/contact")
    public String contact(String adresse, String objet, String message, HttpSession session){

        Long userId= (Long)session.getAttribute("userId");
        MailService mailService= new MailService();
        mailService.sendSimpleMessage(
                adresse,
                "Objet :"+objet,
                "Contenue :"+message
        );

        return "redirect:/user/view/"+ userId;

    }


    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {

        User user=userRepository.getOne(userId);
        user.setStatus(false);
        user.setActive(false);
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/registration")
    public String getform(Model model) {
        System.out.println(countries);
        model.addAttribute("user", new User());
        model.addAttribute("countries", countries);
        return "user/inscription";
    }


    @PostMapping("/role/save1")
    public String role(Model model, AddUserRole form, HttpSession session){
        Long userId= (Long)session.getAttribute("userId");
        Role role= roleRepository.getOne(form.getRoleId());
        System.out.println(role.getRole());
        User user= userRepository.getOne(userId);
        System.out.println(user.getName());
        user.setRoles(role);
        form.setUserId(user.getUserId());
        addUserRoleRepository.save(form);
        userRepository.save(user);
        return "redirect:/user/staffs/"+user.getUserId();
    }

    @PostMapping("/role/save")
    public String roleProfilt(@PathVariable Long userId ,Model model, AddUserRole form, HttpSession session){

        Role role= roleRepository.getOne(form.getRoleId());
        System.out.println(role.getRole());
        User user= userRepository.getOne(userId);
        System.out.println(user.getName());
        user.setRoles(role);
        form.setUserId(user.getUserId());
        addUserRoleRepository.save(form);
        userRepository.save(user);
        return "redirect:/user/staffs/"+user.getUserId();
    }

    @PostMapping("/role/update/{userId}")
    public String updateRole(AddUserRole addUserRole, @PathVariable Long userId){
        Role role= roleRepository.getOne(addUserRole.getRoleId());
        User user= userRepository.getOne(userId);
        for (int i=0;i<user.getRoles().size();i++){
            user.removeRelation(user.getRoles().get(i));
        }
        user.setRoles(role);
        addUserRole.setUserId(user.getUserId());
        addUserRoleRepository.save(addUserRole);
        userRepository.save(user);
        return "redirect:/user/staffs/"+user.getUserId();
    }
    @PostMapping("/role/update1/{userId}")
    public String updateRole1(AddUserRole addUserRole, @PathVariable Long userId){
        Role role= roleRepository.getOne(addUserRole.getRoleId());
        User user= userRepository.getOne(userId);
        System.out.println(user.getName());
        System.out.println(user.getRoles());
        for (int i=0;i<user.getRoles().size();i++){
            user.removeRelation(user.getRoles().get(i));
        }

        user.setRoles(role);
        addUserRole.setUserId(user.getUserId());
        addUserRoleRepository.save(addUserRole);
        userRepository.save(user);
        return "redirect:/user/staffs/"+user.getUserId();
    }

   /* @GetMapping("/updateRole")
    public String updateRole(@RequestParam("userId") Long userId, @RequestParam("role") String role) {
        User user = userService.getById(userId);
        Role role1 = roleService.getById(user.getRole().getRoleId());
        role1.setRole(role);
        roleService.saveOrUpdate(role1);
        user.setRole(role1);
        userRepository.save(user);
        return "redirect:/user";


    }

    @GetMapping("/update/role")
    public String updateRoles(@RequestParam("userId") Long userId, @RequestParam("role") String role, HttpSession session) {
        User user = userService.getById(userId);
        Long groupeId = (Long) session.getAttribute("groupeId");
        Role role1 = roleService.getById(user.getRole().getRoleId());
        role1.setRole(role);
        roleService.saveOrUpdate(role1);
        return "redirect:/user";
    }
    */


    private String validate_url="yesbanana.org/validate/";
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createUser(@Valid User user, Model model, @RequestParam("file") MultipartFile file, BindingResult bindingResult, String role) {
        String fileName = fileUploadServices.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
/*
            FileUploadRespone fileUploadRespone = new FileUploadRespone(fileName, fileDownloadUri);*/
        user.setImg("/downloadFile/" + fileName);
        user.setStatus(true);
        //user.setActive(true);


        User user1 = userService.findByEmail(user.getEmail());
        User user2= userService.findByName(user.getName());
        model.addAttribute("countries", countries);
        if (user1 != null) {

            bindingResult.rejectValue("email", "user.error", "There is already a user registered with the email provided");
        }
        if (user2 != null){
            bindingResult.rejectValue("name", "user.error", "There is already a user registered with the name provided");

        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Il existe un utilisateur avec le même email ou alors le meme nom d'utilisateur.");
            return "user/inscription";
        } else {
            userService.saveOrUpdate(user);

            MailService mailService = new MailService();
            mailService.sendSimpleMessage(
                    user.getEmail(),
                    "Bienvenue sur Yesbanana votre inscription a été éffectuer avec succès",
                    "veuillez cliquer sur le lien de confirmation "+validate_url+user.getUserId()+ " pour terminer votre inscription et" +
                            " profiter pleinement de votre espace membre"
            );

            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de s'inscrire " +
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");


            return "redirect1";
        }
    }

    @PostMapping(value = "/visitor/create/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String create_visitor(HttpSession session,@Valid User user, Model model, @RequestParam("file") MultipartFile file, BindingResult bindingResult, String role) {
        String fileName = fileUploadServices.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
/*
            FileUploadRespone fileUploadRespone = new FileUploadRespone(fileName, fileDownloadUri);*/
        user.setImg("/downloadFile/" + fileName);
        user.setStatus(true);
        //user.setActive(true);
        User user1 = userService.findByEmail(user.getEmail());
        if (user1 != null) {

            bindingResult.rejectValue("email", "user.error", "There is already a user registered with the email provided");
        }
        User user2 = userService.findByName(user.getName());
        if (user2 != null) {

            bindingResult.rejectValue("name", "user.error", "There is already a user registered with the name provided");
        }
        model.addAttribute("countries", countries);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Il existe un utilisateur avec le même email.");
            return "user/visitor";
        } else {
            User user3=
            userService.saveOrUpdate(user);

            MailService mailService = new MailService();
            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de s'inscrire et a ajouté une publication qui est en suspend " +
                            "sur la plateforme YesBanana. Veuillez vous connectez pour apprecier et traiter sa publication.");

            session.setAttribute("userId",userService.findByEmail(user3.getEmail()).getUserId());
            model.addAttribute("post",new Post());
            return "visitor/book";
        }
    }

    @GetMapping("/visitor/form")
    public String visitor(Model model){
        model.addAttribute("countries",countries);
        model.addAttribute("user", new User());
        return "user/visitor";
    }
    @PostMapping(value = "/create/visitor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createVisitor(@Valid User user, Model model, @RequestParam("file") MultipartFile file, @RequestParam("cvFile") MultipartFile cvFile, BindingResult bindingResult, String role) {
        String fileName = fileUploadServices.storeFile(file);
        user.setImg("/downloadFile/" + fileName);
        if (!cvFile.isEmpty()) {
            String fileNameCv = fileUploadService.storeFile(cvFile);

            user.setCv("/downloadFile/" + fileNameCv);
        }
        user.setStatus(true);
        //user.setActive(true);

        User user1 = userService.findByEmail(user.getEmail());
        if (user1 != null) {

            bindingResult.rejectValue("email", "user.error", "There is already a user registered with the email provided");
        }
        User user2 = userService.findByName(user.getName());
        if (user2 != null) {

            bindingResult.rejectValue("name", "user.error", "There is already a user registered with the name provided");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Il existe un utilisateur avec le même email.");
            model.addAttribute("countries",countries);
            if (user.getCategory().equals("Assistant")|user.getCategory().equals("Professeur")|user.getCategory().equals("Chef des travaux")){
                return "visitor/expertu";
            }else if (user.getDiplom() !=null && user.getDiplom().equals("Master1&2")|user.getDiplom().equals("Phd/Doctorat")){
                return "visitor/expertp";
            }else if (user.getCategory().equals("Primaire")){
                return "visitor/teacher";
            }else if (user.getCategory().equals("Secondaire")){
                return "visitor/teachers";
            }else if (user.getCategory().equals("Etudiant")){
                return "visitor/student";
            }
            return "user/userForm";

        } else {
            userService.saveOrUpdate(user);

            MailService mailService = new MailService();
            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur a partir du module emploi",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de s'inscrire  en tant que" + user.getAnotherDetail()+
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");


            return "redirect2";
        }
    }

    @GetMapping("/update")
    public String update(Model model, HttpSession session) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        session.setAttribute("avatar", user.getImg());
        session.setAttribute("name", user.getName());
        User user1=userService.getById(user.getUserId());
        model.addAttribute("countries", countries);
        model.addAttribute("user", user1);
        model.addAttribute("groupes", groupeRepository.findAll());
        System.out.println(groupeRepository.findAll());
        if (user1.getAutorization().equals(true)){
            return "user/user1";
        }else {
            return "user/user";
        }




    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public String updateUser ( User user, @RequestParam("file") MultipartFile file, @RequestParam("cvFile") MultipartFile cvFile){
            if(!file.isEmpty()) {
                String fileName = fileUploadService.storeFile(file);

                user.setImg("/downloadFile/" + fileName);

            }
            else
            {
                user.setImg(user.getImg());
            }
        if (!cvFile.isEmpty()){
            String fileNameCv= fileUploadServices.storeFile(cvFile);
                user.setCv("/downloadFile/"+fileNameCv);
        }else {
            user.setCv(user.getCv());
        }

        AddUserRole addUserRole= addUserRoleRepository.findByUserId(user.getUserId());
        Role role= roleRepository.getOne(addUserRole.getRoleId());
        System.out.println(role.getRole());



        if (user.getPassword().isEmpty()){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user1 = userService.findByName(auth.getName());
        System.out.println(user1.getPassword());
            user.setPassword(user1.getPassword());
            user.setActive(true);
            user.setStatus(true);
            user.addRoles(user1.getRoles());
            user.setGroupes(user1.getGroupes());
            addUserRole.setUserId(user.getUserId());
            userService.update(user);
            MailService mailService = new MailService();

            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de modifier son profil " +
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");



            return "redirect:/groupe/groupes";

        }

        else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(true);
            userService.update(user);
            MailService mailService = new MailService();
            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de modifier son profil " +
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");



            return "redirect:/logout";

        }



        }


    @PostMapping(value = "/modifier", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateUser1 ( User user, @RequestParam("file") MultipartFile file){
        if(!file.isEmpty()) {
            String fileName = fileUploadServices.storeFile(file);
            user.setImg("/downloadFile/" + fileName);
        }
        else
        {
            user.setImg(user.getImg());
        }

        if (user.getPassword().isEmpty()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user1 = userService.findByName(auth.getName());
            System.out.println(user1.getPassword());
            user.setPassword(user1.getPassword());
            user.setActive(true);
            user.setStatus(true);
            userService.update(user);
            MailService mailService = new MailService();

            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de modifier son profil " +
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");



            return "redirect:/groupe/groupes";

        }

        else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(true);
            user.setStatus(true);
            userService.update(user);
            MailService mailService = new MailService();
            mailService.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "YesBanana: Notification Inscription d'un utilisateur",
                    "L'utilisateur " + user.getName() + " dont l'email est " +
                            user.getEmail()+ "  Vient de modifier son profil " +
                            "sur la plateforme YesBanana. Veuillez vous connectez pour manager son status.");



            return "redirect:/logout";

        }
    }

    @GetMapping("/these/{theseId}")
    public String get(Model model, @PathVariable Long theseId){

        Optional<These> optional= theseRepository.findById(theseId);
        model.addAttribute("these1",optional.get());
        return "user/these/these";

    }

    @GetMapping("/equipe/{theseId}")
    public String getEquipe(Model model, @PathVariable Long theseId, HttpSession session){
        Optional<These> optional= theseRepository.findById(theseId);
        model.addAttribute("these1",optional.get());
        return "user/these/these1";

    }

    @GetMapping("/biblib/{theseId}")
    public String getBibLib(Model model, @PathVariable Long theseId, HttpSession session){
        System.out.println("sdfffsfghjdg");
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
        session.setAttribute("theseId", these.getTheseId());
        model.addAttribute("bibliothequess",bibliothequeRepository.findAllByThese(these.getTheseId()));
        model.addAttribute("bibliotheque", new Bibliotheque());
        model.addAttribute("these1",these);
        model.addAttribute("bibliographies",bibliographies);
        model.addAttribute("bibliography", new Bibliography());

        return "user/these/theseBibLib";

    }

}