package com.derteuffel.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by derteuffel on 14/05/2019.
 */
@Entity
public class Syllabus implements Serializable{

    @Id
    @GeneratedValue
    private Long syllabusId;
    private String auteur;
    private String title;
    @Column
    private String description;
    @Column
    private String category;
    private String universite;
    private String faculte;
    private String departement;
    private String annee;
    private String options;
    private String promotion;
    private String otherInformation;
    @Column
    private ArrayList<String> pieces;
    @Column
    private Date creationDate=new Date();
    private Double publishPrice;
    private Boolean status;
    private Boolean suprimee;

    public Syllabus() {
    }

    public Syllabus(String auteur, String title, String description, String category, String universite, String annee, String otherInformation, String faculte, String departement, String options,
                    String promotion, ArrayList<String> pieces, Date creationDate, Double publishPrice, Boolean status, Boolean suprimee) {
        this.auteur = auteur;
        this.description = description;
        this.category = category;
        this.universite = universite;
        this.faculte = faculte;
        this.annee=annee;
        this.otherInformation=otherInformation;
        this.departement = departement;
        this.options = options;
        this.title=title;
        this.promotion = promotion;
        this.pieces = pieces;
        this.creationDate = creationDate;
        this.publishPrice = publishPrice;
        this.status = status;
        this.suprimee = suprimee;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public Long getSyllabusId() {
        return syllabusId;
    }

    public void setSyllabusId(Long syllabusId) {
        this.syllabusId = syllabusId;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUniversite() {
        return universite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public String getFaculte() {
        return faculte;
    }

    public void setFaculte(String faculte) {
        this.faculte = faculte;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = pieces;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Double getPublishPrice() {
        return publishPrice;
    }

    public void setPublishPrice(Double publishPrice) {
        this.publishPrice = publishPrice;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getSuprimee() {
        return suprimee;
    }

    public void setSuprimee(Boolean suprimee) {
        this.suprimee = suprimee;
    }
}
