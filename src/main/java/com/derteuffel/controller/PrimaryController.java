package com.derteuffel.controller;

import com.derteuffel.data.Education;
import com.derteuffel.data.Primaire;
import com.derteuffel.data.Region;
import com.derteuffel.repository.PrimaireRepository;
import com.derteuffel.repository.RegionRepository;
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
@RequestMapping("/primaire")
public class PrimaryController {

    @Autowired
    private PrimaireRepository primaireRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private RegionRepository regionRepository;

    @GetMapping("/form/{regionId}")
    public String form(Model model,@PathVariable Long regionId){
        model.addAttribute("primaire",new Primaire());
        model.addAttribute("region", regionRepository.getOne(regionId));
        return "primaire/form";

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
    public String save(Primaire primaire, @RequestParam("files")MultipartFile[] files,String montant,Long regionId,
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

        primaire.setCouverture("/downloadFile/" + fileName);
        primaire.setPieces(filesPaths);
        primaire.setPrice(Double.parseDouble(montant));
        primaire.setLikes(0);
        primaire.setStatus(false);
        primaire.setRegion(regionRepository.getOne(regionId));

        Primaire primaire1= primaireRepository.save(primaire);
        if (primaire1.getType().contains("colonie")){
            return "redirect:/primaire/primaires/colonie/"+regionRepository.getOne(regionId).getRegionId();
        }else if(primaire1.getType().contains("cours")){
            return "redirect:/primaire/primaires/cours/"+regionRepository.getOne(regionId).getRegionId();
        }else if(primaire1.getType().contains("anglais")){
            return "redirect:/primaire/primaires/langue/"+regionRepository.getOne(regionId).getRegionId();
        }else if(primaire1.getType().contains("jeux")){
            return "redirect:/primaire/primaires/games/"+regionRepository.getOne(regionId).getRegionId();
        }else if(primaire1.getType().contains("bibliotheque")){
            return "redirect:/primaire/primaires/bibliotheque/"+regionRepository.getOne(regionId).getRegionId();
        }else if(primaire1.getType().contains("transport")){
            return "redirect:/primaire/primaires/transport/"+regionRepository.getOne(regionId).getRegionId();
        }else {
            return "redirect:/groupe/groupes";
        }

    }

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    Date date= new Date();

    /*---- here colonies all methods  start ------*/
    @GetMapping("/primaires/colonie/{regionId}")
    public String get9first(Model model, @PathVariable Long regionId, HttpSession session){
        session.setAttribute("regionId", regionId);
         List<Primaire> allPrimaires= primaireRepository.findFirst12ByType("colonie de vacances", Sort.by( "educationId").descending());
        List<Primaire> primaires=new ArrayList<>();
        List<Primaire> events=new ArrayList<>();


        for(Primaire primaire : allPrimaires){
            String primaireDate=sdf.format(primaire.getRealeseDate());
            String date1=sdf.format(date);
            if (primaire.getRegion().getRegionId().equals(regionId)){
                if (!(primaireDate.compareTo(date1)>=0) ){
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
        return "primaire/colonie/colonies";

    }


    @GetMapping("/primaires/colonie/all/{regionId}")
    public String getAllPassEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("colonie de vacances");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/colonie/all";
    }

    @GetMapping("/primaires/colonies/events/{regionId}")
    public String getAllUpCommingEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("colonie de vacances");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/colonie/events";
    }

    @GetMapping("/colonie/{educationId}")
    public String getOnecolonie(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = primaireRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "primaire/colonie/primaire";
    }

    /*---- here colonies methods end ----*/

    /*---- Here courses methods start ----*/

    @GetMapping("/primaires/cours/{regionId}")
    public String cours(Model model, @PathVariable Long regionId){
        List<Primaire> allPrimaires= primaireRepository.findFirst12ByType("cours d'appui", Sort.by( "educationId").descending());
        List<Primaire> primaires=new ArrayList<>();
        List<Primaire> events=new ArrayList<>();
        for(Primaire primaire : allPrimaires){
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
        return "primaire/cours/cours";

    }

    @GetMapping("/primaires/cours/all/{regionId}")
    public String getAllPassCourseEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("cours d'appui");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/cours/all";
    }

    @GetMapping("/primaires/cours/events/{regionId}")
    public String getAllUpCommingCoursesEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("cours d'appui");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/cours/events";
    }

    @GetMapping("/cours/{educationId}")
    public String getOnecourse(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = primaireRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "primaire/cours/primaire";
    }



    /*----- Here courses methods end ----*/
    /*----- Here Languages methods start ----*/


    @GetMapping("/primaires/langue/{regionId}")
    public String anglais(Model model, @PathVariable Long regionId){
        List<Primaire> allPrimaires= primaireRepository.findFirst12ByType("anglais et/ou francais", Sort.by( "educationId").descending());
        List<Primaire> primaires=new ArrayList<>();
        List<Primaire> events=new ArrayList<>();
        for(Primaire primaire : allPrimaires){
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
        return "primaire/langue/langues";

    }

    @GetMapping("/primaires/langue/all/{regionId}")
    public String getAllPassLangueeEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("anglais et/ou francais");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/langue/all";
    }

    @GetMapping("/primaires/langue/events/{regionId}")
    public String getAllUpCommingLangueEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("anglais et/ou francais");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/langue/events";
    }

