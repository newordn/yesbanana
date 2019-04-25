package com.derteuffel.controller;

import com.derteuffel.data.Education;
import com.derteuffel.data.Region;
import com.derteuffel.data.Secondary;
import com.derteuffel.repository.RegionRepository;
import com.derteuffel.repository.SecondaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/secondaire")
public class SecondaryController {

    @Autowired
    private SecondaryRepository secondaryRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private RegionRepository regionRepository;

    @GetMapping("/form/{regionId}")
    public String form(Model model, @PathVariable Long regionId){
        model.addAttribute("primaire",new Secondary());
        model.addAttribute("region", regionRepository.getOne(regionId));
        return "secondaire/form";

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
    public String save(Secondary secondary, @RequestParam("files")MultipartFile[] files,String montant,Long regionId,
                       @RequestParam("photo")MultipartFile photo){
        String fileName = fileUploadService.storeFile(photo);

        List<FileUploadRespone> pieces= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArrayList<String> filesPaths = new ArrayList<String>();
        for(int i=0;i<pieces.size();i++)
        {
            filesPaths.add(pieces.get(i).getFileDownloadUri());
        }

        secondary.setCouverture("/downloadFile/" + fileName);
        secondary.setPieces(filesPaths);
        secondary.setPrice(Double.parseDouble(montant));
        secondary.setLikes(0);
        secondary.setStatus(false);
        secondary.setRegion(regionRepository.getOne(regionId));

       Secondary secondary1= secondaryRepository.save(secondary);
        if (secondary1.getType().contains("colonie")){
            return "redirect:/secondaire/secondaires/colonie/"+regionRepository.getOne(regionId).getRegionId();
        }else if(secondary1.getType().contains("cours")){
            return "redirect:/secondaire/secondaires/cours/"+regionRepository.getOne(regionId).getRegionId();
        }else if(secondary1.getType().contains("anglais")){
            return "redirect:/secondaire/secondaires/langue/"+regionRepository.getOne(regionId).getRegionId();
        }else if(secondary1.getType().contains("jeux")){
            return "redirect:/secondaire/secondaires/games/"+regionRepository.getOne(regionId).getRegionId();
        }else if(secondary1.getType().contains("bibliotheque")){
            return "redirect:/secondaire/secondaires/bibliotheque/"+regionRepository.getOne(regionId).getRegionId();
        }else if(secondary1.getType().contains("transport")){
            return "redirect:/secondaire/secondaires/transport/"+regionRepository.getOne(regionId).getRegionId();
        }else {
            return "redirect:/groupe/groupes";
        }

    }

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    Date date= new Date();

    /*---- here colonies all methods  start ------*/
    @GetMapping("/secondaires/colonie/{regionId}")
    public String get9first(Model model, @PathVariable Long regionId, HttpSession session){
        session.setAttribute("regionId", regionId);
        List<Secondary> allPrimaires= secondaryRepository.findFirst12ByType("colonie de vacances", Sort.by( "educationId").descending());
        List<Secondary> primaires=new ArrayList<>();
        List<Secondary> events=new ArrayList<>();


        for(Secondary secondary : allPrimaires){
            String primaireDate=sdf.format(secondary.getRealeseDate());
            String date1=sdf.format(date);
            if (secondary.getRegion().getRegionId().equals(regionId)){
                if (!(primaireDate.compareTo(date1)>=0) ){
                    primaires.add(secondary);
                }else {
                    events.add(secondary);
                }

            }
        }

        model.addAttribute("primaires",primaires);
        model.addAttribute("listSize",primaires.size());
        model.addAttribute("events",events);
        model.addAttribute("coursesSize",events.size());
        model.addAttribute("region", regionRepository.getOne(regionId));
        return "secondaire/colonie/colonies";

    }


    @GetMapping("/secondaires/colonie/all/{regionId}")
    public String getAllPassEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("colonie de vacances");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary secondary : lists){
            String primaireDate=sdf.format(secondary.getRealeseDate());
            String date1=sdf.format(date);
            if (secondary.getRegion().getRegionId().equals(regionId)){
                if (!(primaireDate.compareTo(date1)>=0) ){
                    primaires.add(secondary);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/colonie/all";
    }

    @GetMapping("/secondaires/colonies/events/{regionId}")
    public String getAllUpCommingEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("colonie de vacances");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary primaire : lists){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (primaireDate.compareTo(date1)>=0 ){
                    primaires.add(primaire);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/colonie/events";
    }

    @GetMapping("/colonie/{educationId}")
    public String getOnecolonie(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education secondaire = secondaryRepository.getOne(educationId);
        model.addAttribute("primaire",secondaire);
        return "secondaire/colonie/secondaire";
    }

    /*---- here colonies methods end ----*/

    /*---- Here courses methods start ----*/

    @GetMapping("/secondaires/cours/{regionId}")
    public String cours(Model model, @PathVariable Long regionId){
        List<Secondary> allPrimaires= secondaryRepository.findFirst12ByType("cours d'appui", Sort.by( "educationId").descending());
        List<Secondary> primaires=new ArrayList<>();
        List<Secondary> events=new ArrayList<>();
        for(Secondary primaire : allPrimaires){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if ( !(primaireDate.compareTo(date1)>=0)){
                    primaires.add(primaire);
                }else {
                    events.add(primaire);
                }

            }
        }

        model.addAttribute("primaires",primaires);
        model.addAttribute("listSize",primaires.size());
        model.addAttribute("events",events);
        model.addAttribute("coursesSize",events.size());
        model.addAttribute("region", regionRepository.getOne(regionId));
        return "secondaire/cours/cours";

    }

    @GetMapping("/secondaires/cours/all/{regionId}")
    public String getAllPassCourseEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("cours d'appui");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary primaire : lists){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (!(primaireDate.compareTo(date1)>=0) ){
                    primaires.add(primaire);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/cours/all";
    }

    @GetMapping("/secondaires/cours/events/{regionId}")
    public String getAllUpCommingCoursesEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("cours d'appui");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary primaire : lists){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (primaireDate.compareTo(date1)>=0 ){
                    primaires.add(primaire);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/cours/events";
    }

    @GetMapping("/cours/{educationId}")
    public String getOnecourse(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = secondaryRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "secondaire/cours/secondaire";
    }



    /*----- Here courses methods end ----*/
    /*----- Here Languages methods start ----*/


    @GetMapping("/secondaires/langue/{regionId}")
    public String anglais(Model model, @PathVariable Long regionId){
        List<Secondary> allPrimaires= secondaryRepository.findFirst12ByType("anglais et/ou francais", Sort.by( "educationId").descending());
        List<Secondary> primaires=new ArrayList<>();
        List<Secondary> events=new ArrayList<>();
        for(Secondary primaire : allPrimaires){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if ( !(primaireDate.compareTo(date1)>=0)){
                    primaires.add(primaire);
                }else {
                    events.add(primaire);
                }

            }
        }

        model.addAttribute("primaires",primaires);
        model.addAttribute("listSize",primaires.size());
        model.addAttribute("events",events);
        model.addAttribute("coursesSize",events.size());
        model.addAttribute("region", regionRepository.getOne(regionId));
        return "secondaire/langue/langues";

    }

