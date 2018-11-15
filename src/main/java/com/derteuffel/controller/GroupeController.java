


package com.derteuffel.controller;

import com.derteuffel.data.*;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.TheseRepository;
import com.derteuffel.repository.UserRepository;
import com.derteuffel.service.TheseService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by derteuffel on 23/10/2018.
 */
@Controller
@RequestMapping("/groupe")
public class GroupeController {
    
    // attributes
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TheseRepository theseRepository;
    @Autowired
    private TheseService theseService;

    private static int currentPage=1;
    private static int pageSize=6;

    
    // for listing all the crews
    @GetMapping("/groupes")
    public String findAllByParentOrderByGroupeIdDesc(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        session.setAttribute("userId",user.getUserId());
        session.setAttribute("avatar",user.getImg());
        session.setAttribute("name", user.getName());
        session.setAttribute("userRole",user.getRole().getRole());
        List<User> users = userRepository.findAllByRole("admin");
        users.addAll(userRepository.findAllByRole("root"));
        System.out.println(users);
        if (user.getRole().getRole().equals("root")){
            model.addAttribute("crews", groupeRepository.findAll());
            System.out.println(groupeRepository.findAll());
            model.addAttribute("groupe", new Groupe());
            model.addAttribute("users",users);
        }else {


            model.addAttribute("crews", groupeRepository.findByUsers_UserId(user.getUserId()));
            model.addAttribute("groupe", new Groupe());
        }
        return "crew/crews";
    }

    // for adding a user into one crew
    @GetMapping("/add1/{groupeId}/{userId}")
    public String add( @PathVariable Long groupeId, @PathVariable Long userId)
    {
        Groupe groupe = groupeRepository.getOne(groupeId);
        User user = userService.getById(userId);
        groupe.setUsers(user);
        groupeRepository.save(groupe);
        System.out.println("ddf");
        return "redirect:/groupe/groupes";
    }
    
    // for saving a crew
    @PostMapping("/save")
    public String save(Groupe groupe){
        Groupe groupe1 = groupeRepository.saveAndFlush(groupe);
        Long userId = Long.parseLong(groupe.getGroupChief());
        Long groupeId = groupe1.getGroupeId();
        return "redirect:/groupe/add1/"+ groupeId + "/"+ userId ;
    }

    // just to use it in the model for retrieving users ids
       public class UsersGroupe {
        private String usersIds;

        public String getUsersIds() {
            return usersIds;
        }

        public void setUsersIds(String usersIds) {
            this.usersIds = usersIds;
        }

    }
    // insight for a crew
       @GetMapping("/groupe/stats")
    public String stats(Model model, HttpSession session)
    {
        Long groupeId = (Long) session.getAttribute("groupeId");
        List<User> users = userRepository.findByGroupes_GroupeId(groupeId);
        List<These> theses = theseRepository.findByGroupeOrderByTheseIdDesc(groupeId);
        model.addAttribute("usersSize",users.size());
        model.addAttribute("thesesSize",theses.size());
        return "crew/stats";
    }
    
    // all the theses for a particular user
    @GetMapping("/groupe/all/user/these")
    public String findByUser(Model model, @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size")Optional<Integer>size, HttpSession session){
        page.ifPresent(p->currentPage=p);
        size.ifPresent(s->pageSize=s);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        Page<These> thesePage=theseService.findAllByUser(PageRequest.of(currentPage-1, pageSize));
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
        session.setAttribute("avatar", user.getImg());
        session.setAttribute("name", user.getName());
        System.out.println();
        return "crew/theses";
    }

    // all the theses for a crew
    @GetMapping("/groupe/all/these")
    public String findByGroupe(Model model, @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size")Optional<Integer>size, HttpSession session){
        page.ifPresent(p->currentPage=p);
        size.ifPresent(s->pageSize=s);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        Page<These> thesePage=theseService.findAllByGroupe(PageRequest.of(currentPage-1, pageSize),(Long)session.getAttribute("groupeId"));
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
        session.setAttribute("avatar", user.getImg());
        session.setAttribute("name", user.getName());
        System.out.println();
        return "crew/theses";
    }

    // updating a crew
    @GetMapping("/update/{groupeId}")
    public String update(Model model,@PathVariable Long groupeId){
        Groupe groupe=groupeRepository.getOne(groupeId);
        model.addAttribute("groupe", groupe);
        model.addAttribute("users", userRepository.findAllByRole("admin"));

        return "crew/crew";
    }

