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
        return eventRepository.findAllByType(type, Sort.by(Sort.Direction.DESC,"eventId"));
    }
}
