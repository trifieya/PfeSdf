package tn.sdf.pfesdf.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Profil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfil;
    @Enumerated(EnumType.STRING)
    private Diplome diplome;
    private String maladie;
    private LocalDate dateHebergCentre;
    private LocalDate dateInscriPlatform;
    @Enumerated(EnumType.STRING)
    private Situation situation;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profilrdv")
    //@JsonManagedReference
    private Set<RendezVous>rendezVousSet;
    @OneToOne
    Personne profilpresonne;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "profildoc")
    private Set<Document>documents;



}
