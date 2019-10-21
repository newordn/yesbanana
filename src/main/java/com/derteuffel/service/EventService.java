package com.derteuffel.service;

import com.derteuffel.data.Event;
import com.derteuffel.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public void save(Event event){
        eventRepository.save(event);
    }

    public Event getOne(Long eventId){
        return eventRepository.getOne(eventId);
    }

    public List<Event> findAll(){
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC,"eventId"));
    }

    public List<Event> findForSearch(String title, String type, String description){
        return eventRepository.findAllForSearch(title,type,description);
    }

    public void delete(Long eventId){
        eventRepository.deleteById(eventId);
    }

    public List<Event> findByType(String type){
        return eventRepository.findAllByTypeAndStatus(type,true, Sort.by(Sort.Direction.DESC,"eventId"));
    }

    public  List<Event> findByCategory(String category){
        return eventRepository.findByCategoryAndStatus(category, true, Sort.by(Sort.Direction.DESC,"eventId"));
    }


    public List<Event> findFirst6( String type, String category){
        return eventRepository.findFirst6ByTypeAndCategoryAndStatus(type, category, true,Sort.by(Sort.Direction.DESC, "eventId"));
    }

    public List<Event> findFirst3( String type, String category){
        return eventRepository.findFirst3ByTypeAndCategoryAndStatus(type, category, true,Sort.by(Sort.Direction.DESC, "eventId"));
    }

    public List<Event> findFirst9(String category, String type){
        return  eventRepository.findFirst9ByTypeAndCategoryAndStatus(category, type, true, Sort.by(Sort.Direction.DESC,"eventId"));
    }

    public List<Event> findByTypeAndCategory(String type, String category){
        return eventRepository.findByTypeAndCategoryAndStatus(type,category,true,Sort.by(Sort.Direction.DESC,"eventId"));
    }

}
