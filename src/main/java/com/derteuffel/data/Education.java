package com.derteuffel.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Education implements Serializable {

    @Id
    @GeneratedValue
    private Long educationId;
    @Column
    @NotEmpty
    private String title;
    @Column
    private String description;
    @Column
    private String edition;
    @Column
    private String editeur;
    @Column
    private String maison_edition;
    @Column
    private String anne_edition;
    @Column
    private Boolean status=false;
    @Column
    private String pieces;
    @Column
    private String type;
    @Column
    private String couverture;
    private String region;
    private  String country;
    private int classe;
    private Boolean suprime=false;

    @ManyToOne
    private User user;



    public Education() {
    }

    public Education(@NotEmpty String title, String description, String edition, String editeur,
                     String maison_edition, String anne_edition, Boolean status, String pieces, String type, String couverture, String region, String country, int classe,Boolean suprime) {
        this.title = title;
        this.description = description;
        this.edition = edition;
        this.suprime=suprime;
        this.editeur = editeur;
        this.maison_edition = maison_edition;
        this.anne_edition = anne_edition;
        this.status = status;
        this.pieces = pieces;
        this.type = type;
        this.couverture = couverture;
        this.region = region;
        this.country = country;
        this.classe = classe;
    }


    public Boolean getSuprime() {
        return suprime;
    }

    public void setSuprime(Boolean suprime) {
        this.suprime = suprime;
    }

    public String getRegion() {
        return region;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public String getCouverture() {
        return couverture;
    }

    public void setCouverture(String couverture) {
        this.couverture = couverture;
    }

    public Long getEducationId() {
        return educationId;
    }

    public void setEducationId(Long educationId) {
        this.educationId = educationId;
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

    public String getPieces() {
        return pieces;
    }

    public void setPieces(String pieces) {
        this.pieces = pieces;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getMaison_edition() {
        return maison_edition;
    }

    public void setMaison_edition(String maison_edition) {
        this.maison_edition = maison_edition;
    }

    public String getAnne_edition() {
        return anne_edition;
    }

    public void setAnne_edition(String anne_edition) {
        this.anne_edition = anne_edition;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Education)) return false;
        Education education = (Education) o;
        return getClasse() == education.getClasse() &&
                Objects.equals(getEducationId(), education.getEducationId()) &&
                Objects.equals(getTitle(), education.getTitle()) &&
                Objects.equals(getDescription(), education.getDescription()) &&
                Objects.equals(getEdition(), education.getEdition()) &&
                Objects.equals(getEditeur(), education.getEditeur()) &&
                Objects.equals(getMaison_edition(), education.getMaison_edition()) &&
                Objects.equals(getAnne_edition(), education.getAnne_edition()) &&
                Objects.equals(getStatus(), education.getStatus()) &&
                Objects.equals(getPieces(), education.getPieces()) &&
                Objects.equals(getType(), education.getType()) &&
                Objects.equals(getCouverture(), education.getCouverture()) &&
                Objects.equals(getRegion(), education.getRegion()) &&
                Objects.equals(getCountry(), education.getCountry()) &&
                Objects.equals(getSuprime(), education.getSuprime()) &&
                Objects.equals(getUser(), education.getUser());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEducationId(), getTitle(), getDescription(), getEdition(), getEditeur(),
                getMaison_edition(), getAnne_edition(), getStatus(), getPieces(), getType(), getCouverture(),
                getRegion(), getCountry(), getClasse(), getSuprime(), getUser());
    }
}
