package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue
    private Long commentId;
    private Date created_comment;
    private String content;
    private String publisher;
    @ManyToOne
    private Event event;


    public Comment() {
    }

    public Comment(Date created_comment, String content, String publisher,Event event) {
        this.created_comment = created_comment;
        this.content = content;
        this.event = event;
        this.publisher=publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Date getCreated_comment() {
        return created_comment;
    }

    public void setCreated_comment(Date created_comment) {
        this.created_comment = created_comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
