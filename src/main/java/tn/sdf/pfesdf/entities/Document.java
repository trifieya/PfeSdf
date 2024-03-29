package tn.sdf.pfesdf.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDoc;
    @Enumerated(EnumType.STRING)
    private TypeDocument typedocument;
    private Boolean valeur;
    @JsonIgnore
    @ManyToMany
    private Set<Profil> profildoc=new HashSet<>();
}
