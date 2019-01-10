package com.derteuffel.restController;

import com.derteuffel.data.User;
import com.derteuffel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by derteuffel on 07/01/2019.
 */
@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Collection<User> list(){
        return userRepository.findAllByOrderByUserIdDesc();
    }
}
