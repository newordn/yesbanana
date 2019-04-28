package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Neword on 30/03/2019.
 */
@Entity
public class Article {


    @Id
    @GeneratedValue
    Long id;

    public Long getId() {
        return id;
    }

    private String name;

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String container;

    public Article(String name, String container, Double prix, Panier panier) {
        this.name = name;
        this.container = container;
        this.prix = prix;
        this.panier = panier;
    }

    private Double prix;
    @ManyToOne
    private Panier panier;

    public Article() {
    }

    public Article(String name, Double prix, Panier panier) {
        this.name = name;
        this.prix = prix;
        this.panier = panier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }



}
