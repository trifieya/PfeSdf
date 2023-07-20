package tn.sdf.pfesdf.entities;

import lombok.*;

import javax.persistence.*;
import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class RendezVous implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRdv;
    private LocalDateTime dateRdv;
    private LocalDateTime dateFinRdv;
    private String description;
    private String lieu;
    private String motif;
    private Boolean conclu;
    @ManyToOne
    Profil profilrdv;
    @ManyToOne
    Agent agent;

  
}
