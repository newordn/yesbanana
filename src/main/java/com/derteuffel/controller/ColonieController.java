package com.derteuffel.controller;

import com.derteuffel.data.Colonie;
import com.derteuffel.data.Reservation;
import com.derteuffel.data.User;
import com.derteuffel.repository.ColonieRepository;
import com.derteuffel.repository.ReservationRepository;
import com.derteuffel.service.MailService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by derteuffel on 06/06/2019.
 */
@Controller
@RequestMapping("/colonie")
public class ColonieController {

    @Autowired
    private ColonieRepository colonieRepository;
    @Autowired
    private UserService userService;
    @Autowired
            private ReservationRepository reservationRepository;

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

    @Value("${file.upload-dir}")
    private String fileStorage;

    @GetMapping("/colonies")
    public String lists_all(Model model, HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        List<Colonie> lists=colonieRepository.findByStatus(true, Sort.by(Sort.Direction.DESC,"colonieId"));
        model.addAttribute("roles",user.getRoles());
        session.setAttribute("roles", user.getRoles());
        model.addAttribute("colonies",lists);
        model.addAttribute("colonie", new Colonie());
        model.addAttribute("countries",countries);
        return "colonie/colonies";
    }


    @GetMapping("/detail/{colonieId}")
    public String view(@PathVariable Long colonieId, Model model,HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        Colonie colonie =colonieRepository.getOne(colonieId);
        session.setAttribute("colonieId",colonie.getColonieId());
        model.addAttribute("user",user);
        model.addAttribute("colonie", colonie);
        model.addAttribute("reservation",new Reservation());
        model.addAttribute("countries", countries);
        return "colonie/colonie";
    }


