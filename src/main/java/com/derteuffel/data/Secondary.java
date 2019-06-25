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
public class Secondary extends Education implements Serializable {



    public Secondary() {
    }

    public Secondary(@NotEmpty String title, String description, String edition, String editeur, String maison_edition, String anne_edition, Boolean status,
                     String pieces, String type, String couverture, String region, String country, int classe, Boolean suprime) {
        super(title, description, edition, editeur, maison_edition, anne_edition, status, pieces, type, couverture, region, country, classe, suprime);
    }
}
