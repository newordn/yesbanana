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


}