    @PostMapping("/add/users")
    public  String addGroupUser(UsersGroupe usersGroupe, HttpSession session){
        Long groupeId = (Long)session.getAttribute("groupeId");
        Groupe groupe= groupeRepository.getOne(groupeId);
        String[] usersIds = usersGroupe.getUsersIds().split(",");
        ArrayList<Long> usersIdsLong = new ArrayList<>();

        for(int i=0;i<usersIds.length;i++)
        {
            if(!usersIds[i].isEmpty())
            usersIdsLong.add(Long.parseLong(usersIds[i]));
        }
        System.out.println(usersIdsLong);
        for ( Long id: usersIdsLong)
        {
            groupe.setUsers(userService.getById(id));
        }
        groupeRepository.save(groupe);
        return "redirect:/groupe/groupe/users/"+ groupeId;

    }

    @GetMapping("/groupe/users/{groupeId}")
    public String groupeUser(Model model, @PathVariable Long groupeId){
        List<User> users1 = userRepository.findAllByRole("user");
        Groupe groupe = groupeRepository.getOne(groupeId);
        model.addAttribute("users1",users1);
        model.addAttribute("usersGroupe", new UsersGroupe());
        model.addAttribute("groupeName",groupe.getGroupeName());
        List<User> users = userRepository.findByGroupes_GroupeId(groupeId);
        model.addAttribute("users",users);
        return "crew/users";

    }



    @GetMapping("/groupe/{groupeId}")
    public String findById(@PathVariable Long groupeId,HttpSession session, Model model) {
        session.setAttribute("groupeId", groupeId);
        Long userId=(Long)session.getAttribute("userId");
        Groupe groupe = groupeRepository.getOne(groupeId);
        User user=userService.getById(userId);
        if (!user.getRole().getRole().equals("user")){
            model.addAttribute("userId", userId);
            model.addAttribute("these",new These());
            model.addAttribute("theses", theseRepository.findByGroupeOrderByTheseIdDesc(groupeId));
            model.addAttribute("groupeName",groupe.getGroupeName());
            return "redirect:/groupe/groupe/all/these";
        }else {
            model.addAttribute("userId", userId);
            model.addAttribute("these",new These());
            model.addAttribute("theses", theseRepository.findByUserOrderByTheseIdDesc(user.getUserId()));
            model.addAttribute("groupeName",groupe.getGroupeName());
            return "redirect:/groupe/groupe/all/user/these";
        }



    }

    @DeleteMapping("/delete/{groupeId}")
    public String deleteById(@PathVariable Long groupeId) {
        groupeRepository.deleteById(groupeId);
        return "redirect:/groupe/groupes";
    }

    @GetMapping("/")
    public String findAll(){
        return "crew/crews";
    }

    @GetMapping("/encadrement/chef/{groupeId}")
    public String findAllByGroupeAndCategory1(Model model,@PathVariable Long groupeId){

        List<User> users= userRepository.findByGroupes_GroupeId(groupeId);
         List<User> users1= new ArrayList<>();
        for (User user:users){

            if (user.getCategory().equals("Chef de travaux")){
                users1.add(user);
            }
        }

        model.addAttribute("users", users1);
        return "crew/encadrement";
    }

    @GetMapping("/encadrement/assistant/{groupeId}")
    public String findAllByGroupeAndCategory2(Model model,@PathVariable Long groupeId){

        List<User> users_assistant= userRepository.findByGroupes_GroupeId(groupeId);
        List<User> users_list= new ArrayList<>();
        for (User user1:users_assistant){

            if (user1.getCategory().equals("Assistant")){
                users_list.add(user1);
            }
        }

        model.addAttribute("users1", users_list);
      return "crew/encadrement";
    }

    @GetMapping("/encadrement/encadreur/{groupeId}")
    public String findAllByGroupeAndCategory3(Model model,@PathVariable Long groupeId){

        List<User> users_encadreur= userRepository.findByGroupes_GroupeId(groupeId);
        List<User> users_2= new ArrayList<>();
        for (User user:users_encadreur){

            if (user.getCategory().equals("Encadreur")){
                users_2.add(user);
            }
        }

        model.addAttribute("users2", users_2);
        return "crew/encadrement";
    }

    @GetMapping("/encadrement/etudiant/{groupeId}")
    public String findAllByGroupeAndCategory4(Model model,@PathVariable Long groupeId){

        List<User> users_etudiant= userRepository.findByGroupes_GroupeId(groupeId);
        List<User> users_3= new ArrayList<>();
        for (User user:users_etudiant){

            if (user.getCategory().equals("Etudiant")){
                users_3.add(user);
            }
        }

        model.addAttribute("users3", users_3);
        return "crew/encadrement";
    }

    @GetMapping("/training/{groupeId}")
    public String encadrement(Model model, @PathVariable Long groupeId){
        Groupe groupe=groupeRepository.getOne(groupeId);
        model.addAttribute("groupe",groupe);
        return "crew/encadrement";
    }
}
