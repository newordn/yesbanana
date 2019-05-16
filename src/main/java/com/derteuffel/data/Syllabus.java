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
public class Syllabus extends Post{

    public Syllabus() {
    }

    public Syllabus(String title, Boolean status, double publishPrice, String description, String promotion, String faculty,
                    String publisherName, String category, ArrayList<String> pieces, Date creationDate) {
        super(title, status, publishPrice, description, promotion, faculty, publisherName, category, pieces, creationDate);
    }
}
