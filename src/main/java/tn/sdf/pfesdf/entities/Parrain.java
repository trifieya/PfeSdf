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
@Table(name = "parrains",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class Parrain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParrain;
    private String Photo;
    private String nom;
    private String prenom;
    @NotBlank
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
    private Integer phnum;
    private Integer cin;
    //private String role;
    private Float logitude;
    private Float latitude;
    @Column(name = "resettoken")
    private String resettoken;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parrain")
    private Set<Personne>personnespar;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "parrain_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public Parrain(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
