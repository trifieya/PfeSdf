package tn.sdf.pfesdf.interfaces;

import tn.sdf.pfesdf.entities.Centre;

import java.util.List;

public interface ICentreService {
    public List<Centre> retrieveAllCentres();

    public Centre updateCentre (Centre  c );

    public  Centre addCentre(Centre c);

    public Centre retrieveCentre (Long  idCentre);

    public  void removeCentre(Long idCentre);
}
