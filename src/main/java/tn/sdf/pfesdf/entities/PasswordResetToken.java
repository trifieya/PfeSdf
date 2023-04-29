package tn.sdf.pfesdf.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
@Getter
@Setter
@Entity
@NoArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long token_id;
    private String token;
    private Date expirationTime;
    private static final int EXPIRATION_TIME = 10;

    @OneToOne
    @JoinColumn(name = "personne_id")
    private Personne personne;
    @OneToOne
    @JoinColumn(name = "parrain_id")
    private Parrain parrain;
    @OneToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;
    @OneToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public PasswordResetToken(String token, Personne personne,Agent agent,
                              Parrain parrain,Admin admin) {
        super();
        this.token = token;
        this.personne = personne;
        this.agent = agent;
        this.parrain = parrain;
        this.admin = admin;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public PasswordResetToken(String token, Personne personne) {
        super();
        this.token = token;
        this.personne = personne;
        this.expirationTime = this.getTokenExpirationTime();
    }
    public PasswordResetToken(String token, Parrain parrain) {
        super();
        this.token = token;
        this.parrain = parrain;
        this.expirationTime = this.getTokenExpirationTime();
    }
    public PasswordResetToken(String token, Admin admin) {
        super();
        this.token = token;
        this.admin = admin;
        this.expirationTime = this.getTokenExpirationTime();
    }
    public PasswordResetToken(String token, Agent agent) {
        super();
        this.token = token;
        this.agent = agent;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public PasswordResetToken(String token) {
        super();
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }

}
