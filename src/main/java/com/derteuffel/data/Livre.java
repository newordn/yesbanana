package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by derteuffel on 14/05/2019.
 */
@Entity
@PrimaryKeyJoinColumn(name = "postId")
public class Livre extends Post implements Serializable{



    public Livre() {
    }



}
