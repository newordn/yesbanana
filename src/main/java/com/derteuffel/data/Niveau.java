package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class Niveau implements Serializable {

    @Id
    @GeneratedValue
    private Long niveauId;
    private int niveau;
    @OneToMany(mappedBy = "niveau")
    private List<Matiere> matieres;

    public Niveau() {
    }

    public Niveau(Long niveauId, int niveau, List<Matiere> matieres) {
        this.niveauId = niveauId;
        this.niveau = niveau;
        this.matieres = matieres;
    }

    public Long getNiveauId() {
        return niveauId;
    }

    public void setNiveauId(Long niveauId) {
        this.niveauId = niveauId;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public List<Matiere> getMatieres() {
        return matieres;
    }

    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres;
    }
}
