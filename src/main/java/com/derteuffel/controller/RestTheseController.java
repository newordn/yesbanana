package com.derteuffel.controller;

import com.derteuffel.data.Groupe;
import com.derteuffel.data.These;
import com.derteuffel.data.User;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.TheseRepository;
import com.derteuffel.repository.UserRepository;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RestTheseController{
    @Autowired
    private TheseRepository theseRepository;

    @Autowired
    private UserService userRepository;

    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*@Autowired
    private StrongPasswordEncryptor strongPasswordEncryptor;*/

    @GetMapping(value = "/these/getOne/{theseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public These findById(@PathVariable Long theseId) {

        Optional<These> theseOptional=theseRepository.findById(theseId);
        System.out.println(theseOptional.get().getLevel());
        return theseOptional.get();


    }

    @GetMapping("/user/get/{userId}")
    public User findOne(@PathVariable Long userId){
       return userRepository.getById(userId);
    }

    @GetMapping("/groupes")
public List<Groupe> findAll(){
    return  groupeRepository.findAll();

}
    /*@GetMapping("/users")
    public List<User> findAllu() {
        return userRepository.listAll();
    }*/

    @PostMapping("/groupe/groupe/save")
    public Groupe save(Groupe groupe){
    return     groupeRepository.save(groupe);
    }


    @GetMapping("/{password}")
    public boolean update( @PathVariable String password){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByName(auth.getName());
        if (bCryptPasswordEncoder.matches(password, user.getPassword())){
            return true;

        }else {
            return false;
        }

    }
}
