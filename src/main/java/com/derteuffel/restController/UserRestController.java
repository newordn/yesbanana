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
            map.put("status","true");
            return map;
        }else {
            map.put("status","false");
            return map;
        }
    }
    @GetMapping("/chiefs")
    public List<User> chiefs() {
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByCategory("Chef des travaux");
        List<User> chiefs= new ArrayList<>();
        for (User user : users){
            for (int i=0; i<users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    chiefs.add(user);
                }
            }
        }
        System.out.println(chiefs);
        return chiefs;
    }

    @GetMapping("/assistants")
    public List<User> assistants() {
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByCategory("Assistant");
        List<User> assistants= new ArrayList<>();
        for (User user : users){
            for (int i=0; i<users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    assistants.add(user);
                }
            }
        }
        System.out.println(assistants);
        return assistants;
    }
    @GetMapping("/professeurs")
    public List<User> professors() {
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByCategory("Professeur");
        List<User> professeurs= new ArrayList<>();
        for (User user : users){
            for (int i=0; i<users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    professeurs.add(user);
                }
            }
        }
        System.out.println(professeurs);
        return professeurs;
    }
    @GetMapping("/primaries")
    public List<User> primaries() {
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByCategory("Primaire");
        List<User> primaries= new ArrayList<>();
        for (User user : users){
            for (int i=0; i<users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    primaries.add(user);
                }
            }
        }
        System.out.println(primaries);
        return primaries;
    }
    @GetMapping("/secondaries")
    public List<User> secondary() {
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByCategory("Secondaire");
        List<User> secondaries= new ArrayList<>();
        for (User user : users){
            for (int i=0; i<users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    secondaries.add(user);
                }
            }
        }
        System.out.println(secondaries);
        return secondaries;
    }
    @GetMapping("/student")
    public List<User> students() {
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByCategory("Etudiant");
        List<User> students= new ArrayList<>();
        for (User user : users){
            for (int i=0; i<users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    students.add(user);
                }
            }
        }
        System.out.println(students);
        return students;
    }

    @GetMapping("/master")
    public List<User> master() {
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByDiplomOrderByUserIdDesc("Master1&2");
        List<User> userList= new ArrayList<>();
        for (User user: users){
            for (int i=0; i< users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    userList.add(user);
                }
            }
        }
        return userList;
    }

    @GetMapping("/doctorat")
    public List<User> doctorat() {
        List<User> users= userRepository.findAllByActiveOrderByUserIdDesc(null);
        List<User> users1= userRepository.findAllByDiplomOrderByUserIdDesc("Phd/Doctorat");
        List<User> userList= new ArrayList<>();
        for (User user: users){
            for (int i=0; i< users1.size(); i++){
                if (user.getUserId().equals(users1.get(i).getUserId())){
                    userList.add(user);
                }
            }
        }
        return userList;
    }

}
