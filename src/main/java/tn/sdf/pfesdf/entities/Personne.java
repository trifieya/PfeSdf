package tn.sdf.pfesdf.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "personnes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })

public class Personne implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonne;
    private String Photo; //à ajouter  tetnaha ml profil
    private String nom;
    private String prenom;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private LocalDate age;
    @Enumerated(EnumType.STRING)
    private TrancheAge trancheAge ;
    @Enumerated(EnumType.STRING)
    private TypePersonne typePersonne = TypePersonne.JEUNE_SDF; //moch commun nrml mahich importante dima null bch nkhaleha
    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    @Size(max = 120)
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Integer phnum;
    private Integer cin; //bch tetzed mbaad w tetnaha ml profil
    //private String role;
    private double logitude;
    private double latitude;
    private LocalDate date_ajout;
    private LocalDate date_affect_parrain;

   @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "personne_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    Centre centre;

    @ManyToOne
    Delegation delegation;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Programme> programmes;
    @ManyToOne
    Parrain parrain;
    @ManyToOne
    Agent agent;
    @Enumerated(EnumType.STRING)
    private Discipline discipline;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personnef")
    //@JsonManagedReference
    private Set<FeedBack>feedBacks;
//evider occurence donnée loop
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "profilpresonne")
    Profil profil;


    public  Personne (String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }





}
