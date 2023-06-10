package tn.sdf.pfesdf.interfaces;



import tn.sdf.pfesdf.entities.RendezVous;

import java.util.List;

public interface IRendezVousService {
    public List<RendezVous> retrieveAllRendezVous();

    public RendezVous updateRendezVous (RendezVous  rdv );

    public  RendezVous addRendezVous(RendezVous rdv);

    public RendezVous retrieveRendezVous (Long  idRdv);

    public  void removeRendezVous(Long idRdv);
}