    @PostMapping("/save")
    public String save(Colonie colonie, @RequestParam("files")MultipartFile[] files,@RequestParam("couverture")MultipartFile couverture, String prix, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        if (couverture.isEmpty()){
            System.out.println("je suis vide");
            colonie.setCover("/downloadFile/new/images/blog_1.jpg");
        }else {
            if (!(couverture.isEmpty())){
                try{
                    byte[] bytes = couverture.getBytes();
                    Path path = Paths.get(fileStorage+couverture.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
                colonie.setCover("/downloadFile/"+couverture.getOriginalFilename());
            }
        }
        ArrayList<String> filesPaths = new ArrayList<String>();

        for (MultipartFile file : files){
            if (!(file.isEmpty())){
                try{
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(fileStorage+file.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
                filesPaths.add("/downloadFile/"+file.getOriginalFilename());
            }
        }
        colonie.setFichier(filesPaths);


        if (colonie.getActivite().length()< 150){

            model.addAttribute("errors", "ce le  champs description doit contenir au moins 150 charactere");
            return "colonie/colonies";
        }else {

            colonie.setPrice(Double.parseDouble(prix));
            colonie.setStatus(true);
            colonie.setActive(false);
            colonieRepository.save(colonie);
            MailService backMessage = new MailService();
            backMessage.sendSimpleMessage(
                    "solutioneducationafrique@gmail.com",
                    "Notification de l'ajout d'un evenement dans le module de colonie des vancances, Titre de l'activite : " + colonie.getTitle(),
                    user.getName() + " vous notifi celon le contenue suivant :  veuillez bien prendre connaissance du message et apporter des modifications souligner"
            );

            return "redirect:/colonie/detail/" + colonie.getColonieId();
        }

    }

    @PostMapping("/update")
    public String update(Colonie colonie, @RequestParam("files")MultipartFile[] files,@RequestParam("couverture")MultipartFile couverture, String prix){
        System.out.println(colonie.getFichier());
        if (prix.isEmpty()){
            colonie.setPrice(colonie.getPrice());
        }else {
            colonie.setPrice(Double.parseDouble(prix));
        }

        if (couverture.isEmpty()){
            colonie.setCover(colonie.getCover());
        }else {
            if (!(couverture.isEmpty())){
                try{
                    byte[] bytes = couverture.getBytes();
                    Path path = Paths.get(fileStorage+couverture.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
                colonie.setCover("/downloadFile/"+couverture.getOriginalFilename());
            }
        }

        ArrayList<String> filesPaths = new ArrayList<String>();

        for (MultipartFile file : files){
            if (!(file.isEmpty())){
                try{
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(fileStorage+file.getOriginalFilename());
                    Files.write(path,bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
                filesPaths.add("/downloadFile/"+file.getOriginalFilename());
            }
        }
        colonie.setFichier(filesPaths);

                 colonieRepository.save(colonie);

        return "redirect:/colonie/detail/"+colonie.getColonieId();
    }

    @GetMapping("/colonies/{pays}")
    public String findByCountry(Model model, @PathVariable String pays){
        List<Colonie> colonies=colonieRepository.findByPaysAndStatusOrderByColonieIdDesc(pays,true);
        model.addAttribute("colonies",colonies);
        model.addAttribute("countries", countries);
        return "colonie/colonies";

    }

    @GetMapping("/activation/{colonieId}")
    public String active(@PathVariable Long colonieId){
        Colonie colonie= colonieRepository.getOne(colonieId);
        if (colonie.getActive()==true){
            colonie.setActive(false);
        }else {
            colonie.setActive(true);
        }
        colonieRepository.save(colonie);
        return "redirect:/colonie/colonies";
    }

    @GetMapping("/delete/{colonieId}")
    public String delete(@PathVariable Long colonieId){
        Colonie colonie= colonieRepository.getOne(colonieId);
        if (colonie.getStatus()==true){
            colonie.setStatus(false);
        }else {
            colonie.setStatus(true);
        }
        colonieRepository.save(colonie);
        return "redirect:/colonie/colonies";
    }

    @GetMapping("/reservations/{colonieId}")
    public String reservations(Model model, @PathVariable Long colonieId,HttpSession session){
        Colonie colonie=colonieRepository.getOne(colonieId);

        model.addAttribute("colonieId", colonie.getColonieId());
        session.setAttribute("colonieId", colonie.getColonieId());
        model.addAttribute("colonie", colonie);
        List<Reservation> reservations=reservationRepository.findByColonie(colonie.getColonieId());
        model.addAttribute("reservations", reservations);
        return "colonie/reservations";

    }


    @GetMapping("/activation/reservation/{reservationId}")
    public String reservation_activation(@PathVariable Long reservationId, HttpSession session){
            Reservation reservation=reservationRepository.getOne(reservationId);
        if (reservation.getStatus()== true){
            reservation.setStatus(false);
        }else {
            reservation.setStatus(true);
        }

        reservationRepository.save(reservation);
        return "redirect:/colonie/reservations/"+(Long)session.getAttribute("colonieId");
    }

    @GetMapping("/reservation/{reservationId}")
    public  String reservation(Model model, @PathVariable Long reservationId){
        Reservation reservation=reservationRepository.getOne(reservationId);
        model.addAttribute("reservation",reservation);
        return "colonie/reservation";
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

    @GetMapping("/reservations/paid/{colonieId}")
    public String reservation_paid(Model model, @PathVariable Long colonieId){

        Colonie colonie= colonieRepository.getOne(colonieId);
        List<Reservation> reservations=reservationRepository.findByColonieAndStatus(colonie.getColonieId(),true);
        List<Reservation> reservations1=reservationRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        List<String> regions=new ArrayList<>();
        List<String> activites=new ArrayList<>();
        for (Reservation reservation : reservations1){

            countries.add(reservation.getPays());
            saisons.add(reservation.getSaison());
            sites.add(reservation.getSite());
            regions.add(reservation.getRegion());
            activites.add(reservation.getActivite());
        }

        model.addAttribute("colonie", colonie);
        model.addAttribute("reservations", reservations);
        model.addAttribute("countries", removeDuplicates(countries));
        model.addAttribute("saisons", removeDuplicates(saisons));
        model.addAttribute("sites", removeDuplicates(sites));
        model.addAttribute("regions", removeDuplicates(regions));
        model.addAttribute("activites",  removeDuplicates(activites));
        return "colonie/payed_reservations";
    }

    @GetMapping("/reservations/unpaid/{colonieId}")
    public String reservation_unpaid(Model model, @PathVariable Long colonieId){

        Colonie colonie= colonieRepository.getOne(colonieId);
        List<Reservation> reservations=reservationRepository.findByColonieAndStatus(colonie.getColonieId(),false);
        List<Reservation> reservations1=reservationRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        List<String> regions=new ArrayList<>();
        List<String> activites=new ArrayList<>();
        for (Reservation reservation : reservations1){

            countries.add(reservation.getPays());
            saisons.add(reservation.getSaison());
            sites.add(reservation.getSite());
            regions.add(reservation.getRegion());
            activites.add(reservation.getActivite());
        }

        model.addAttribute("colonie", colonie);
        model.addAttribute("reservations", reservations);
        model.addAttribute("countries", removeDuplicates(countries));
        model.addAttribute("saisons", removeDuplicates(saisons));
        model.addAttribute("sites", removeDuplicates(sites));
        model.addAttribute("regions", removeDuplicates(regions));
        model.addAttribute("activites",  removeDuplicates(activites));
        return "colonie/payed_reservations";
    }
    @GetMapping("/reservation/country/{pays}/{colonieId}")
    public String reservation_pays(Model model, @PathVariable String pays, @PathVariable Long colonieId){

        Colonie colonie= colonieRepository.getOne(colonieId);
        List<Reservation> reservations=reservationRepository.findByColonieAndStatusAndPays(colonie.getColonieId(),pays,true);
        List<Reservation> reservations1=reservationRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        List<String> regions=new ArrayList<>();
        List<String> activites=new ArrayList<>();
        for (Reservation reservation : reservations1){

            countries.add(reservation.getPays());
            saisons.add(reservation.getSaison());
            sites.add(reservation.getSite());
            regions.add(reservation.getRegion());
            activites.add(reservation.getActivite());
        }

        model.addAttribute("colonie", colonie);
        model.addAttribute("reservations", reservations);
        model.addAttribute("countries", removeDuplicates(countries));
        model.addAttribute("saisons", removeDuplicates(saisons));
        model.addAttribute("sites", removeDuplicates(sites));
        model.addAttribute("regions", removeDuplicates(regions));
        model.addAttribute("activites",  removeDuplicates(activites));
        return "colonie/payed_reservations";
    }

    @GetMapping("/reservation/region/{region}/{colonieId}")
    public String reservation_regions(Model model, @PathVariable String region, @PathVariable Long colonieId){

        Colonie colonie= colonieRepository.getOne(colonieId);
        List<Reservation> reservations=reservationRepository.findByColonieAndStatusAndRegion(colonie.getColonieId(),region,true);
        List<Reservation> reservations1=reservationRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        List<String> regions=new ArrayList<>();
        List<String> activites=new ArrayList<>();
        for (Reservation reservation : reservations1){

            countries.add(reservation.getPays());
            saisons.add(reservation.getSaison());
            sites.add(reservation.getSite());
            regions.add(reservation.getRegion());
            activites.add(reservation.getActivite());
        }

        model.addAttribute("colonie", colonie);
        model.addAttribute("reservations", reservations);
        model.addAttribute("countries", removeDuplicates(countries));
        model.addAttribute("saisons", removeDuplicates(saisons));
        model.addAttribute("sites", removeDuplicates(sites));
        model.addAttribute("regions", removeDuplicates(regions));
        model.addAttribute("activites",  removeDuplicates(activites));
        return "colonie/payed_reservations";
    }

    @GetMapping("/reservation/saison/{saison}/{colonieId}")
    public String reservation_saison(Model model, @PathVariable String saison, @PathVariable Long colonieId){

        Colonie colonie= colonieRepository.getOne(colonieId);
        List<Reservation> reservations=reservationRepository.findByColonieAndStatusAndSaison(colonie.getColonieId(),saison,true);
        List<Reservation> reservations1=reservationRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        List<String> regions=new ArrayList<>();
        List<String> activites=new ArrayList<>();
        for (Reservation reservation : reservations1){

            countries.add(reservation.getPays());
            saisons.add(reservation.getSaison());
            sites.add(reservation.getSite());
            regions.add(reservation.getRegion());
            activites.add(reservation.getActivite());
        }

        model.addAttribute("colonie", colonie);
        model.addAttribute("reservations", reservations);
        model.addAttribute("countries", removeDuplicates(countries));
        model.addAttribute("saisons", removeDuplicates(saisons));
        model.addAttribute("sites", removeDuplicates(sites));
        model.addAttribute("regions", removeDuplicates(regions));
        model.addAttribute("activites",  removeDuplicates(activites));
        return "colonie/payed_reservations";
    }

    @GetMapping("/reservation/site/{site}/{colonieId}")
    public String reservation_site(Model model, @PathVariable String site, @PathVariable Long colonieId){

        Colonie colonie= colonieRepository.getOne(colonieId);
        List<Reservation> reservations=reservationRepository.findByColonieAndStatusAndSite(colonie.getColonieId(),site,true);
        List<Reservation> reservations1=reservationRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        List<String> regions=new ArrayList<>();
        List<String> activites=new ArrayList<>();
        for (Reservation reservation : reservations1){

            countries.add(reservation.getPays());
            saisons.add(reservation.getSaison());
            sites.add(reservation.getSite());
            regions.add(reservation.getRegion());
            activites.add(reservation.getActivite());
        }

        model.addAttribute("colonie", colonie);
        model.addAttribute("reservations", reservations);
        model.addAttribute("countries", removeDuplicates(countries));
        model.addAttribute("saisons", removeDuplicates(saisons));
        model.addAttribute("sites", removeDuplicates(sites));
        model.addAttribute("regions", removeDuplicates(regions));
        model.addAttribute("activites",  removeDuplicates(activites));
        return "colonie/payed_reservations";
    }

    @GetMapping("/reservation/activite/{activite}/{colonieId}")
    public String reservation_activite(Model model, @PathVariable String activite, @PathVariable Long colonieId){

        Colonie colonie= colonieRepository.getOne(colonieId);
        List<Reservation> reservations=reservationRepository.findByColonieAndStatusAndActivite(colonie.getColonieId(),activite,true);
        List<Reservation> reservations1=reservationRepository.findAll();
        List<String> saisons=new ArrayList<>();
        List<String> countries=new ArrayList<>();
        List<String> sites=new ArrayList<>();
        List<String> regions=new ArrayList<>();
        List<String> activites=new ArrayList<>();
        for (Reservation reservation : reservations1){

            countries.add(reservation.getPays());
            saisons.add(reservation.getSaison());
            sites.add(reservation.getSite());
            regions.add(reservation.getRegion());
            activites.add(reservation.getActivite());
        }

        System.out.println(countries);
        System.out.println(saisons);
        System.out.println(sites);
        System.out.println(regions);
        System.out.println(activites);
        model.addAttribute("colonie", colonie);
        model.addAttribute("reservations", reservations);
        model.addAttribute("countries", removeDuplicates(countries));
        model.addAttribute("saisons", removeDuplicates(saisons));
        model.addAttribute("sites", removeDuplicates(sites));
        model.addAttribute("regions", removeDuplicates(regions));
        model.addAttribute("activites",  removeDuplicates(activites));
        return "colonie/payed_reservations";
    }
}
