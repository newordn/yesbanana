package com.derteuffel.controller;

import com.derteuffel.data.Groupe;
import com.derteuffel.data.These;
import com.derteuffel.data.User;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.TheseRepository;
import com.derteuffel.repository.UserRepository;
import com.derteuffel.service.TheseService;
import com.itextpdf.text.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private GroupeRepository groupeRepository;
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
        return "crew/theseForm";
    }

    // for update a these in a crew
    @GetMapping("/add/edit/{theseId}")
    public  String editthese(Model model, @PathVariable Long theseId){
        model.addAttribute("these",theseRepository.getOne(theseId));
        return "crew/theseUpdate";
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
                aRow.createCell(12).setCellValue(these1.getResumes());
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

    // for saving a these
    @PostMapping("/add/create")
    public String save(These these, @RequestParam("file") MultipartFile file, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        String fileName= fileUploadService.storeFile(file);
        Long groupeId = (Long) session.getAttribute("groupeId");
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();


        FileUploadRespone fileUploadRespone=new FileUploadRespone(fileName,fileDownloadUri);
        these.setResumes(fileUploadRespone.getFileDownloadUri());
        Groupe groupe= groupeRepository.getOne(groupeId);
        these.setGroupe(groupe);
        these.setUser(user);
        theseRepository.save(these);
        if (!user.getRole().getRole().equals("user")){
            return "redirect:/groupe/groupe/all/these/";
        }else {
            return "redirect:/groupe/groupe/all/user/these";
        }

    }
    // for the root to save a test directly from the theses list
  /*  @PostMapping("/add/create/root")
    public String saveRoot(These these, @RequestParam("file") MultipartFile file,HttpSession session) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        String fileName= fileUploadService.storeFile(file);
        Long groupeId = (Long) session.getAttribute("groupeId");
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();


        FileUploadRespone fileUploadRespone=new FileUploadRespone(fileName,fileDownloadUri);
        these.setResumes(fileUploadRespone.getFileDownloadUri());
        Groupe groupe= groupeRepository.getOne(groupeId);
        these.setGroupe(groupe);
        these.setUser(user);
        theseRepository.save(these);
        return "redirect:/these";

    }*/



    @DeleteMapping("/delete/{theseId}")
    public void deleteById(@PathVariable Long theseId) {
        theseService.delete(theseId);
    }



    @GetMapping("/update/{theseId}")
    public  String update(Model model, @PathVariable Long theseId){
        model.addAttribute("these", theseRepository.getOne(theseId));
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
    public String get(Model model, @PathVariable Long theseId){
        Optional<These> optional= theseRepository.findById(theseId);
        model.addAttribute("these1",optional.get());
        return "these/these";
    }

    @GetMapping("/equipe/{theseId}")
    public String getEquipe(Model model, @PathVariable Long theseId){
        Optional<These> optional= theseRepository.findById(theseId);
        model.addAttribute("these1",optional.get());
        return "these/these1";
    }
    
    @GetMapping("/biblib/{theseId}")
    public String getBibLib(Model model, @PathVariable Long theseId){
        These these= theseRepository.getOne(theseId);
        String[] librairies = these.getLibrary().split(";");
        String[] bibliographies1 = these.getBibliography().split(";");
        ArrayList<String> bibliographies = new ArrayList<String>();
        
        for(String b : bibliographies1)
        {
           if( b.split(":").length>1)
           bibliographies.add(b.split(":")[1]);
        }
        model.addAttribute("librairies",librairies);
        model.addAttribute("bibliographies",bibliographies);
        model.addAttribute("these1",these);
        return "these/theseBibLib";
    }



}

