package com.derteuffel.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Education implements Serializable {

    @Id
    @GeneratedValue
    private Long educationId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Long likes;
    @Column
    private ArrayList<String> pieces=new ArrayList<>();
    @Column
    private String type;


    public Education() {
    }

    public Education(String title, String description, Long likes, ArrayList<String> pieces, String type) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.pieces = pieces;
        this.type = type;
    }

    public Long getEducationId() {
        return educationId;
    }

    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = pieces;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
