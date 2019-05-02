package com.derteuffel.data;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by Neword on 30/03/2019.
 */
@Entity
public class Panier {

    @Id
    @GeneratedValue
    Long id;

    public Boolean getBought() {
        return bought;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
    }

    private Boolean bought=false;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCount() {
        return count;
    }
    public Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Panier() {
    }

    public Panier(Boolean status, Date date, Double count, List<Article> articles, User user, Boolean bought) {
        this.status = status;
        this.date = date;
        this.count = count;
        this.articles = articles;
        this.user = user;
        this.bought = bought;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    private Date date;

    private Double count;
    @OneToMany(mappedBy = "panier")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<Article> articles;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
