package com.derteuffel.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by derteuffel on 01/02/2019.
 */
@Entity
public class Bibliotheque implements Serializable {
    @Id
    @GeneratedValue
    private Long bibliothequeId;

    private String bibliotheques;

    @ManyToOne
    private These these;

    public Bibliotheque() {
    }

    public Bibliotheque(String bibliotheques, These these) {
        this.bibliotheques = bibliotheques;
        this.these = these;
    }

    public Long getBibliothequeId() {
        return bibliothequeId;
    }

    public void setBibliothequeId(Long bibliothequeId) {
        this.bibliothequeId = bibliothequeId;
    }

    public String getBibliotheques() {
        return bibliotheques;
    }

    public void setBibliotheques(String bibliotheques) {
        this.bibliotheques = bibliotheques;
    }

    @JsonIgnore
    public These getThese() {
        return these;
    }

    public void setThese(These these) {
        this.these = these;
    }
}
