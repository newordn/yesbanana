package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

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
    private Boolean status;

    @ManyToOne
    private Colonie colonie;

    public Reservation() {
    }

    public Reservation(String nom, String telephone, String pays, String region, String email, String site, String activite,
                       Double prix, String type, int nombreEnfants, String saison, String heureDebut, String heureFin,
                       Boolean status, Colonie colonie) {
        this.nom = nom;
        this.telephone = telephone;
        this.pays = pays;
        this.region = region;
        this.site = site;
        this.email=email;
        this.activite = activite;
        this.prix = prix;
        this.type = type;
        this.nombreEnfants = nombreEnfants;
        this.saison = saison;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.status = status;
        this.colonie = colonie;
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

    public Colonie getColonie() {
        return colonie;
    }

    public void setColonie(Colonie colonie) {
        this.colonie = colonie;
    }
}
