package tn.sdf.pfesdf.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Alerte implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlerte;
    private String alerteur;
    private Integer tel;
    private String description;
    private double logitude;
    private double latitude;
    private Boolean traite;
    private LocalDate datealerte;
    //@ManyToOne
    //Agent agental;

}
