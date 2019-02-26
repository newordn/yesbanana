package com.derteuffel.data;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Entity
public class Options implements Serializable {
    @Id
    @GeneratedValue
    private Long optionsId;

    private  String optionsName;

    @ManyToOne
    private Faculty faculty;
    public Options() {
    }

    public Options(String optionsName, Faculty faculty) {
        this.optionsName = optionsName;
        this.faculty=faculty;
    }

    public Long getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(Long optionsId) {
        this.optionsId = optionsId;
    }

    public String getOptionsName() {
        return optionsName;
    }

    public void setOptionsName(String optionsName) {
        this.optionsName = optionsName;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
