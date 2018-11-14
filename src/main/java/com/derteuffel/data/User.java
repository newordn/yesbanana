package com.derteuffel.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.internal.engine.groups.Group;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * Created by derteuffel on 06/10/2018.
 */

@Entity
public class User implements Serializable{

    @Id
    @GeneratedValue
    private Long userId;

    @Column
    @NotNull
    @Size(min = 2)
    private  String name;
    @Column
    @Email
    @NotNull
    private String email;
    @Column
    @Length(min = 6)
    private String password;
    @NotNull
    @Column
    private String country;
    @Column
    @NotNull
    @Size(min = 3)
    private String region;
    @Column
    @Size(min = 2)
    private String university;
    @Column
    @Size(min = 3)
    private String faculty;
    @Column
    @NotNull
    private String number;
    @Column
    private String img;
    @Column
    private Date createdDate= new Date();
    @Column
    private Boolean active;

    //next added attributes
    //qualification information
    @Column
    private String category;
    @Column
    private  String diplom;
    @Column
    private String expertDomain;

    //work validity period
    @Column
    private Date beginningPeriod;
    @Column
    private Date endPeriod;
    //days interval
    @Column
    private Date dayWorkBeginning;
    @Column
    private Date dayWorkEnd;

    //student management information
    @Column
    private int studentNumber;
    //affiliation quality
    @Column
    private int numberOfWorkers;
    @Column
    private int numberInGroupe;
    //another detail for user
    @Column
    private String anotherDetail;
    //private cv informations
    @Column
    private String cv;


    //private Boolean isAutorized;

    @OneToMany(mappedBy = "user")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<These> theses;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role")
    private Role role;


    @ManyToMany(mappedBy="users")
    private List<Groupe> groupes;

    public User() {
    }

    public User(@NotNull @Size(min = 2) String name, @Email @NotNull String email, @Length(min = 6) String password,
                @NotNull String country, @NotNull @Size(min = 3) String region, @Size(min = 2) String university,
                @Size(min = 3) String faculty, @NotNull String number, String img, Date createdDate, Boolean active,
                String category, String diplom, String expertDomain, Date beginningPeriod, Date endPeriod, Date dayWorkBeginning,
                Date dayWorkEnd, int studentNumber, int numberOfWorkers, int numberInGroupe, String anotherDetail, String cv,
                List<These> theses, Role role, List<Groupe> groupes) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.country = country;
        this.region = region;
        this.university = university;
        this.faculty = faculty;
        this.number = number;
        this.img = img;
        this.createdDate = createdDate;
        this.active = active;
        this.category = category;
        this.diplom = diplom;
        this.expertDomain = expertDomain;
        this.beginningPeriod = beginningPeriod;
        this.endPeriod = endPeriod;
        this.dayWorkBeginning = dayWorkBeginning;
        this.dayWorkEnd = dayWorkEnd;
        this.studentNumber = studentNumber;
        this.numberOfWorkers = numberOfWorkers;
        this.numberInGroupe = numberInGroupe;
        this.anotherDetail = anotherDetail;
        this.cv = cv;
        this.theses = theses;
        this.role = role;
        this.groupes = groupes;
    }

   /* public Boolean getAutorized() {
        return isAutorized;
    }*/

   /* public void setAutorized(Boolean autorized) {
        isAutorized = autorized;
    }*/

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiplom() {
        return diplom;
    }

    public void setDiplom(String diplom) {
        this.diplom = diplom;
    }

    public String getExpertDomain() {
        return expertDomain;
    }

    public void setExpertDomain(String expertDomain) {
        this.expertDomain = expertDomain;
    }

    public Date getBeginningPeriod() {
        return beginningPeriod;
    }

    public void setBeginningPeriod(Date beginningPeriod) {
        this.beginningPeriod = beginningPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Date getDayWorkBeginning() {
        return dayWorkBeginning;
    }

    public void setDayWorkBeginning(Date dayWorkBeginning) {
        this.dayWorkBeginning = dayWorkBeginning;
    }

    public Date getDayWorkEnd() {
        return dayWorkEnd;
    }

    public void setDayWorkEnd(Date dayWorkEnd) {
        this.dayWorkEnd = dayWorkEnd;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public void setNumberOfWorkers(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
    }

    public int getNumberInGroupe() {
        return numberInGroupe;
    }

    public void setNumberInGroupe(int numberInGroupe) {
        this.numberInGroupe = numberInGroupe;
    }

    public String getAnotherDetail() {
        return anotherDetail;
    }

    public void setAnotherDetail(String anotherDetail) {
        this.anotherDetail = anotherDetail;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    @JsonIgnore
    public List<Groupe> getGroupe() {
        return groupes;
    }

    public void setGroupe(Groupe groupe) {
        groupes.add(groupe);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
   /* public void addRole(Role role){
        if (!this.roles.contains(role)){
            this.roles.add(role);
        }
        if (!role.getUsers().contains(this)){
            role.getUsers().add(this);
        }
    }
    public void removeRole(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }*/

    @JsonIgnore
    public List<These> getTheses() {
        return theses;
    }


    public void setTheses(List<These> theses) {
        this.theses = theses;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }
}
