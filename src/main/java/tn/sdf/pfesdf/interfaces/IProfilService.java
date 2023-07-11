package tn.sdf.pfesdf.interfaces;
import org.springframework.http.ResponseEntity;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Profil;

import java.util.List;

public interface IProfilService {

    public List<Profil> retrieveAllProfils();
    public List<Profil> retrieveProfilsByAgentOrParrain();

    public Profil updateProfil (Profil  pro );

    public  Profil addProfil(Profil pro);

    public Profil retrieveProfil (Long  idProfil);
    public List<Profil> retrieveArchivedProfils();

    public  void removeProfil(Long idProfil);
    public void assignNearestCentre(Long profilId);
    public double calculateDistance(double longitude1, double latitude1, double longitude2, double latitude2) ;
    public ResponseEntity<Agent> assignrprofilagentdisponibilité(Long idProfil);
    public void assignrprofilparraindisponibilité(Long idProfil);
    public void assignprofilparrainadequat(Long idProfil);
    public void assignprofilagentadequat(Long idProfil);
    public void assignprofilparrainproche(Long idProfil);
    public void assignprofilagentproche(Long idProfil);
    public int calculateScore(Long profilId) ;
    public void affecterProgrammeSelonScore(Long personneId);
    public Profil archiveProfil(Long profilId);
    public Profil unarchiveProfil(Long profilId);
    public Parrain getProfilParrain(Long profilId);
    public Agent getProfilAgent(Long profilId);




    }
