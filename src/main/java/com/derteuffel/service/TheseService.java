package com.derteuffel.service;

import com.derteuffel.data.Groupe;
import com.derteuffel.data.These;
import com.derteuffel.data.User;
import com.derteuffel.repository.GroupeRepository;
import com.derteuffel.repository.TheseRepository;
import com.derteuffel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 15/10/2018.
 */
@Service
public class TheseService {
    @Autowired
    private TheseRepository theseRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupeRepository groupeRepository;



   /* public These save(These these, Long userId){


        User user=userRepository.getOne(userId);
        these.setUser(user);
        return theseRepository.save(these);

    }*/

    public These findById(Long theseId){
        Optional<These> optional= theseRepository.findById(theseId);
        return optional.get();
    }

    public void delete(Long theseId){
        theseRepository.deleteById(theseId);
    }


}
