package com.derteuffel.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Commande implements Serializable
{
    @Id
    @GeneratedValue
    private Long commandeId;
    private int reference;
    private String title;
    private String nomClient;
    private Double montant;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateLivraison;
    private Date dateReservation=new Date();
    private String lieuLivraison;
    private String telephoneBeneficaire;
    private Boolean status;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToMany
    @JoinTable(name = "commande_bibliography", joinColumns = @JoinColumn(name = "commande_id"), inverseJoinColumns = @JoinColumn(name = "bibliography_id"))
    private List<Bibliography> bibliographies;

    public Commande() {
    }

    public Commande(int reference, String title,Boolean status, String nomClient, Double montant, Date dateLivraison, Date dateReservation, String lieuLivraison, String telephoneBeneficaire, List<Bibliography> bibliographies) {
        this.reference = reference;
        this.title = title;
        this.status=status;
        this.montant = montant;
        this.nomClient=nomClient;
        this.dateLivraison = dateLivraison;
        this.dateReservation = dateReservation;
        this.lieuLivraison = lieuLivraison;
        this.telephoneBeneficaire = telephoneBeneficaire;
        this.bibliographies = bibliographies;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getLieuLivraison() {
        return lieuLivraison;
    }

    public void setLieuLivraison(String lieuLivraison) {
        this.lieuLivraison = lieuLivraison;
    }

    public String getTelephoneBeneficaire() {
        return telephoneBeneficaire;
    }

    public void setTelephoneBeneficaire(String telephoneBeneficaire) {
        this.telephoneBeneficaire = telephoneBeneficaire;
    }

    public List<Bibliography> getBibliographies() {
        return bibliographies;
    }

    public void setBibliographies(List<Bibliography> bibliographies) {
        this.bibliographies = bibliographies;
    }
}
