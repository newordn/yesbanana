package com.derteuffel.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import com.derteuffel.data.Panier;
import javax.persistence.*;
import javax.validation.constraints.Email;
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
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginningPeriod ;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endPeriod;
    //days interval
    @Column
    private String dayWorkBeginning;
    @Column
    private String dayWorkEnd;

    //student management information
    @Column
    private String studentNumber;
    //affiliation quality
    @Column
    private Boolean numberOfWorkers;
    @Column
    private String numberInGroupe;
    //another detail for user
    @Column
    private String anotherDetail;
    //private cv informations
    @Column
    private String cv;

    @Column
    private String resetToken;
    @Column
    private String amount;
    @Column
    private String classe;
    @Column
    private  String experienceYear="0";
     private Boolean status;


    @Column
    @NotNull
    private Boolean autorization=false;

    @OneToMany(mappedBy = "user")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<These> theses;
    @OneToMany(mappedBy = "user")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<Bibliography> bibliographies;
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public List<Panier> getPaniers() {
        return paniers;
    }

    public void setPaniers(List<Panier> paniers) {
        this.paniers = paniers;
    }

    @OneToMany(mappedBy = "user")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<Panier> paniers;

    @OneToMany(mappedBy = "user")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<Rapport> rapports;

    @ManyToMany
    @JoinTable(name = "user_groupe", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "groupe_id"))
    private List<Groupe> groupes;

    public User() {
    }

    public User(@NotNull @Size(min = 2) String name, @Email @NotNull String email, @Length(min = 6) String password,
                @NotNull String country, @NotNull @Size(min = 3) String region, @Size(min = 2) String university,
                @Size(min = 3) String faculty, @NotNull String number, String img, Date createdDate, Boolean active,
                String category, String diplom, String expertDomain, Date beginningPeriod, Date endPeriod,
                String dayWorkBeginning, String dayWorkEnd, String studentNumber, Boolean numberOfWorkers,
                String anotherDetail, String classe, String experienceYear, String cv, List<These> theses,
                Boolean status, Set<Role> roles, List<Groupe> groupes, Boolean autorization, String amount) {
        this.name = name;
        this.email = email;
        this.experienceYear= experienceYear;
        this.autorization=autorization;
        this.password = password;
        this.classe=classe;
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
        this.status=status;
        this.expertDomain = expertDomain;
        this.beginningPeriod = beginningPeriod;
        this.endPeriod = endPeriod;
        this.dayWorkBeginning = dayWorkBeginning;
        this.dayWorkEnd = dayWorkEnd;
        this.studentNumber = studentNumber;
        this.numberOfWorkers = numberOfWorkers;
        this.anotherDetail = anotherDetail;
        this.cv = cv;
        this.theses = theses;
        this.roles = roles;
        this.groupes = groupes;
        this.amount=amount;
    }

    public User(@NotNull @Size(min = 2) String name, @Email @NotNull String email,
                @Length(min = 6) String password, @NotNull String country, @NotNull @Size(min = 3) String region,
                @Size(min = 2) String university, @Size(min = 3) String faculty, @NotNull String number, String img,
                Date createdDate, Boolean active, Boolean autorization) {
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
        this.autorization=autorization;
    }

    public User(@NotNull @Size(min = 2) String name, @Email @NotNull String email, @Length(min = 6) String password,
                @NotNull String country, @NotNull @Size(min = 3) String region, @Size(min = 2) String university,
                @Size(min = 3) String faculty, @NotNull String number, String img, Date createdDate, Boolean active,
                String category, String diplom, String expertDomain, Date beginningPeriod, Date endPeriod, String dayWorkBeginning,
                String dayWorkEnd, String studentNumber, Boolean numberOfWorkers, String numberInGroupe, String anotherDetail, String cv,
                List<These> theses,String classe, String experienceYear, Set<Role> roles, List<Groupe> groupes, Boolean autorization) {
        this.name = name;
        this.autorization=autorization;
        this.experienceYear=experienceYear;
        this.email = email;
        this.classe=classe;
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
        this.roles = roles;
        this.groupes = groupes;
    }

    public List<Bibliography> getBibliographies() {
        return bibliographies;
    }

    public void setBibliographies(List<Bibliography> bibliographies) {
        this.bibliographies = bibliographies;
    }

    public List<Rapport> getRapports() {
        return rapports;
    }

    public void setRapports(List<Rapport> rapports) {
        this.rapports = rapports;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getExperienceYear() {
        return experienceYear;
    }

    public void setExperienceYear(String experienceYear) {
        this.experienceYear = experienceYear;
    }


    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Boolean getAutorization() {
        return autorization;
    }

    public void setAutorization(Boolean autorization) {
        this.autorization = autorization;
    }

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

    public String getDayWorkBeginning() {
        return dayWorkBeginning;
    }

    public void setDayWorkBeginning(String dayWorkBeginning) {
        this.dayWorkBeginning = dayWorkBeginning;
    }

    public String getDayWorkEnd() {
        return dayWorkEnd;
    }

    public void setDayWorkEnd(String dayWorkEnd) {
        this.dayWorkEnd = dayWorkEnd;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Boolean getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public void setNumberOfWorkers(Boolean numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
    }

    public String getNumberInGroupe() {
        return numberInGroupe;
    }

    public void setNumberInGroupe(String numberInGroupe) {
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

    public Set<Role> getRoles() {
        return roles;
    }
    public void  addRoles(Set<Role> roles){
        this.roles=roles;
    }

    public void setRoles(Role role) {
        roles.add(role);
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

    public void removeRelation(Role role){
        roles.remove(role);
        role.getUsers().remove(this);
    }

    public void removeGroupeRelation(Groupe groupe){
        groupes.remove(groupe);
        groupe.getUsers().remove(this);
    }


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
        this.groupes=groupes;
    }
}
