package tn.sdf.pfesdf.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Centre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCentre;
    private String nom;
    private  String adresse;
    private Integer telephone;
    private String courriel;
    private Integer capMax;
    private Float longitude;
    private Float latitude;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "centre")
    private Set<Personne> personnesc;
    @ManyToOne(cascade = CascadeType.ALL)
    Ville villec;



}
