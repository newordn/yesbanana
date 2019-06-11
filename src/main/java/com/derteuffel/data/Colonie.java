package com.derteuffel.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by derteuffel on 06/06/2019.
 */
@Entity
public class Colonie implements Serializable {

    @Id
    @GeneratedValue
    private Long colonieId;
    private String title;
    private String activite;
    private String category;
    private Double price;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;
    private String type;
    private String site;
    private String pays;
    private String region;
    private Boolean active;
    private Boolean status;
    private ArrayList<String> fichier;

    public Colonie() {
    }

    public Colonie(String title, String activite, String category, Double price, Date dateDebut,
                   Date dateFin, String type, String site, String pays, String region, Boolean active, Boolean status, ArrayList<String> fichier) {
        this.title = title;
        this.activite = activite;
        this.category = category;
        this.price = price;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
        this.site = site;
        this.pays = pays;
        this.region = region;
        this.active = active;
        this.status = status;
        this.fichier = fichier;
    }

    public Long getColonieId() {
        return colonieId;
    }

    public void setColonieId(Long colonieId) {
        this.colonieId = colonieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<String> getFichier() {
        return fichier;
    }

    public void setFichier(ArrayList<String> fichier) {
        this.fichier = fichier;
    }
}
