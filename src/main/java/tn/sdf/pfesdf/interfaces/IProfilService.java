package tn.sdf.pfesdf.interfaces;
import tn.sdf.pfesdf.entities.Profil;

import java.util.List;

public interface IProfilService {

    public List<Profil> retrieveAllProfils();

    public Profil updateProfil (Profil  pro );

    public  Profil addProfil(Profil pro);

    public Profil retrieveProfil (Long  idProfil);

    public  void removeProfil(Long idProfil);
}
