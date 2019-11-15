package com.derteuffel.service;

import com.derteuffel.data.Publicite;
import com.derteuffel.repository.PubliciteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PubliciteService {

    @Autowired
    private PubliciteRepository publiciteRepository;

    public void save(Publicite publicite){
        publiciteRepository.save(publicite);
    }

    public Publicite get(Long publiciteId){
        return publiciteRepository.getOne(publiciteId);
    }

    public void  delete(Long publiciteId){
        publiciteRepository.deleteById(publiciteId);
    }

    public void update(Publicite publicite){
        publiciteRepository.save(publicite);
    }

    public List<Publicite> findAll(){
        return publiciteRepository.findAll(Sort.by(Sort.Direction.DESC,"publiciteId"));
    }
}
