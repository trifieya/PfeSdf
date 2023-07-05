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
@Entity
public class Gouvernerat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_gouvernerat;
    private Long code;
    private String libelle ;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "gouvernerat")
    private Set<Delegation> delegations;

}
