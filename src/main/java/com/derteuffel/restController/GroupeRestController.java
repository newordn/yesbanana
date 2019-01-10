package com.derteuffel.restController;

import com.derteuffel.data.User;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/user/groupe")
    public List<User> findByGroupes_GroupeId(HttpSession session) {
        return userRepository.findByGroupes_GroupeId((Long)session.getAttribute("groupeId"));
    }
}
