package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.BibliographyRepository;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.TheseRepository;
import com.derteuffel.repository.UserRepository;
import com.derteuffel.service.MailService;
import com.derteuffel.service.TheseService;
import com.itextpdf.text.*;
import org.apache.catalina.Group;
import org.apache.poi.hpsf.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Created by derteuffel on 14/10/2018.
 */
@Controller
@RequestMapping("/these")
public class TheseController {

    // attributes
    @Autowired
    private TheseRepository theseRepository;
    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private TheseService theseService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BibliographyRepository bibliographyRepository;
    @Autowired
    private GroupeRepository groupeRepository;

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
    private String pathToDownloadFileServer = "/home3/banana/jvm/apache-tomcat-8.5.30/domains/yesbanana.org/ROOT/WEB-INF/classes/static/downloadFile/";
    
// for getting all theses
    @GetMapping("")
    public String findAllThese(Model model,
                         @RequestParam("page")Optional<Integer> page,
                         @RequestParam("size")Optional<Integer> size, HttpSession session) {
        page.ifPresent(p->currentPage=p);
        size.ifPresent(s->pageSize=s);

        List<These> theses= theseRepository.findAll();
        System.out.println(theses);
        Page<These> thesePage=theseService.findAllPaginated(PageRequest.of(currentPage-1, pageSize));
        model.addAttribute("theses", thesePage);
        int totalPages=thesePage.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }
        // transmitting the current page number to the view 
        model.addAttribute("currentPage",currentPage);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("avatar", user.getImg());
        session.setAttribute("name", user.getName());
        return "these/theses";
    }

    // for adding a these in a crew

    @GetMapping("/add/form")
    public  String theseForm1(Model model){
        model.addAttribute("these", new These());
        model.addAttribute("countries", countries);
        return "crew/theseForm";
    }

    // for update a these in a crew
    @GetMapping("/add/edit/crew/{theseId}")
    public  String editthese_groupe(Model model, @PathVariable Long theseId){
        model.addAttribute("these",theseRepository.getOne(theseId));
        model.addAttribute("countries", countries);
        return "crew/theseUpdate";
    }

    // for update a these in a crew
    @GetMapping("/add/edit/general/{theseId}")
    public  String editthese_generale(Model model, @PathVariable Long theseId){
        model.addAttribute("these",theseRepository.getOne(theseId));
        model.addAttribute("userId", theseRepository.getOne(theseId).getUser().getUserId());
        System.out.println(theseRepository.getOne(theseId).getUser().getUserId());
        model.addAttribute("groupeId",theseRepository.getOne(theseId).getGroupe().getGroupeId());
        model.addAttribute("countries", countries);
        System.out.println(theseRepository.getOne(theseId).getGroupe().getGroupeId());
        return "these/theseUpdate";
    }

    
    // a function which performs the create
    public String create(int currentPage,HSSFWorkbook workbook) throws FileNotFoundException, DocumentException, IOException
    {
            Page<These> thesePage=theseService.findAllPaginated(PageRequest.of(currentPage-1, pageSize));
            FileOutputStream fileOutputStream=null;
           String filename=null;
           try
           {
        // Obtain a workbook from the excel file
            HSSFSheet sheet=workbook.createSheet("thseses sheets");
            sheet.setDefaultColumnWidth(30);

            // create style for header cells
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            style.setFont(font);

            // create header row
            HSSFRow header = sheet.createRow(0);


            header.createCell(0).setCellValue("UNIVERSTIE");
            header.getCell(0).setCellStyle(style);

            header.createCell(1).setCellValue("FILIERE");
            header.getCell(1).setCellStyle(style);

            header.createCell(2).setCellValue("OPTION");
            header.getCell(2).setCellStyle(style);

            header.createCell(3).setCellValue("SUJET/THEME");
            header.getCell(3).setCellStyle(style);

            header.createCell(4).setCellValue("REGION");
            header.getCell(4).setCellStyle(style);

            header.createCell(5).setCellValue("ANNEE");
            header.getCell(5).setCellStyle(style);

            header.createCell(6).setCellValue("ETUDIANT");
            header.getCell(6).setCellStyle(style);

            header.createCell(7).setCellValue("ENCADREUR");
            header.getCell(7).setCellStyle(style);

            header.createCell(8).setCellValue("PRESIDENT DU JURY");
            header.getCell(8).setCellStyle(style);

            header.createCell(9).setCellValue("EXAMINATEUR");
            header.getCell(9).setCellStyle(style);

            header.createCell(10).setCellValue("BIBLIOGRAPHIE");
            header.getCell(10).setCellStyle(style);

            header.createCell(11).setCellValue("BIBLIOTHEQUE/SITE WEB");
            header.getCell(11).setCellStyle(style);

            header.createCell(12).setCellValue("SOMMAIRE");
            header.getCell(12).setCellStyle(style);

            header.setHeight((short)-1);

            // create data rows
            int rowCount = 1;

            for (These these1 : thesePage) {
                HSSFRow aRow = sheet.createRow(rowCount++);
                aRow.createCell(0).setCellValue(these1.getUniversity());
                aRow.createCell(1).setCellValue(these1.getFaculty());
                aRow.createCell(2).setCellValue(these1.getOptions());
                aRow.createCell(3).setCellValue(these1.getSubject());
                aRow.createCell(4).setCellValue(these1.getRegions());
                aRow.createCell(5).setCellValue(these1.getTheseDate());
                aRow.createCell(6).setCellValue(these1.getStudent());
                aRow.createCell(7).setCellValue(these1.getWorkChief());
                aRow.createCell(8).setCellValue(these1.getProfesor());
                aRow.createCell(9).setCellValue(these1.getAssistant());
                aRow.createCell(10).setCellValue(these1.getBibliography());
                aRow.createCell(11).setCellValue(these1.getLibrary());
            }
            filename = pathToDownloadFileServer + "theses" + currentPage + ".xls";
            fileOutputStream = new FileOutputStream(filename);
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }


    // for the creation of excel docs, don't mind the name 
    @GetMapping(value = "/createPdf/{currentPage}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody   
    public  FileSystemResource createPdf(@PathVariable("currentPage") int currentPage,HSSFWorkbook workbook, HttpServletResponse response) throws DocumentException, IOException  {
            response.setContentType("application/xls");      
            response.setHeader("Content-Disposition", "attachment; filename=" + "theses" + currentPage + ".xls");
        return new FileSystemResource(create(currentPage, workbook));
    }
    public FileUploadRespone uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadRespone(fileName, fileDownloadUri);
    }

    // for saving a these
    @PostMapping("/add/create")
    public String save(These these, @RequestParam("files") MultipartFile[] files, HttpSession session, String adresse, String contenue) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        List<FileUploadRespone> pieces= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArrayList<String> filesPaths = new ArrayList<String>();
        for(int i=0;i<pieces.size();i++)
        {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }
        String[]libariries= these.getLibrary().split(";");
        ArrayList<String> listLib=new ArrayList<>();
        for (int i=0;i<libariries.length;i++){
            listLib.add(libariries[i]);
        }
        these.setLibraries(listLib);
        these.setLibrary(null);
        String[] bibliographies1 = these.getBibliography().split(";");
        System.out.println(bibliographies1);
        these.setBibliography(null);
        Long groupeId = (Long) session.getAttribute("groupeId");
        these.setResumes(filesPaths);
        Groupe groupe= groupeRepository.getOne(groupeId);
        these.setGroupe(groupe);
        these.setUser(user);
        these.setOptions(these.getOptions().toLowerCase());
        theseRepository.save(these);
        MailService mail= new MailService();
        mail.sendSimpleMessage(
                adresse,
                "Notification de enregistrement d'une Thèse",
                user.getName()+" vous notifi celon le contenue suivant :"+ contenue+" veuillez bien prendre connaissance du message et apporter des modifications souligner"
        );
        Collection<Role> roles=user.getRoles();
        int p=0;
        for (Role role : roles){
            if (!role.getRole().equals("USER")){
                p=1;
            }
        }
        if (p==1){
            return "redirect:/groupe/groupe/all/these/";
        }else {
            return "redirect:/groupe/groupe/all/user/these";
        }

    }

    // for saving a these
    @PostMapping("/add/update/somaire")
    public String update(These these, @RequestParam("files") MultipartFile[] files, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
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
        these.setAssistant((String)session.getAttribute("assistant"));
        these.setStudent((String)session.getAttribute("student"));
        these.setProfesor((String)session.getAttribute("professor"));
        these.setWorkChief((String) session.getAttribute("workChief"));
        these.setLibraries((ArrayList<String>)session.getAttribute("libraries"));
        theseRepository.save(these);
        Collection<Role> roles=user.getRoles();
        return "redirect:/these/these/"+these.getTheseId();

    }

    // for saving a these
    @PostMapping("/add/update/equipe")
    public String updateEquipe(These these, @RequestParam("files") MultipartFile[] files, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
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
        these.setLibraries((ArrayList<String>)session.getAttribute("libraries"));
        these.setResumes((ArrayList<String>)session.getAttribute("resumes"));
        theseRepository.save(these);
        Collection<Role> roles=user.getRoles();
       return "redirect:/these/equipe/"+ these.getTheseId();

    }

    @GetMapping("/publish/{theseId}")
    public String publishPeriod(@PathVariable Long theseId, HttpSession session){
        These these= theseRepository.getOne(theseId);
        these.setStatus(true);
        theseRepository.save(these);
        return "redirect:/groupe/groupe/"+ (Long)session.getAttribute("groupeId");
    }

    @GetMapping("/draft/{theseId}")
    public String draftPeriod(@PathVariable Long theseId, HttpSession session){
        These these= theseRepository.getOne(theseId);
        these.setStatus(false);
        theseRepository.save(these);
        return "redirect:/groupe/groupe/"+(Long)session.getAttribute("groupeId");
    }

    @GetMapping("/unPublish/form/{theseId}")
    public String unPublishForm(@PathVariable Long theseId, Model model,HttpSession session){
        These these= theseRepository.getOne(theseId);
        model.addAttribute("groupeId", (Long)session.getAttribute("groupeId"));
        model.addAttribute("countries", countries);
        model.addAttribute("these", these);
        session.setAttribute("bibliographies", these.getBibliographies());
        session.setAttribute("Libraries", these.getLibraries());
        session.setAttribute("userId", these.getUser().getUserId());
        session.setAttribute("country", these.getCountry());
        return "crew/correction";
    }
    @PostMapping("/unPublish/{theseId}")
    public String unPublishPeriod(These these, HttpSession session, String adresse, String contenue){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        these.setStatus(null);
        these.setLibraries((ArrayList<String>) session.getAttribute("libraries"));
        these.setUser(userRepository.getOne((Long)session.getAttribute("userId")));
        these.setGroupe(groupeRepository.getOne((Long)session.getAttribute("groupeId")));
        these.setCountry((String)session.getAttribute("country"));
        theseRepository.save(these);
        MailService backMessage= new MailService();
        backMessage.sendSimpleMessage(
                adresse,
                "Notification de correction du contenu de cette Thèse",
                user.getName()+" vous notifi celon le contenue suivant :"+contenue+" veuillez bien prendre connaissance du message et apporter des modifications souligner"
        );
        return "redirect:/groupe/groupe/"+(Long)session.getAttribute("groupeId");
    }


    @DeleteMapping("/delete/{theseId}")
    public void deleteById(@PathVariable Long theseId) {
        theseService.delete(theseId);
    }



    @GetMapping("/update/{theseId}")
    public  String update(Model model, @PathVariable Long theseId){
        model.addAttribute("these", theseRepository.getOne(theseId));
        model.addAttribute("countries", countries);
        return "these/theseUpdate";
    }
    @GetMapping("/user/{userId}")
    public List<These> findByUser(@PathVariable Long userId){

        List<These> theses= theseRepository.findByUserOrderByTheseIdDesc(userId);

        List<These> all= theseRepository.findAll();
        //System.out.println(all);
        System.out.println(theses);
        return theses;
    }

    @GetMapping("/these/{theseId}")
    public String get(Model model, @PathVariable Long theseId,HttpSession session){

        Optional<These> optional= theseRepository.findById(theseId);
        model.addAttribute("these1",optional.get());
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
        session.setAttribute("assistant", optional.get().getAssistant());
        session.setAttribute("student", optional.get().getStudent());
        session.setAttribute("professor", optional.get().getProfesor());
        session.setAttribute("workChief", optional.get().getWorkChief());
        session.setAttribute("libraries", optional.get().getLibraries());
        return "these/these";


    }

    @GetMapping("/equipe/{theseId}")
    public String getEquipe(Model model, @PathVariable Long theseId, HttpSession session){
        Optional<These> optional= theseRepository.findById(theseId);
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
        session.setAttribute("bibliographies", optional.get().getBibliographies());
        session.setAttribute("libraries", optional.get().getLibraries());
        session.setAttribute("resumes", optional.get().getResumes());
        model.addAttribute("these1",optional.get());
           return "these/these1";


    }

    @GetMapping("/biblib/{theseId}")
    public String getBibLib(Model model, @PathVariable Long theseId){
        System.out.println("sdfffsfghjdg");
        These these= theseRepository.getOne(theseId);
        ArrayList<String> librairies= these.getLibraries();
        model.addAttribute("librairies",librairies);
        model.addAttribute("these1",these);
        model.addAttribute("bibliographies",bibliographyRepository.findAllByThese(these.getTheseId()));
        model.addAttribute("bibliography", new Bibliography());

        return "these/theseBibLib";

    }


}

