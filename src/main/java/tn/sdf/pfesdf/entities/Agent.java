package tn.sdf.pfesdf.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "agents",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class Agent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgent;
    private String Photo;
    private String nom;
    private String prenom;
    private Long idCentre;

    @Size(max = 50)
    @Email
    private String email;
    private LocalDate age;
    @Enumerated(EnumType.STRING)
    private TrancheAge trancheAge;
    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    @Size(max = 120)
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Discipline discipline;
    private Integer phnum;
    private Integer cin;
    private Integer nbmax=5;
    private Integer nbencours=0;
    //private String role;
    private double logitude;
    private double latitude;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "agent")
    private Set<RendezVous>rendezVousag;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Set<Personne>personnesparrain;
   // @JsonIgnore
    //@OneToMany(cascade = CascadeType.ALL,mappedBy = "agental")
    //private Set<Alerte>alertes;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "agent_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    public  Agent(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
