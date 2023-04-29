package tn.sdf.pfesdf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Profil;
import tn.sdf.pfesdf.interfaces.IProfilService;
import tn.sdf.pfesdf.repository.ProfilRepository;

import java.util.List;

@Service
public class ProfilServiceImpl  implements IProfilService {
    @Autowired
    ProfilRepository profilRepository;
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
}
