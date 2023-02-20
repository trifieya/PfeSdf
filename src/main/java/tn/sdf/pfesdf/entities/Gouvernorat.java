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
public class Gouvernorat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGouvernorat;
    private String nomGouvernorat;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "gouvernorat")
    private Set<Ville>villes;

}
