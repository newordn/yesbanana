package com.derteuffel.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by derteuffel on 23/03/2019.
 */
@Entity
public class Payment implements Serializable {

    @Id
    @GeneratedValue
    private Long paymentId;
    private  Double amount;
    private String description;
    private String article;
    private Date paymentDate= new Date();
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    public Payment() {
    }

    public Payment(Double amount, String article,String description,User user) {
        this.amount = amount;
        this.article = article;
        this.description = description;
        this.user = user;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
