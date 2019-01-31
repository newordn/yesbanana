package com.derteuffel.data;

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

    @ManyToOne
    private These these;

    public Bibliography() {
    }

    public Bibliography(String auteur, String title, String description, These these) {
        this.auteur = auteur;
        this.title = title;
        this.description = description;
        this.these = these;
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

    public These getThese() {
        return these;
    }

    public void setThese(These these) {
        this.these = these;
    }
}
