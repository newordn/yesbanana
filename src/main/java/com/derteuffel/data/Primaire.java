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



    public Primaire() {
    }
    public Primaire(String title,Double price, String description, int likes, Date realeseDate, ArrayList<String> pieces, String type,Boolean status, String couverture) {
        super(title,price, description, likes, pieces, type,status, realeseDate,couverture);
    }

}
