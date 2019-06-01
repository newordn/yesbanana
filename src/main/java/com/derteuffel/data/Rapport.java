package com.derteuffel.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by derteuffel on 01/06/2019.
 */
@Entity
public class Rapport implements Serializable{

    @Id
    @GeneratedValue
    private Long rapportId;
    private String userName;
    private  String occupation;
    private String title;
    private String resumes;
    private ArrayList<String> pieces;
    private Date created_date= new Date();
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date release_date;
    private Boolean supprime;
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Groupe groupe;

    public Rapport() {
    }

    public Rapport(String userName, String occupation, String title, String resumes,
                   ArrayList<String> pieces, Date created_date, Date release_date, Boolean status, Boolean supprime) {
        this.userName = userName;
        this.occupation = occupation;
        this.supprime=supprime;
        this.title = title;
        this.resumes = resumes;
        this.status=status;
        this.pieces = pieces;
        this.created_date = created_date;
        this.release_date = release_date;
    }

    public Boolean getSupprime() {
        return supprime;
    }

    public void setSupprime(Boolean supprime) {
        this.supprime = supprime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getRapportId() {
        return rapportId;
    }

    public void setRapportId(Long rapportId) {
        this.rapportId = rapportId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResumes() {
        return resumes;
    }

    public void setResumes(String resumes) {
        this.resumes = resumes;
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = pieces;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
}
