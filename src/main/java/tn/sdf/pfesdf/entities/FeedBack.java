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
public class FeedBack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFeedback;
    private LocalDate dateajoutFeed;
    @Enumerated(EnumType.STRING)
    private TypeFeedback feedcentre;
    @Enumerated(EnumType.STRING)
    private TypeFeedback feedactivite;
    @Enumerated(EnumType.STRING)
    private TypeFeedback feedglobal;
    //private Integer note;
    //private TypeFeedback typeFeedback;
    //private Long idCentre;
    private Long idAgent;
    private Long idParrain;
    @ManyToOne
    Personne personnef;



}
