package com.derteuffel.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by derteuffel on 30/01/2019.
 */
@Entity
public class Bibliography implements Serializable{
    @Id
    @GeneratedValue
    private Long bibliographyId;
    private String auteur;
    private String title;
    private String motCle;
    private String description;
    private String link;
    private String couverture;
    private Boolean disponibility;
    private Double price;
    private Double pagePrice;
    private String editeur;
    private String lieu_edition;
    private String annee_edition;
    private String fichier;
    private String pageNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private These these;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Bibliography() {
    }

    public Bibliography(String auteur, String fichier, String couverture, String pageNumber, String title,String motCle, String description, These these, String link,Boolean disponibility,
                        Double pagePrice, Double price, String editeur, String lieu_edition, String annee_edition) {
        this.auteur = auteur;
        this.title = title;
        this.price=price;
        this.pageNumber=pageNumber;
        this.fichier=fichier;
        this.pagePrice=pagePrice;
        this.motCle=motCle;
        this.disponibility=disponibility;
        this.description = description;
        this.link=link;
        this.couverture=couverture;
        this.annee_edition=annee_edition;
        this.editeur=editeur;
        this.lieu_edition=lieu_edition;
        this.these = these;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMotCle() {
        return motCle;
    }

    public void setMotCle(String motCle) {
        this.motCle = motCle;
    }

    public String getCouverture() {
        return couverture;
    }

    public void setCouverture(String couverture) {
        this.couverture = couverture;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getLieu_edition() {
        return lieu_edition;
    }

    public void setLieu_edition(String lieu_edition) {
        this.lieu_edition = lieu_edition;
    }

    public String getAnnee_edition() {
        return annee_edition;
    }

    public void setAnnee_edition(String annee_edition) {
        this.annee_edition = annee_edition;
    }

    public Double getPagePrice() {
        return pagePrice;
    }

    public void setPagePrice(Double pagePrice) {
        this.pagePrice = pagePrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDisponibility() {
        return disponibility;
    }

    public void setDisponibility(Boolean disponibility) {
        this.disponibility = disponibility;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getBibliographyId() {
        return bibliographyId;
    }

    public void setBibliographyId(Long bibliographyId) {
        this.bibliographyId = bibliographyId;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public These getThese() {
        return these;
    }

    public void setThese(These these) {
        this.these = these;
    }
}
