package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by derteuffel on 05/06/2019.
 */
@Entity
public class Bourse implements Serializable{

    @Id
    @GeneratedValue
    private Long bourseId;
    private String link;
    private String title;
    private String description;
    private Boolean status;
    private Boolean suprime;

    public Bourse() {
    }

    public Bourse(String link, String title, String description, Boolean status, Boolean suprime) {
        this.link = link;
        this.title=title;
        this.description = description;
        this.status=status;
        this.suprime=suprime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getSuprime() {
        return suprime;
    }

    public void setSuprime(Boolean suprime) {
        this.suprime = suprime;
    }

    public Long getBourseId() {
        return bourseId;
    }

    public void setBourseId(Long bourseId) {
        this.bourseId = bourseId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