    @GetMapping("/langue/{educationId}")
    public String getOnelangue(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = primaireRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "primaire/langue/primaire";
    }

/*------ Here language methods end -----*/



/*------ Here Games methods start -----*/

    @GetMapping("/primaires/games/{regionId}")
    public String jeux(Model model, @PathVariable Long regionId){
        List<Primaire> allPrimaires= primaireRepository.findFirst12ByType("jeux educatif", Sort.by( "educationId").descending());
        List<Primaire> primaires=new ArrayList<>();
        List<Primaire> events=new ArrayList<>();
        for(Primaire primaire : allPrimaires){
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
        return "primaire/game/games";

    }


    @GetMapping("/primaires/games/all/{regionId}")
    public String getAllPassGameEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("jeux educatif");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/game/all";
    }

    @GetMapping("/primaires/games/events/{regionId}")
    public String getAllUpCommingGameEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("jeux educatif");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/game/events";
    }

    @GetMapping("/games/{educationId}")
    public String getOneGame(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = primaireRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "primaire/game/primaire";
    }

    /*------ Here Games methods end -----*/


    /*------ Here Bibliotheque methods start -----*/

    @GetMapping("/primaires/bibliotheque/{regionId}")
    public String bibliotheque(Model model, @PathVariable Long regionId){
        List<Primaire> allPrimaires= primaireRepository.findFirst12ByType("bibliotheque en ligne", Sort.by( "educationId").descending());
        List<Primaire> primaires=new ArrayList<>();
        List<Primaire> events=new ArrayList<>();
        for(Primaire primaire : allPrimaires){
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
        return "primaire/bibliotheque/bibliotheques";

    }

    @GetMapping("/primaires/bibliotheque/all/{regionId}")
    public String getAllPassBibliothequeEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("bibliotheque en ligne");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/bibliotheque/all";
    }

    @GetMapping("/primaires/bibliotheque/events/{regionId}")
    public String getAllUpCommingBibliothequeEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("bibliotheque en ligne");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/bibliotheque/events";
    }

    @GetMapping("/bibliotheque/{educationId}")
    public String getOneBibliotheque(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = primaireRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "primaire/bibliotheque/primaire";
    }


    /*----- Here Bibliotheque methods end -----*/


    /*----- Here Transports methods start -----*/

    @GetMapping("/primaires/transport/{regionId}")
    public String transport(Model model, @PathVariable Long regionId){
        List<Primaire> allPrimaires= primaireRepository.findFirst12ByType("transport securise", Sort.by( "educationId").descending());
        List<Primaire> primaires=new ArrayList<>();
        List<Primaire> events=new ArrayList<>();
        for(Primaire primaire : allPrimaires){
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
        return "primaire/transport/transports";

    }

    @GetMapping("/primaires/transport/all/{regionId}")
    public String getAllPassTransportEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("transport securise");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/transport/all";
    }

    @GetMapping("/primaires/transport/events/{regionId}")
    public String getAllUpCommingTransportEvent(Model model, @PathVariable Long regionId){

        List<Primaire> lists= primaireRepository.findAllByTypeOrderByEducationIdDesc("transport securise");
        List<Primaire> primaires=new ArrayList<>();
        Region region= regionRepository.getOne(regionId);
        for(Primaire primaire : lists){
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
        return "primaire/transport/events";
    }

    @GetMapping("/transport/{educationId}")
    public String getOneTransport(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = primaireRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "primaire/transport/primaire";
    }


    /*------ Here Transport methods end ----*/


    @GetMapping("/primaire/{educationId}")
    public String getOne(Model model, @PathVariable Long educationId, HttpSession session){

        Long regionId=(Long)session.getAttribute("regionId");
        Region region=regionRepository.getOne(regionId);
        model.addAttribute("region",region);
        Education primaire = primaireRepository.getOne(educationId);
        model.addAttribute("primaire",primaire);
        return "primaire/primaire";
    }

    @GetMapping("/like/{educationId}")
    public String likes(@PathVariable Long educationId){
        Education primaire= primaireRepository.getOne(educationId);
        int n=primaire.getLikes();
        n++;
        primaire.setLikes(n);
        System.out.println(n);
        primaireRepository.save(primaire);
       return "redirect:/primaire/primaire/"+primaire.getEducationId();
    }

    @GetMapping("/publish/{educationId}")
    public String publish(@PathVariable Long educationId){
        Education primaire= primaireRepository.getOne(educationId);
        if (primaire.getStatus()== true){
            primaire.setStatus(false);
        }else {
            primaire.setStatus(true);
        }
        primaireRepository.save(primaire);

        return "redirect:/primaire/primaire/"+primaire.getEducationId();
    }

}
