package tn.sdf.pfesdf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Centre;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.entities.Profil;
import tn.sdf.pfesdf.interfaces.IProfilService;
import tn.sdf.pfesdf.repository.CentreRepository;
import tn.sdf.pfesdf.repository.PersonneRepository;
import tn.sdf.pfesdf.repository.ProfilRepository;

import java.util.List;

@Service
public class ProfilServiceImpl  implements IProfilService {
    @Autowired
    ProfilRepository profilRepository;
    @Autowired
    CentreRepository centreRepository;
    @Autowired
    PersonneRepository personneRepository;
    @Override
    public List<Profil> retrieveAllProfils() {

        return profilRepository.findAll();
    }

    @Override
    public Profil updateProfil(Profil pro) {

        return profilRepository.save(pro);
    }

    @Override
    public Profil addProfil(Profil pro) {

        return profilRepository.save(pro);
    }

    @Override
    public Profil retrieveProfil(Long idProfil) {

        return profilRepository.findById(idProfil).orElse(null);
    }

    @Override
    public void removeProfil(Long idProfil) {
        profilRepository.deleteById(idProfil);

    }
    public void assignNearestCentre(Long profilId) {
        Profil profil = profilRepository.findById(profilId).orElseThrow(() -> new IllegalArgumentException("Invalid profil ID"));

        Personne personne = profil.getProfilpresonne();
        List<Centre> centres = centreRepository.findAll();

        Centre nearestCentre = null;
        Float minDistance = Float.MAX_VALUE;

        for (Centre centre : centres) {
            Float distance = calculateDistance(personne.getLogitude(), personne.getLatitude(), centre.getLongitude(), centre.getLatitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearestCentre = centre;
            }
        }

        personne.setCentre(nearestCentre);
        personneRepository.save(personne);
    }
    public float calculateDistance(float longitude1, float latitude1, float longitude2, float latitude2) {
        float earthRadius = 6371f; // Radius of the Earth in kilometers

        // Convert latitude and longitude to radians
        float lat1Rad = (float) Math.toRadians(latitude1);
        float lon1Rad = (float) Math.toRadians(longitude1);
        float lat2Rad = (float) Math.toRadians(latitude2);
        float lon2Rad = (float) Math.toRadians(longitude2);

        // Calculate the differences
        float latDiff = lat2Rad - lat1Rad;
        float lonDiff = lon2Rad - lon1Rad;

        // Calculate the Haversine distance
        float a = (float) (Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2));
        float c = 2 * (float) Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float distance = earthRadius * c;

        return distance;
    }

}
