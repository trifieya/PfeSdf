package tn.sdf.pfesdf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Centre;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.interfaces.ICentreService;
import tn.sdf.pfesdf.repository.CentreRepository;

import java.util.List;

@Service
public class CentreServiceImpl implements ICentreService {
    @Autowired
    CentreRepository centreRepository;
    @Override
    public List<Centre> retrieveAllCentres() {
        return centreRepository.findAll();
    }

    @Override
    public Centre updateCentre(Centre c) {
        return centreRepository.save(c);
    }

    @Override
    public Centre addCentre(Centre c) {
        Centre centre = centreRepository.save(c);
        return centre;
    }

    @Override
    public Centre retrieveCentre(Long idCentre) {
        return centreRepository.findById(idCentre).orElse(null);
    }

    @Override
    public void removeCentre(Long idCentre) {
        centreRepository.deleteById(idCentre);

    }
    @Override
    public void enregistrerCoordonnees(Long idCentre,Double latitude, Double longitude) {

        Centre centre = centreRepository.findById(idCentre).orElse(null);
        centre.setLatitude(latitude);
        centre.setLongitude(longitude);
        centreRepository.save(centre);
    }
}
