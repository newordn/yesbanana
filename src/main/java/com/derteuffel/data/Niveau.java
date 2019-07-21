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
    private String slug;
    @OneToMany(mappedBy = "niveau")
    private List<Primaire> primaires;

    public Niveau() {
    }

    public Niveau(int niveau, String slug, List<Primaire> primaires) {
        this.niveau = niveau;
        this.slug = slug;
        this.primaires = primaires;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public List<Primaire> getPrimaires() {
        return primaires;
    }

    public void setPrimaires(List<Primaire> primaires) {
        this.primaires = primaires;
    }
}
