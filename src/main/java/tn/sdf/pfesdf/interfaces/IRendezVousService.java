package tn.sdf.pfesdf.interfaces;



import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.entities.RendezVous;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IRendezVousService {
    public List<RendezVous> retrieveAllRendezVousbyAgent();

    public RendezVous updateRendezVous(Long idrdv, RendezVous rdv);

    public  RendezVous addRendezVous(RendezVous rdv) throws MessagingException, UnsupportedEncodingException;

    public RendezVous retrieveRendezVous (Long  idRdv);

    public  void removeRendezVous(Long idRdv);
    public boolean checkRendezVousOverlap(RendezVous newRdv);
    public List<RendezVous> retrieveAllRendezVousbyAgentForAdmin(Long idAgent);
}
