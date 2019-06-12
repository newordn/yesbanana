package com.derteuffel.controller;

import com.derteuffel.data.Colonie;
import com.derteuffel.data.User;
import com.derteuffel.repository.ColonieRepository;
import com.derteuffel.service.MailService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FileUploadService fileUploadService;
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
    public String view(@PathVariable Long colonieId, Model model){
        Colonie colonie =colonieRepository.getOne(colonieId);
        model.addAttribute("colonie", colonie);
        model.addAttribute("countries", countries);
        return "colonie/colonie";
    }

    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
    }

    @PostMapping("/save")
    public String save(Colonie colonie, @RequestParam("files")MultipartFile[] files,@RequestParam("couverture")MultipartFile couverture, String prix){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        String fileName = fileUploadService.storeFile(couverture);
        if (couverture.isEmpty()){
            System.out.println("je suis vide");
            colonie.setCover("/downloadFile/new/images/blog_1.jpg");
        }else {
            System.out.println("je suis charger");
            colonie.setCover("/downloadFile/"+fileName);
        }

        List<FileUploadRespone> pieces= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        if (pieces.size() == 0) {
            colonie.setFichier(colonie.getFichier());
        }else {
            ArrayList<String> filesPaths = new ArrayList<String>();
            for(int i=0;i<pieces.size();i++)
            {

                filesPaths.add(pieces.get(i).getFileDownloadUri());

            }
            colonie.setFichier(filesPaths);

        }

        colonie.setPrice(Double.parseDouble(prix));
        colonie.setStatus(true);
        colonie.setActive(false);
        colonieRepository.save(colonie);
        MailService backMessage = new MailService();
        backMessage.sendSimpleMessage(
                "solutioneducationafrique@gmail.com",
                "Notification de l'ajout d'un evenement dans le module de colonie des vancances, Titre de l'activite : "+ colonie.getTitle(),
                user.getName() + " vous notifi celon le contenue suivant :  veuillez bien prendre connaissance du message et apporter des modifications souligner"
        );

        return "redirect:/colonie/detail/"+colonie.getColonieId();

    }

    @PostMapping("/update")
    public String update(Colonie colonie, @RequestParam("files")MultipartFile[] files,@RequestParam("couverture")MultipartFile couverture, String prix){
        System.out.println(colonie.getFichier());
        if (prix.isEmpty()){
            colonie.setPrice(colonie.getPrice());
        }else {
            colonie.setPrice(Double.parseDouble(prix));
        }

        String fileName = fileUploadService.storeFile(couverture);
        if (couverture.isEmpty()){
            colonie.setCover(colonie.getCover());
        }else {
            colonie.setCover("/downloadFile/"+fileName);
        }

        List<FileUploadRespone> pieces = Arrays.asList(files)
                    .stream()
                    .map(file -> uploadFile(file))
                    .collect(Collectors.toList());
            if (pieces.size() <= 1) {
                System.out.println("je suis la");
                System.out.println(pieces.size());
                colonieRepository.save(colonie);
            } else {
                ArrayList<String> filesPaths = new ArrayList<String>();
                for (int i = 0; i < pieces.size(); i++) {

                    filesPaths.add(pieces.get(i).getFileDownloadUri());

                }
                colonie.setFichier(filesPaths);
                colonieRepository.save(colonie);
            }


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
}
