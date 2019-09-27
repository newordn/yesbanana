package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Newsletter {

    @Id
    @GeneratedValue
    private Long newsletterId;
     private String email;

    public Newsletter() {
    }

    public Newsletter(String email) {
        this.email = email;
    }

    public Long getNewsletterId() {
        return newsletterId;
    }

    public void setNewsletterId(Long newsletterId) {
        this.newsletterId = newsletterId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
