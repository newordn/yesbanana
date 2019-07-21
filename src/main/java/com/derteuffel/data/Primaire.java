package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Entity
@PrimaryKeyJoinColumn(name = "educationId")
public class Primaire extends Education implements Serializable {

    @ManyToOne
    private Niveau niveau;
    @ManyToOne
    private Matiere matiere;


    public Primaire() {
    }

    public Primaire(@NotEmpty String title, String description, String edition, String editeur, String maison_edition, String anne_edition, Boolean status, String pieces,
                    String type, String couverture, String region, String country, int classe, Boolean suprime, Niveau niveau, Matiere matiere) {
        super(title, description, edition, editeur, maison_edition, anne_edition, status, pieces, type, couverture, region, country, classe, suprime);
        this.niveau = niveau;
        this.matiere = matiere;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
