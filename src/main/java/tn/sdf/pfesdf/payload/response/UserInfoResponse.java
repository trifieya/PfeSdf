package tn.sdf.pfesdf.payload.response;
import tn.sdf.pfesdf.entities.Gender;
import tn.sdf.pfesdf.entities.TrancheAge;

import java.time.LocalDate;
import java.util.List;
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String nom;
    private List<String> roles;

    private String Photo;

    private String prenom;

    private LocalDate age;

    private TrancheAge trancheAge;

    private String password;


    private Gender gender;
    private Integer phnum;
    private Integer cin;

    private Double logitude;

    private Double latitude;

    public UserInfoResponse(Long id, String username, String email, String nom, String photo, String prenom, LocalDate age, TrancheAge trancheAge, String password, Gender gender, Integer phnum, Integer cin, Double logitude, Double latitude, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.Photo = photo;
        this.prenom = prenom;
        this.age = age;
        this.trancheAge = trancheAge;
        this.password = password;
        this.gender = gender;
        this.phnum = phnum;
        this.cin = cin;
        this.logitude = logitude;
        this.latitude = latitude;
        this.roles = roles;
    }


    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getAge() {
        return age;
    }

    public void setAge(LocalDate age) {
        this.age = age;
    }

    public TrancheAge getTrancheAge() {
        return trancheAge;
    }

    public void setTrancheAge(TrancheAge trancheAge) {
        this.trancheAge = trancheAge;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getPhnum() {
        return phnum;
    }

    public void setPhnum(Integer phnum) {
        this.phnum = phnum;
    }

    public Integer getCin() {
        return cin;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public Double getLogitude() {
        return logitude;
    }

    public void setLogitude(Double logitude) {
        this.logitude = logitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
