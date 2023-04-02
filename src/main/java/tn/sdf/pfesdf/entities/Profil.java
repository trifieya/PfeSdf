package tn.sdf.pfesdf.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfil;
    private Diplome diplome;
    private String maladie;
    private LocalDate dateHebergCentre;
    private LocalDate dateInscriPlatform;
    private Situation situation;
    @OneToOne(mappedBy = "profil")
    Personne personneprof;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "profildoc")
    private Set<Document>documents;



}
