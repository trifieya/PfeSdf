package tn.sdf.pfesdf.interfaces;


import tn.sdf.pfesdf.entities.Alerte;

import java.util.List;

public interface IAlerteService {
    public List<Alerte> retrieveAllAlertes();

    public Alerte updateAlerte(Alerte al);

    public Alerte addAlerte(Alerte al);

    public Alerte retrieveAlerte(Long idAlerte);

    public void removeAlerte(Long idAlerte);

    public void enregistrerCoordonnees(Long idAlerte, Double latitude, Double longitude);
    public void updatetraite(Long idAlerte, Boolean traite);

    }


