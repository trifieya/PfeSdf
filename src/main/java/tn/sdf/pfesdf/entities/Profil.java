package tn.sdf.pfesdf.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
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
    private int score;
    private boolean archived;
    @Enumerated(EnumType.STRING)
    private Classification classification;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profilrdv")
    //@JsonManagedReference
    private Set<RendezVous>rendezVousSet;
    @JsonManagedReference //L'annotation @JsonManagedReference est utilisée pour la classe parente (Profil), tandis que l'annotation @JsonBackReference est utilisée pour la classe enfant (FeedBack), ce qui permet d'éviter la sérialisation en boucle lors de la conversion en JSON.
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy = "profilf")
    //@JsonManagedReference
    private Set<FeedBack>feedBacks;
    @OneToOne
    Personne profilpresonne;

    @ManyToMany (cascade = CascadeType.ALL,mappedBy = "profildoc")
    private Set<Document>documents =new HashSet<>();



}
