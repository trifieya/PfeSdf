package tn.sdf.pfesdf.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @Table(name = "roles")
    public class Role {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Enumerated(EnumType.STRING)
        @Column(length = 20)
        private ERole name;
        @ManyToMany(mappedBy = "roles")
        private Set<Personne> personnes = new HashSet<>();

        @ManyToMany(mappedBy = "roles")
        private Set<Agent> agents = new HashSet<>();

        @ManyToMany(mappedBy = "roles")
        private Set<Parrain> parrains = new HashSet<>();

        @ManyToMany(mappedBy = "roles")
        private Set<Admin> admins = new HashSet<>();

        public Role(ERole name) {
            this.name = name;
        }



}
