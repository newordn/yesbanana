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



    public Page<These> findAllPaginated(Pageable pageable) {

        List<These> theses=theseRepository.findAllByOrderByTheseIdDesc();
        System.out.println(theses);
        int pageSize=pageable.getPageSize();
        int currentPage= pageable.getPageNumber();
        int startItem= pageSize*currentPage;

        List<These> theses1;

        if (theses.size()<startItem){
            theses1= Collections.emptyList();
        }else {
            int toIndex=Math.min(startItem+pageSize, theses.size());
            theses1=theses.subList(startItem,toIndex);
        }

        Page<These> thesePage
                =new PageImpl<These>(theses1, PageRequest.of(currentPage,pageSize), theses.size());

        return thesePage;
    }

    public Page<These> findAllByUser(Pageable pageable) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName());
        Long userId = user.getUserId();
        System.out.println(userId);
        List<These> theses=theseRepository.findByUserOrderByTheseIdDesc(userId);
        System.out.println(theses);
        int pageSize=pageable.getPageSize();
        int currentPage= pageable.getPageNumber();
        int startItem= pageSize*currentPage;

        List<These> theses2;

        if (theses.size()<startItem){
            theses2= Collections.emptyList();
        }else {
            int toIndex=Math.min(startItem+pageSize, theses.size());
            theses2=theses.subList(startItem,toIndex);
        }

        Page<These> thesePage
                =new PageImpl<These>(theses2, PageRequest.of(currentPage,pageSize), theses.size());

        return thesePage;
    }

    public Page<These> findAllByGroupe(Pageable pageable, Long groupeId) {
        Groupe groupe=groupeRepository.getOne(groupeId);
        List<These> theses=theseRepository.findByGroupeOrderByTheseIdDesc(groupe.getGroupeId());
        System.out.println(theses);
        int pageSize=pageable.getPageSize();
        int currentPage= pageable.getPageNumber();
        int startItem= pageSize*currentPage;

        List<These> theses2;

        if (theses.size()<startItem){
            theses2= Collections.emptyList();
        }else {
            int toIndex=Math.min(startItem+pageSize, theses.size());
            theses2=theses.subList(startItem,toIndex);
        }

        Page<These> thesePage
                =new PageImpl<These>(theses2, PageRequest.of(currentPage,pageSize), theses.size());

        return thesePage;
    }

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
