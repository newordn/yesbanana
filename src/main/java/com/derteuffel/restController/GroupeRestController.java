package com.derteuffel.restController;

import com.derteuffel.data.Groupe;
import com.derteuffel.data.User;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.UserRepository;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by derteuffel on 09/01/2019.
 */
@RestController
public class GroupeRestController {

    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/user/groupe")
    public List<User> findByGroupes_GroupeId(HttpSession session) {
        return userRepository.findByGroupes_GroupeId((Long)session.getAttribute("groupeId"));
    }

    @GetMapping("/chef")
    public List<User> findAllByGroupeAndCategory1( HttpSession session){

        Groupe groupe=groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        List<User> users= userRepository.findByGroupes_GroupeId(groupe.getGroupeId());
        System.out.println(users);
        List<User> userList=userService.findByCategory("Chef des travaux");
        System.out.println(userList);
        List<User> chiefs= new ArrayList<>();
        for (int i=0;i<users.size();i++){
            for (User user: userList){
                if (user.getUserId().equals(users.get(i).getUserId())){
                    chiefs.add(user);
                }
            }
        }
        System.out.println(chiefs);


        return chiefs;
    }

    @GetMapping("/assistant")
    public List<User> assistant(HttpSession session){

        Groupe groupe=groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        List<User> users_assistant= userRepository.findByGroupes_GroupeId(groupe.getGroupeId());
        List<User> userList=userService.findByCategory("Assistant");
        List<User> assistant= new ArrayList<>();
        for (int i=0;i<users_assistant.size();i++){
            for (User user: userList){
                if (user.getUserId().equals(users_assistant.get(i).getUserId())){
                    assistant.add(user);
                }
            }
        }
        return assistant;
    }

    @GetMapping("/encadreur")
    public List<User> encadreur(HttpSession session){

        Groupe groupe=groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        List<User> users_assistant= userRepository.findByGroupes_GroupeId(groupe.getGroupeId());
        List<User> userList=userService.findByCategory("Encadreur");
        List<User> encadreur= new ArrayList<>();
        for (int i=0;i<users_assistant.size();i++){
            for (User user: userList){
                if (user.getUserId().equals(users_assistant.get(i).getUserId())){
                    encadreur.add(user);
                }
            }
        }
        return encadreur;
    }

    @GetMapping("/etudiant")
    public List<User> etudiant(HttpSession session){

        Groupe groupe=groupeRepository.getOne((Long)session.getAttribute("groupeId"));
        List<User> users_assistant= userRepository.findByGroupes_GroupeId(groupe.getGroupeId());
        List<User> userList=userService.findByCategory("Etudiant");
        List<User> etudiant= new ArrayList<>();
        for (int i=0;i<users_assistant.size();i++){
            for (User user: userList){
                if (user.getUserId().equals(users_assistant.get(i).getUserId())){
                    etudiant.add(user);
                }
            }
        }
        return etudiant;
    }
}
