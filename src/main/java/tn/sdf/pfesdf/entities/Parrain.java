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
public class Parrain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParrain;
    private String Photo;
    private String nom;
    private String prenom;
    private String mail;
    private LocalDate age;
    @Enumerated(EnumType.STRING)
    private TrancheAge trancheAge;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Integer phnum;
    private Integer cin;
    private String role;
    private Float logitude;
    private Float latitude;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parrain")
    private Set<Personne>personnespar;

}
