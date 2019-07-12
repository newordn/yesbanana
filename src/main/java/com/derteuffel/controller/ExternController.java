package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private BourseRepository bourseRepository;

    @Autowired
    private ColonieRepository colonieRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private StudentWorkRepository studentWorkRepository;

    @GetMapping("/catalogues")
    public String catalogues(){
        return "these_module/side/catalogues";
    }
    @GetMapping("/livres")
    public String livres(Model model){
        List<Bibliography> bibliographies=bibliographyRepository.findAllByDisponibility(true);
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
    public String livreSide(Model model,@PathVariable Long bibliographyId){
        Bibliography livre=bibliographyRepository.getOne(bibliographyId);
        model.addAttribute("livre",livre);
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

        List<Bibliography> allThesesBibliographies=bibliographyRepository.findAllByThese(these.getTheseId());
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

}
