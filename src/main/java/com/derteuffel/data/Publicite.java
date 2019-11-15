package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Publicite implements Serializable {

    @Id
    @GeneratedValue
    private Long publiciteId;
    private String title;
    private String couverture;
    private String file;
    private Date createdDate= new Date();
    private String fileType;
    private Boolean status=false;

    public Publicite() {
    }

    public Publicite(Long publiciteId, String title, String couverture, String file, Date createdDate,
                     String fileType, Boolean status) {
        this.publiciteId = publiciteId;
        this.title = title;
        this.couverture = couverture;
        this.file = file;
        this.createdDate=createdDate;
        this.fileType=fileType;
        this.status=status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getPubliciteId() {
        return publiciteId;
    }

    public void setPubliciteId(Long publiciteId) {
        this.publiciteId = publiciteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCouverture() {
        return couverture;
    }

    public void setCouverture(String couverture) {
        this.couverture = couverture;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
