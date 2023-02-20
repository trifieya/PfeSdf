package tn.sdf.pfesdf.entities;

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
public class Programme implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProg;
    private String nomProg;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    @Enumerated(EnumType.STRING)
    private TypeProgramme typeProgramme;
    private String description;
    @ManyToMany(mappedBy = "programmes",cascade = CascadeType.ALL)
    private Set<Personne> personnespro;





}
