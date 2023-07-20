package tn.sdf.pfesdf.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String description;
    private Long idAgent;
        private Long idParrain;
    @JsonBackReference
    @ManyToOne
    Profil profilf;

    //private Integer note;
    //private TypeFeedback typeFeedback;
    //private Long idCentre;


    public FeedBack(TypeFeedback feedcentre, TypeFeedback feedactivite, TypeFeedback feedglobal) {
        this.feedcentre = feedcentre;
        this.feedactivite = feedactivite;
        this.feedglobal = feedglobal;
    }
}
