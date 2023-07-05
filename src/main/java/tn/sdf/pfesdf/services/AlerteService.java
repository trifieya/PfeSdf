package tn.sdf.pfesdf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Alerte;
import tn.sdf.pfesdf.entities.Centre;
import tn.sdf.pfesdf.interfaces.IAlerteService;
import tn.sdf.pfesdf.repository.AlerteRepository;

import java.time.LocalDate;
import java.util.List;
@Service
public class AlerteService implements IAlerteService {
    @Autowired
    AlerteRepository alerteRepository;
    @Override
    public List<Alerte> retrieveAllAlertes() {
        return alerteRepository.findAll() ;
    }

    @Override
    public Alerte updateAlerte(Alerte al) {
        return alerteRepository.save(al);
    }

    @Override
    public Alerte addAlerte(Alerte al) {
        al.setDatealerte(LocalDate.now());
        return alerteRepository.save(al);
    }

    @Override
    public Alerte retrieveAlerte(Long idAlerte) {
        return alerteRepository.findById(idAlerte).orElse(null);
    }

    @Override
    public void removeAlerte(Long idAlerte) {
        alerteRepository.deleteById(idAlerte);
    }
    @Override
    public void enregistrerCoordonnees(Long idAlerte,Double latitude, Double longitude) {

        Alerte alerte = alerteRepository.findById(idAlerte).orElse(null);
        alerte.setLatitude(latitude);
        alerte.setLogitude(longitude);
        alerteRepository.save(alerte);
    }

    public void updatetraite(Long idAlerte, Boolean traite){
        Alerte alerte = alerteRepository.findById(idAlerte).orElse(null);
        alerte.setTraite(traite);
        alerteRepository.save(alerte);

    }

}
