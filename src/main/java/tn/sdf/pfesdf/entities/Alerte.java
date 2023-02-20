package tn.sdf.pfesdf.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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
    private String description;
    private Float logitude;
    private Float latitude;
    @ManyToOne
    Agent agental;

}
