package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Entity
@PrimaryKeyJoinColumn(name = "educationId")
public class Primaire extends Education implements Serializable {

    @ManyToOne
    private Region region;

    public Primaire() {
    }
    public Primaire(String title, String description, Long likes, ArrayList<String> pieces, String type) {
        super(title, description, likes, pieces, type);
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
