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
public class Ville implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVille;
    private String nomVille;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ville")
    private Set<Personne> personnesv;
    @ManyToOne
    Gouvernorat gouvernorat;
}
