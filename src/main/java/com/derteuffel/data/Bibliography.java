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
    private String description;
    private String link;
    private Boolean disponibility;
    private Double price;
    private Double pagePrice;

    @ManyToOne
    private These these;

    public Bibliography() {
    }

    public Bibliography(String auteur, String title, String description, These these, String link,Boolean disponibility,Double pagePrice, Double price) {
        this.auteur = auteur;
        this.title = title;
        this.price=price;
        this.pagePrice=pagePrice;
        this.disponibility=disponibility;
        this.description = description;
        this.link=link;
        this.these = these;
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
