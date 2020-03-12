package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.MatiereRepository;
import com.derteuffel.repository.NiveauRepository;
import com.derteuffel.repository.PrimaireRepository;
import com.derteuffel.repository.RegionRepository;
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

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Controller
@RequestMapping("/primaire")
public class PrimaryController {

    @Autowired
    private PrimaireRepository primaireRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
   private UserService userService;
    @Autowired
    private NiveauRepository niveauRepository;

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("countries",countries);
        model.addAttribute("primaire",new Primaire());
        return "primaire/form";
    }



    @Value("${file.upload-dir}")
    private String fileStorage;

    @PostMapping("/save")
    public String save(Primaire primaire, @RequestParam("file")MultipartFile file, @RequestParam("photo")MultipartFile photo){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        if (!(file.isEmpty())){
            try{
                byte[] bytes = file.getBytes();
                Path path = Paths.get(fileStorage+file.getOriginalFilename());
                Files.write(path,bytes);
            }catch (Exception e){
                e.printStackTrace();
            }
            primaire.setPieces("/downloadFile/"+file.getOriginalFilename());
        }
        if (!(photo.isEmpty())){
            try{
                byte[] bytes = photo.getBytes();
                Path path = Paths.get(fileStorage+photo.getOriginalFilename());
                Files.write(path,bytes);
            }catch (Exception e){
                e.printStackTrace();
            }
            primaire.setCouverture("/downloadFile/"+photo.getOriginalFilename());
        }
        primaire.setStatus(false);
        primaire.setUser(user);
        primaireRepository.save(primaire);

        return "redirect:/primaire/primaires";

    }

    @PostMapping("/update/{educationId}")
    public String update(Primaire primaire, @RequestParam("file")MultipartFile file,Long userId, @RequestParam("photo")MultipartFile photo){
        User user=userService.getById(userId);
        if (!photo.isEmpty()) {
            try{
                byte[] bytes = photo.getBytes();
                Path path = Paths.get(fileStorage+photo.getOriginalFilename());
                Files.write(path,bytes);
            }catch (Exception e){
                e.printStackTrace();
            }
            primaire.setCouverture("/downloadFile/"+photo.getOriginalFilename());
        }else {
            primaire.setCouverture(primaire.getCouverture());
        }

        if (!file.isEmpty()) {
            try{
                byte[] bytes = file.getBytes();
                Path path = Paths.get(fileStorage+file.getOriginalFilename());
                Files.write(path,bytes);
            }catch (Exception e){
                e.printStackTrace();
            }
            primaire.setPieces("/downloadFile/"+file.getOriginalFilename());
        }else {
            primaire.setPieces(primaire.getPieces());
        }
        primaire.setStatus(false);
        primaire.setUser(user);
        primaireRepository.save(primaire);

        return "redirect:/primaire/primaires";

    }

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    Date date= new Date();

    /*---- here colonies all methods  start ------*/
    @GetMapping("/primaires")
    public String getAll(Model model, HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        model.addAttribute("roles",user.getRoles());
        session.setAttribute("roles",user.getRoles());
        List<Primaire> primaires=primaireRepository.findBySuprime(false);
        List<Matiere> matieres=matiereRepository.findAll();
        for (Primaire primaire : primaires){
            System.out.println("je suis la");
            Niveau niveau1=niveauRepository.findByNiveau(primaire.getClasse());
            Niveau niveau = new Niveau();
            if (niveau1 == null) {
                niveau.setNiveau(primaire.getClasse());
                if (primaire.getClasse() == 1){
                    niveau.setSlug("PREMIERE ANNEE");
                }else if (primaire.getClasse() == 2){
                    niveau.setSlug("DEUXIEME ANNEE");
                }else if (primaire.getClasse() == 3){
                    niveau.setSlug("TROISIEME ANNEE");
                }else if (primaire.getClasse() == 4){
                    niveau.setSlug("QUATRIEME ANNEE");
                }else if (primaire.getClasse() == 5){
                    niveau.setSlug("CINQUIEME ANNEE");
                }else if (primaire.getClasse() == 6){
                    niveau.setSlug("SIXIEME ANNEE");
                }else if (primaire.getClasse() == 7){
                    niveau.setSlug("SEPTIEME ANNEE");
                }else {
                    System.out.println("je ne suis pas coool");
                }
                niveauRepository.saveAndFlush(niveau);
            }else {
                System.out.println("jexiste deja");
            }
            for (Matiere matiere : matieres){
                System.out.println("deuxieme niveau");
                System.out.println(primaire.getType());
                System.out.println(primaire.getClasse());
                System.out.println(matiere.getName());
                System.out.println(matiere.getClasse());
                if (primaire.getType().contains(matiere.getName())  && primaire.getClasse() == matiere.getClasse()){
                    System.out.println("troisieme niveau");
                    primaire.setMatiere(matiereRepository.getOne(matiere.getMatiereId()));
                    matiere.setNiveau(niveauRepository.findByNiveau(matiere.getClasse()));
                    primaireRepository.save(primaire);
                }else {
                    System.out.println("incompatible");
                }
            }
        }
        model.addAttribute("primaires",primaires);
        return "primaire/primaires";

    }



    @GetMapping("/primaire/{educationId}")
    public String getOne(Model model, @PathVariable Long educationId){
        Primaire primaire=primaireRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "primaire/primaire";
    }

    @GetMapping("/primaire/edit/{educationId}")
    public String updateForm(@PathVariable Long educationId,Model model){
        Primaire primaire= primaireRepository.getOne(educationId);
        model.addAttribute("countries",countries);
        model.addAttribute("primaire",primaire);
        return "primaire/edit";
    }

    @GetMapping("/publish/{educationId}")
    public String publish(@PathVariable Long educationId){
        Primaire primaire= primaireRepository.getOne(educationId);
        if (primaire.getStatus()== true){
            primaire.setStatus(false);
        }else {
            primaire.setStatus(true);
        }
        primaireRepository.save(primaire);

        return "redirect:/primaire/primaire/"+primaire.getEducationId();
    }

    @GetMapping("/delete/{educationId}")
    public String delete(@PathVariable Long educationId){
        Primaire primaire= primaireRepository.getOne(educationId);
        if (primaire.getSuprime()== true){
            primaire.setSuprime(false);
        }else {
            primaire.setSuprime(true);
        }
        primaireRepository.save(primaire);

        return "redirect:/primaire/primaires";
    }

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

}
