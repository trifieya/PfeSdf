package tn.sdf.pfesdf.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
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
    private Double longitude;
    private Double latitude;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "centre")
    @JsonIgnore
    private Set<Personne> personnesc;
    @ManyToOne
    Delegation delegation;
    //@ManyToOne(cascade = CascadeType.ALL)
   // Admin adminc;


    public Centre(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Centre(String nom, Integer telephone, Integer capMax) {
        this.nom = nom;
        this.telephone = telephone;
        this.capMax = capMax;
    }
}
