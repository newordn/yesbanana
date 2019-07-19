package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class Matiere implements Serializable {

    @Id
    @GeneratedValue
    private Long matiereId;
    private String name;
    private int classe;
    @OneToMany(mappedBy = "matiere")
    private List<Primaire> primaires;
    private Niveau niveau;

    public Matiere() {
    }

    public Matiere(Long matiereId, String name, List<Primaire> primaires, int classe) {
        this.matiereId = matiereId;
        this.name = name;
        this.classe=classe;
        this.primaires = primaires;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public Long getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(Long matiereId) {
        this.matiereId = matiereId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Primaire> getPrimaires() {
        return primaires;
    }

    public void setPrimaires(List<Primaire> primaires) {
        this.primaires = primaires;
    }
}
