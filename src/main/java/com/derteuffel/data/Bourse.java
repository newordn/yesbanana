package com.derteuffel.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    private Date createdDate=new Date();
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadLine;

    public Bourse() {
    }

    public Bourse(String link, String title, String description, Boolean status, Boolean suprime, Date createdDate,Date deadLine) {
        this.link = link;
        this.title=title;
        this.description = description;
        this.status=status;
        this.suprime=suprime;
        this.createdDate=createdDate;
        this.deadLine=deadLine;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
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
