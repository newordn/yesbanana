package com.derteuffel.service;

import com.derteuffel.data.Event;
import com.derteuffel.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return eventRepository.findAll();
    }

    public List<Event> findForSearch(String title, String type, String description){
        return eventRepository.findAllForSearch(title,type,description);
    }

    public void delete(Long eventId){
        eventRepository.deleteById(eventId);
    }
}