    @GetMapping("/secondaires/langue/all/{regionId}")
    public String getAllPassLangueeEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("anglais et/ou francais");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary primaire : lists){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (!(primaireDate.compareTo(date1)>=0) ){
                    primaires.add(primaire);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/langue/all";
    }

    @GetMapping("/secondaires/langue/events/{regionId}")
    public String getAllUpCommingLangueEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("anglais et/ou francais");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary primaire : lists){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (primaireDate.compareTo(date1)>=0 ){
                    primaires.add(primaire);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/langue/events";
    }

    @GetMapping("/langue/{educationId}")
    public String getOnelangue(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = secondaryRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "secondaire/langue/secondaire";
    }

/*------ Here language methods end -----*/



/*------ Here Games methods start -----*/

    @GetMapping("/secondaires/games/{regionId}")
    public String jeux(Model model, @PathVariable Long regionId){
        List<Secondary> allPrimaires= secondaryRepository.findFirst12ByType("jeux educatif", Sort.by( "educationId").descending());
        List<Secondary> primaires=new ArrayList<>();
        List<Secondary> events=new ArrayList<>();
        for(Secondary primaire : allPrimaires){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if ( !(primaireDate.compareTo(date1)>=0)){
                    primaires.add(primaire);
                }else {
                    events.add(primaire);
                }

            }
        }

        model.addAttribute("primaires",primaires);
        model.addAttribute("listSize",primaires.size());
        model.addAttribute("events",events);
        model.addAttribute("coursesSize",events.size());
        model.addAttribute("region", regionRepository.getOne(regionId));
        return "secondaire/game/games";

    }


    @GetMapping("/secondaires/games/all/{regionId}")
    public String getAllPassGameEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("jeux educatif");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary primaire : lists){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (!(primaireDate.compareTo(date1)>=0) ){
                    primaires.add(primaire);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/game/all";
    }

    @GetMapping("/secondaires/games/events/{regionId}")
    public String getAllUpCommingGameEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("jeux educatif");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary primaire : lists){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (primaireDate.compareTo(date1)>=0 ){
                    primaires.add(primaire);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/game/events";
    }

    @GetMapping("/games/{educationId}")
    public String getOneGame(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = secondaryRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "secondaire/game/secondaire";
    }

    /*------ Here Games methods end -----*/


    /*------ Here Bibliotheque methods start -----*/

    @GetMapping("/secondaires/bibliotheque/{regionId}")
    public String bibliotheque(Model model, @PathVariable Long regionId){
        List<Secondary> allPrimaires= secondaryRepository.findFirst12ByType("bibliotheque en ligne", Sort.by( "educationId").descending());
        List<Secondary> primaires=new ArrayList<>();
        List<Secondary> events=new ArrayList<>();
        for(Secondary primaire : allPrimaires){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if ( !(primaireDate.compareTo(date1)>=0)){
                    primaires.add(primaire);
                }else {
                    events.add(primaire);
                }

            }
        }

        model.addAttribute("primaires",primaires);
        model.addAttribute("listSize",primaires.size());
        model.addAttribute("events",events);
        model.addAttribute("coursesSize",events.size());
        model.addAttribute("region", regionRepository.getOne(regionId));
        return "secondaire/bibliotheque/bibliotheques";

    }

    @GetMapping("/secondaires/bibliotheque/all/{regionId}")
    public String getAllPassBibliothequeEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("bibliotheque en ligne");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary primaire : lists){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (!(primaireDate.compareTo(date1)>=0) ){
                    primaires.add(primaire);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/bibliotheque/all";
    }

    @GetMapping("/secondaires/bibliotheque/events/{regionId}")
    public String getAllUpCommingBibliothequeEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("bibliotheque en ligne");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary primaire : lists){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (primaireDate.compareTo(date1)>=0 ){
                    primaires.add(primaire);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/bibliotheque/events";
    }

    @GetMapping("/bibliotheque/{educationId}")
    public String getOneBibliotheque(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = secondaryRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "secondaire/bibliotheque/secondaire";
    }


    /*----- Here Bibliotheque methods end -----*/


    /*----- Here Transports methods start -----*/

    @GetMapping("/secondaires/transport/{regionId}")
    public String transport(Model model, @PathVariable Long regionId){
        List<Secondary> allPrimaires= secondaryRepository.findFirst12ByType("transport securise", Sort.by( "educationId").descending());
        List<Secondary> primaires=new ArrayList<>();
        List<Secondary> events=new ArrayList<>();
        for(Secondary primaire : allPrimaires){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (!(primaireDate.compareTo(date1)>=0)){
                    primaires.add(primaire);
                }else {
                    events.add(primaire);
                }

            }
        }

        model.addAttribute("primaires",primaires);
        model.addAttribute("listSize",primaires.size());
        model.addAttribute("events",events);
        model.addAttribute("coursesSize",events.size());
        model.addAttribute("region", regionRepository.getOne(regionId));
        return "secondaire/transport/transports";

    }

    @GetMapping("/secondaires/transport/all/{regionId}")
    public String getAllPassTransportEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("transport securise");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary primaire : lists){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (!(primaireDate.compareTo(date1)>=0) ){
                    primaires.add(primaire);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/transport/all";
    }

    @GetMapping("/secondaires/transport/events/{regionId}")
    public String getAllUpCommingTransportEvent(Model model, @PathVariable Long regionId){

        List<Secondary> lists= secondaryRepository.findAllByTypeOrderByEducationIdDesc("transport securise");
        List<Secondary> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Secondary primaire : lists){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (primaireDate.compareTo(date1)>=0 ){
                    primaires.add(primaire);
                }

            }
        }

        model.addAttribute("region",region);
        model.addAttribute("primaires",primaires);
        return "secondaire/transport/events";
    }

    @GetMapping("/transport/{educationId}")
    public String getOneTransport(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = secondaryRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "secondaire/transport/secondaire";
    }


    /*------ Here Transport methods end ----*/


    @GetMapping("/secondaire/{educationId}")
    public String getOne(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = secondaryRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "secondaire/secondaire";
    }

    @GetMapping("/like/{educationId}")
    public String likes(@PathVariable Long educationId){
        Education primaire= secondaryRepository.getOne(educationId);
        int n=primaire.getLikes();
        n++;
        primaire.setLikes(n);
        System.out.println(n);
        secondaryRepository.save(primaire);
        return "redirect:/secondaire/secondaire/"+primaire.getEducationId();
    }

    @GetMapping("/publish/{educationId}")
    public String publish(@PathVariable Long educationId){
        Education primaire= secondaryRepository.getOne(educationId);
        if (primaire.getStatus()== true){
            primaire.setStatus(false);
        }else {
            primaire.setStatus(true);
        }
        secondaryRepository.save(primaire);

        return "redirect:/secondaire/secondaire/"+primaire.getEducationId();
    }

}
