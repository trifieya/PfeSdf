package tn.sdf.pfesdf.interfaces;
import org.springframework.http.ResponseEntity;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Profil;

import java.util.List;

public interface IProfilService {

    public List<Profil> retrieveAllProfils();
    public List<Profil> retrieveProfilsByAgent();

    public Profil updateProfil (Profil  pro );

    public  Profil addProfil(Profil pro);

    public Profil retrieveProfil (Long  idProfil);

    public  void removeProfil(Long idProfil);
    public void assignNearestCentre(Long profilId);
    public float calculateDistance(float longitude1, float latitude1, float longitude2, float latitude2);
    public ResponseEntity<Agent> assignrprofilagentdisponibilité(Long idProfil);
    public void assignrprofilparraindisponibilité(Long idProfil);
    public void assignprofilparrainadequat(Long idProfil);
    public void assignprofilagentadequat(Long idProfil);
    public void assignprofilparrainproche(Long idProfil);
    public void assignprofilagentproche(Long idProfil);


    }
