package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Entrepreneuriat {

    @Id
    @GeneratedValue
    private Long entrepreneuriatId;

    private String title;
    private ArrayList<String> pieces=new ArrayList<>();
    private String description;
    private Boolean status;
    private Boolean states;
    private Double price;

    public Entrepreneuriat() {
    }

    public Entrepreneuriat(String title, ArrayList<String> pieces, String description, Boolean status, Boolean states, Double price) {
        this.title = title;
        this.pieces = pieces;
        this.description = description;
        this.status = status;
        this.states = states;
        this.price=price;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getEntrepreneuriatId() {
        return entrepreneuriatId;
    }

    public void setEntrepreneuriatId(Long entrepreneuriatId) {
        this.entrepreneuriatId = entrepreneuriatId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = pieces;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStates() {
        return states;
    }

    public void setStates(Boolean states) {
        this.states = states;
    }
}
