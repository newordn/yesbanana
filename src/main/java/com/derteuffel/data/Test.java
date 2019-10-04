package com.derteuffel.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by derteuffel on 07/12/2018.
 */
@Entity
public class Test implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private ArrayList<String> pieces;

    public Test() {
    }

    public Test(ArrayList<String> pieces) {
        this.pieces = pieces;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = pieces;
    }
}
