package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
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
    private Region region;

    public Primaire() {
    }
    public Primaire(String title, String description, int likes, Date realeseDate, ArrayList<String> pieces, String type,Boolean status, String fileType) {
        super(title, description, likes, pieces, type,status, realeseDate,fileType);
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
