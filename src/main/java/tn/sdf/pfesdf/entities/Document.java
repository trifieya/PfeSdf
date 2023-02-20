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
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDoc;
    private String document;
    @ManyToOne
    Profil profildoc;
}
