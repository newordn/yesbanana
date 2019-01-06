package com.derteuffel.restController;

import com.derteuffel.data.Event;
import com.derteuffel.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by derteuffel on 06/01/2019.
 */
@RestController
public class EventRestController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/event/events")
    public List<Event> events(){
        return  eventRepository.findAllByType("administration et finance");
    }
}
