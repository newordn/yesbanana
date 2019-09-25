package com.derteuffel.service;


import com.derteuffel.data.Entrepreneuriat;
import com.derteuffel.repository.EntrepreneuriatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntrepreneuriatService {

    @Autowired
    private EntrepreneuriatRepository entrepreneuriatRepository;


    public List<Entrepreneuriat> findAll() {
        return entrepreneuriatRepository.findAll();
    }

    public Entrepreneuriat getOne(Long entrepreneuriatId) {
        return entrepreneuriatRepository.getOne(entrepreneuriatId);
    }

    public Entrepreneuriat save(Entrepreneuriat entrepreneuriat) {
        return entrepreneuriatRepository.save(entrepreneuriat);
    }

    public void deleteById(Long entrepreneuriatId) {
        entrepreneuriatRepository.deleteById(entrepreneuriatId);
    }


    public List<Entrepreneuriat> findByStatesAndStatus(Boolean states, Boolean status){
        return entrepreneuriatRepository.findAllByStatesAndStatus(states,status);
    }

    public void  activation(Long entrepreneuriatId){
        Entrepreneuriat entrepreneuriat=entrepreneuriatRepository.getOne(entrepreneuriatId);
        if (entrepreneuriat.getStatus()== false){
            entrepreneuriat.setStatus(true);
        }else {
            entrepreneuriat.setStatus(false);
        }
        entrepreneuriatRepository.save(entrepreneuriat);
    }

    public void  edit(Entrepreneuriat entrepreneuriat, ArrayList<String> files, String prix){

        entrepreneuriat.setPieces(files);
        entrepreneuriat.setPrice(Double.parseDouble(prix));

        entrepreneuriatRepository.save(entrepreneuriat);
    }
}
