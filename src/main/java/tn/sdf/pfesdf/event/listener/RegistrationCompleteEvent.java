package tn.sdf.pfesdf.event.listener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import tn.sdf.pfesdf.entities.Personne;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private  Personne personne;
    private String applicationUrl;

    public RegistrationCompleteEvent(Personne personne, String applicationUrl) {
        super(personne);
        this.personne = personne;
        this.applicationUrl = applicationUrl;
    }


}
