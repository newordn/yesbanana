package com.derteuffel.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by derteuffel on 13/12/2018.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Post implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Long postId;
    @Column
    private String title;
    private String otherInformation;
    private String publisherName;
    @Column
    private String description;
    @Column
    private String category;
    @Column
    private ArrayList<String> pieces;
    @Column
    private Date creationDate=new Date();
    private Double publishPrice;
    private Boolean status;
    private Boolean suprimee;
    private String publisherEmail;
    private String publisherPhone;
    private String publisherLocalisation;
    private String type;
    public Post() {
    }

    public Post(String title,Boolean status, Double publishPrice,Boolean suprimee,
                String description,String otherInformation,String type,
                String publisherName, String category, ArrayList<String> pieces, Date creationDate) {
        this.title = title;
        this.description = description;
        this.status=status;
        this.publishPrice=publishPrice;
        this.type=type;
        this.otherInformation= otherInformation;
        this.publisherName=publisherName;
        this.category = category;
        this.pieces = pieces;
        this.creationDate = creationDate;
        this.suprimee=suprimee;
    }

    public String getPublisherEmail() {
        return publisherEmail;
    }

    public void setPublisherEmail(String publisherEmail) {
        this.publisherEmail = publisherEmail;
    }

    public String getPublisherPhone() {
        return publisherPhone;
    }

    public void setPublisherPhone(String publisherPhone) {
        this.publisherPhone = publisherPhone;
    }

    public String getPublisherLocalisation() {
        return publisherLocalisation;
    }

    public void setPublisherLocalisation(String publisherLocalisation) {
        this.publisherLocalisation = publisherLocalisation;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSuprimee() {
        return suprimee;
    }

    public void setSuprimee(Boolean suprimee) {
        this.suprimee = suprimee;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Double getPublishPrice() {
        return publishPrice;
    }

    public void setPublishPrice(Double publishPrice) {
        this.publishPrice = publishPrice;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = pieces;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
