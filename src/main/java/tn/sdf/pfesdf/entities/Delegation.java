package tn.sdf.pfesdf.entities;

import com.fasterxml.jackson.annotation.*;
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
public class Delegation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_delegation;
    private Long code;
    private String libelle ;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "delegation")
    private Set<Personne> personnesv;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "delegation")
    private Set<Agent> agentsv;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "delegation")
    private Set<Parrain> parrainsv;

    @ManyToOne
    Gouvernerat gouvernerat;
}
