package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by derteuffel on 14/05/2019.
 */
@Entity
@PrimaryKeyJoinColumn(name = "postId")
public class Livre extends Post{

    private String publisherEmail;
    private String publisherPhone;
    private String publisherLocalisation;

    public Livre() {
    }

    public Livre(String title, Boolean status, double publishPrice, String description, String promotion, String faculty, String publisherName, String category, ArrayList<String> pieces,
                 Date creationDate, String publisherEmail, String publisherPhone, String publisherLocalisation) {
        super(title, status, publishPrice, description, promotion, faculty, publisherName, category, pieces, creationDate);
        this.publisherEmail = publisherEmail;
        this.publisherPhone = publisherPhone;
        this.publisherLocalisation = publisherLocalisation;
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
}
