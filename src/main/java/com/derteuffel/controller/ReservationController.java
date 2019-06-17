package com.derteuffel.controller;

import com.derteuffel.data.Colonie;
import com.derteuffel.data.Reservation;
import com.derteuffel.data.User;
import com.derteuffel.repository.ColonieRepository;
import com.derteuffel.repository.ReservationRepository;
import com.derteuffel.service.MailService;
import com.derteuffel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * Created by derteuffel on 16/06/2019.
 */
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ColonieRepository colonieRepository;
    public List<Integer> listNumbers= Arrays.asList(1,2,3,4,5,6,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
    public List<String> listHours=Arrays.asList("00:00",
            "01:00",
            "02:00",
            "03:00",
            "04:00",
            "04:00",
            "05:00",
            "06:00",
            "07:00",
            "08:00",
            "09:00",
            "10:00",
            "11:00",
            "12:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00",
            "18:00",
            "19:00",
            "20:00",
            "21:00",
            "22:00",
            "23:00"
    );

    @GetMapping("/form/{colonieId}")
    public String form(Model model, @PathVariable Long colonieId){
        Colonie colonie=colonieRepository.getOne(colonieId);
        model.addAttribute("listNumbers", listNumbers);
        model.addAttribute("listHours", listHours);
        model.addAttribute("colonie",colonie);
        model.addAttribute("reservation",new Reservation());

        return "these_module/colonie/colonie_form";
    }

    @PostMapping("/save")
    public String save(Reservation reservation, String pays,Long colonieId, String region, String site, HttpSession session, String activite, String prix, String type, String saison){
        reservation.setPays(pays);
        reservation.setRegion(region);
        reservation.setSite(site);
        reservation.setActivite(activite);
        reservation.setPrix(reservation.getNombreEnfants()*Double.parseDouble(prix));
        reservation.setType(type);
        reservation.setSaison(saison);
        reservation.setStatus(false);
        reservation.setColonie(colonieRepository.getOne(colonieId));
        reservationRepository.save(reservation);

        MailService backMessage = new MailService();
        backMessage.sendSimpleMessage(
                "solutioneducationafrique@gmail.com",
                "Notification d'une reservation faite sur une activite dans le module de colonie de vacances, Titre de l'activite : "+ reservation.getActivite(),
                reservation.getNom() + " vous notifi celon le contenue suivant :  Bien vouloir manager la reservation faite"
        );

        return "redirect:/visitor/colonie/"+colonieRepository.getOne(colonieId).getColonieId();
    }
}
