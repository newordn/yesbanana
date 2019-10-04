package com.derteuffel.restController;

import com.derteuffel.data.User;
import com.derteuffel.repository.UserRepository;
import com.derteuffel.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by derteuffel on 07/01/2019.
 */
@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/users")
    public Collection<User> list(){
        return userRepository.findAllByOrderByUserIdDesc();
    }
    private String validate_url="yesbanana.org/validate/";

    //users visitor management function
    @PostMapping(value = "/mobile/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public Map<String, String> restRegistration(@RequestBody Map<String,String> object){
        System.out.println("Registration");
       Map map=new HashMap<String,String>();

        User user= userRepository.findByName(object.get("name"));
        if (user!= null){
            map.put("status","false");
            return map;
        }

        User user1= userRepository.findByEmail(object.get("email"));
        if (user1!= null){
            map.put("status","false");
            return map;
        }
        User user2=new User();
        user2.setName(object.get("name"));
        user2.setEmail(object.get("email"));
        if (!object.get("password").equals(object.get("confirmPassword"))){
            map.put("status","false");
            return map;
        }else {
            user2.setPassword(bCryptPasswordEncoder.encode(object.get("password")));
        }
        user2.setCountry(object.get("country"));
        user2.setRegion(object.get("region"));
        user2.setNumber(object.get("phone"));
        user2.setPar_mobile(false);
        user2.setStatus(true);
        userRepository.save(user2);
        MailService mailService = new MailService();
        mailService.sendSimpleMessage(
                "solutioneducationafrique@gmail.com",
                "YesBanana: Notification Inscription d'un utilisateur a partir de l'application mobile",
                "L'utilisateur " + user2.getName() + " dont l'email est " +
                        user2.getEmail()+ "  Vient de s'inscrire " +
                        "sur la plateforme YesBanana.");

        map.put("status","true");
        return map;

    }

    @PostMapping(value = "/mobile/login",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> restLogin( @RequestBody Map<String,String> object){
        System.out.println("i got it");
        System.out.println(object);
        Map map = new HashMap<String,String>();
        if(object.get("login")==null || object.get("password") == null || object.get("login").isEmpty() || object.get("password").isEmpty())
        {
            System.out.println("here is ");
            map.put("status", "false");
            return map;
        }
        String mot_passe = object.get("password");
        System.out.println(mot_passe);
        User user= userRepository.findByName(object.get("login"));
        System.out.println(user.getName());
        System.out.println(user.getPassword());

        if (user.getName().equals(object.get("login")) && bCryptPasswordEncoder.matches(mot_passe,user.getPassword())){
            user.setPar_mobile(true);
            userRepository.save(user);
            map.put("status","true");
            return map;
        }else {
            map.put("status","false");
            return map;
        }
    }


}
