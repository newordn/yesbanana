package com.derteuffel.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by derteuffel on 16/06/2019.
 */
@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue
    private Long reservationId;
    private String nom;
    private String telephone;
    private String pays;
    private String region;
    private String email;
    private String site;
    private String activite;
    private Double prix;
    private String type;
    private int nombreEnfants;
    private String saison;
    private String heureDebut;
    private String heureFin;
    private int nombreJours;
    private Boolean status;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;

    @ManyToOne
    private Colonie colonie;

    public Reservation() {
    }

    public Reservation(String nom, String telephone, String pays, String region, String email, String site, String activite, Double prix, String type, int nombreEnfants,
                       String saison, String heureDebut, String heureFin, int nombreJours, Boolean status, Date dateDebut, Colonie colonie) {
        this.nom = nom;
        this.telephone = telephone;
        this.pays = pays;
        this.region = region;
        this.email = email;
        this.site = site;
        this.activite = activite;
        this.prix = prix;
        this.type = type;
        this.nombreEnfants = nombreEnfants;
        this.saison = saison;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.nombreJours = nombreJours;
        this.status = status;
        this.dateDebut = dateDebut;
        this.colonie = colonie;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getNombreJours() {
        return nombreJours;
    }

    public void setNombreJours(int nombreJours) {
        this.nombreJours = nombreJours;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNombreEnfants() {
        return nombreEnfants;
    }

    public void setNombreEnfants(int nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }

    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @JsonIgnore
    public Colonie getColonie() {
        return colonie;
    }

    public void setColonie(Colonie colonie) {
        this.colonie = colonie;
    }
}
