    package com.derteuffel.controller;


import com.derteuffel.data.*;
import com.derteuffel.repository.*;
import com.derteuffel.service.MailService;
import com.derteuffel.service.RoleService;
import com.derteuffel.service.UserService;
import java.net.MalformedURLException;
import java.net.URL;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by derteuffel on 06/10/2018.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AddUserRoleRepository addUserRoleRepository;
    @Autowired
    AddGroupeUserRepository addGroupeUserRepository;

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



    @GetMapping("/users")
    public String allUsers(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        List<User> users = userRepository.findAllByActiveOrderByUserIdDesc(true);
        List<User> users1= new ArrayList<>();
        for (int i=0;i<users.size();i++){
            if (users.get(i).getStatus() == true){
                users1.add(users.get(i));
            }
        }

        model.addAttribute("roles", user.getRoles());
        session.setAttribute("roles",user.getRoles());
        model.addAttribute("users", users1);
       /* String avatar = "";
        for (User user : users) {
            avatar = user.getImg();
            int d = avatar.indexOf("d");
            user.setImg(avatar.substring(d));
        }*/

        return "user/users";
    }




    @GetMapping("/detail/{userId}")
    public String user(Model model, @PathVariable Long userId, HttpServletRequest request, HttpSession session){

        session.setAttribute("lastUrl", request.getHeader("referer"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByName(auth.getName());
        model.addAttribute("user",user);
        AddUserRole form= new AddUserRole();

        session.setAttribute("userId", user.getUserId());
        Set<Role> roles= roleService.findByGroupe(userId);
        List<Role> roles1= roleRepository.findAll();
        model.addAttribute("form",form);
        model.addAttribute("roles", roles);
        model.addAttribute("roles1", roles1);
        return "user/detail";
    }

    @GetMapping("/staffs/{userId}")
    public String getUser(Model model, @PathVariable Long userId,HttpServletRequest request, HttpSession session){
        session.setAttribute("lastUrl", request.getHeader("referer"));
        session.setAttribute("userId",userId);
        User user= userService.getById(userId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user1=userService.findByName(auth.getName());
        model.addAttribute("user",user);
        AddUserRole form= new AddUserRole();
        AddGroupeUser form1= new AddGroupeUser();
        Set<Role> roles= roleRepository.findByUsers_UserId(user1.getUserId());
        List<Groupe> groupes=groupeRepository.findByUsers_UserId(userId);
        System.out.println(groupes);
        List<Role> roles1= roleRepository.findAll();
        List<Groupe> groupes1=groupeRepository.findAllByStatus(true);
        model.addAttribute("groupes1",groupes1);
        model.addAttribute("groupes",groupes);
        model.addAttribute("form",form);
        model.addAttribute("form1",form1);
        model.addAttribute("roles", roles);
        session.setAttribute("roles",roles);
        model.addAttribute("roles1", roles1);
        return "user/staffs";
    }

    @GetMapping("/livre/{bibliographyId}")
    public String livre(@PathVariable Long bibliographyId, Model model){
        Bibliography bibliography=bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("bibliography", bibliography);
        return "user/livre";
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
        user.setActive(null);
        userRepository.save(user);
        return "redirect:/user/users";
    }

    @GetMapping("/registration")
    public String getform(Model model) {
        System.out.println(countries);
        model.addAttribute("user", new User());
        model.addAttribute("countries", countries);
        return "user/inscription";
    }



    @PostMapping("/role/save")
    public String roleProfil( Long userId , Long roleId, HttpSession session){

        Role role= roleRepository.getOne(roleId);
        System.out.println(role.getRole());
        User user= userRepository.getOne(userId);
        System.out.println(user.getName());
        user.getRoles().clear();
        user.getRoles().add(role);
        userRepository.save(user);
        return "redirect:"+session.getAttribute("lastUrl");
    }

    @PostMapping("/groupe/save")
    public String groupeProfil( Long userId , Long groupeId, HttpSession session){

        Groupe groupe= groupeRepository.getOne(groupeId);
        System.out.println(groupe.getGroupeName());
        User user= userRepository.getOne(userId);
        System.out.println(user.getName());
        user.getGroupes().clear();
        user.getGroupes().add(groupe);
        userRepository.save(user);
        return "redirect:"+session.getAttribute("lastUrl");
    }

    @Value("${file.upload-dir}")
    private String fileStorage;


    private String validate_url="yesbanana.org/validate/";
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createUser(@Valid User user, Model model, @RequestParam("file") MultipartFile file, BindingResult bindingResult, String role) {
        if (!(file.isEmpty())){
            try{
                byte[] bytes = file.getBytes();
                Path path = Paths.get(fileStorage+file.getOriginalFilename());
                Files.write(path,bytes);
            }catch (Exception e){
                e.printStackTrace();
            }
            user.setImg("/downloadFile/"+file.getOriginalFilename());
        }
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
            user.setPar_mobile(false);
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
        if (!(file.isEmpty())){
            try{
                byte[] bytes = file.getBytes();
                Path path = Paths.get(fileStorage+file.getOriginalFilename());
                Files.write(path,bytes);
            }catch (Exception e){
                e.printStackTrace();
            }
            user.setImg("/downloadFile/"+file.getOriginalFilename());
        }
        user.setStatus(true);
        user.setPar_mobile(false);
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
        if (!(file.isEmpty())){
            try{
                byte[] bytes = file.getBytes();
                Path path = Paths.get(fileStorage+file.getOriginalFilename());
                Files.write(path,bytes);
            }catch (Exception e){
                e.printStackTrace();
            }
           user.setImg("/downloadFile/"+file.getOriginalFilename());
        }
        if (!cvFile.isEmpty()) {
            if (!(cvFile.isEmpty())){
                try{
                    byte[] bytes = cvFile.getBytes();
                    Path path = Paths.get(fileStorage+cvFile.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
               user.setCv("/downloadFile/"+file.getOriginalFilename());
            }
        }
        user.setStatus(true);
        user.setPar_mobile(false);
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
        session.setAttribute("par_mobile", user.getPar_mobile());
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
        public String updateUser ( User user, @RequestParam("file") MultipartFile file,HttpSession session, @RequestParam("cvFile") MultipartFile cvFile){
            if(!file.isEmpty()) {
                if (!(file.isEmpty())){
                    try{
                        byte[] bytes = file.getBytes();
                        Path path = Paths.get(fileStorage+file.getOriginalFilename());
                        Files.write(path,bytes);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    user.setImg("/downloadFile/"+file.getOriginalFilename());
                }

            }
            else
            {
                user.setImg(user.getImg());
            }
        if (!cvFile.isEmpty()){
            if (!(cvFile.isEmpty())){
                try{
                    byte[] bytes = cvFile.getBytes();
                    Path path = Paths.get(fileStorage+cvFile.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
                user.setCv("/downloadFile/"+cvFile.getOriginalFilename());
            }
        }else {
            user.setCv(user.getCv());
        }

        AddUserRole addUserRole= addUserRoleRepository.findByUserId(user.getUserId());
        Role role= roleRepository.getOne(addUserRole.getRoleId());
        System.out.println(role.getRole());

        user.setPar_mobile((Boolean)session.getAttribute("par_mobile"));


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
            if (!(file.isEmpty())){
                try{
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(fileStorage+file.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
              user.setImg("/downloadFile/"+file.getOriginalFilename());
            }
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
        List<Bibliography>bibliographies=bibliographyRepository.findAllByThese(these.getTheseId(),Sort.by(Sort.Direction.DESC,"bibliographyId"));
        List<Bibliography> bibliographiesDispo= bibliographyRepository.findAllByDisponibility(true,Sort.by(Sort.Direction.DESC,"bibliographyId"));
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


    @GetMapping("/visitor/buy/form")
    public String visitorForm(Model model, HttpServletRequest request){
        try {
            model.addAttribute("sharelink_old", getURLBase(request) + "/visitor/livres"); // Lien de la page à poster
            model.addAttribute("sharelink", "https://www.yesbanana.org/visitor/livres");
        } catch (MalformedURLException ex) {
            model.addAttribute("sharelink", "https://www.yesbanana.org/visitor/livres");
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("sharevia", ""); // Uniquement pour twitter, le nom de yesbanana sur twitter
        model.addAttribute("sharetext", ""); // Uniquement pour twitter, un texte qui prérempli le tweet
        model.addAttribute("countries", countries );
        model.addAttribute("user", new User());
        return "user/visitorForm";

    }

    public static final String ACCOUNT_SID="AC0f5e1b6e060cd761f654ac85a408a019";
    public static final String AUTH_TOKEN="53e67820fc938b3be43d725d1d2e96a9";
    public static final String TWILIO_NUMBER="+12082161713";

    @PostMapping("/visitor/buy/save")
    public String visitorSave(User user, Errors errors, HttpSession session,String numbers1,String numbers2,String numbers3){

        User user1= userRepository.findByEmail(user.getEmail());
        if (user1 != null){
            errors.rejectValue("email", "user.error", "There is already a user registered with the email provided");
        }

        User user2= userRepository.findByName(user.getName());
        if (user2 != null){
            errors.rejectValue("name", "user.error", "There is already a user registered with the name provided");

        }

        Role role=roleRepository.findByRole("VISITOR");

        if (role != null){

            user.addRoles((new HashSet<Role>(Arrays.asList(role))));

        }else {
            Role role1=new Role("VISITOR");
            roleRepository.save(role1);
            user.addRoles(new HashSet<Role>(Arrays.asList(role1)));
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setStatus(true);
        user.setAutorization(false);
        user.setNumberOfWorkers(false);
       // Twilio.init(ACCOUNT_SID,AUTH_TOKEN);

        List<String> numbers= new ArrayList<>();
        numbers.add(numbers1);
        numbers.add(numbers2);
        numbers.add(numbers3);

        //sent sms for local numbers
      /*  if (!numbers1.isEmpty()) {
            Message message = Message.creator(
                    new PhoneNumber(numbers1),
                    new PhoneNumber(TWILIO_NUMBER),
                    "Decouvrez la bibliotheque numerique disponible pour vous directement sur le lien -->")
                    .setMediaUrl("https://www.yesbanana.org/visitor/livres")
                    .create();
        }

        if (!numbers2.isEmpty()) {
            Message message1 = Message.creator(
                    new PhoneNumber(numbers2),
                    new PhoneNumber(TWILIO_NUMBER),
                    "Decouvrez la bibliotheque numerique disponible pour vous directement sur le lien -->")
                    .setMediaUrl("https://www.yesbanana.org/visitor/livres")
                    .create();
        }

        if (!numbers3.isEmpty()) {
            Message message2 = Message.creator(
                    new PhoneNumber(numbers3),
                    new PhoneNumber(TWILIO_NUMBER),
                    "Decouvrez la bibliotheque numerique disponible pour vous directement sur le lien -->")
                    .setMediaUrl("https://www.yesbanana.org/visitor/livres")
                    .create();
        }*/


        userRepository.save(user);




        return "redirect:/login/visitor";
    }
    
    public String getURLBase(HttpServletRequest request) throws MalformedURLException {

        URL requestURL = new URL(request.getRequestURL().toString());
        String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
        return requestURL.getProtocol() + "://" + requestURL.getHost() + port;

    }



}