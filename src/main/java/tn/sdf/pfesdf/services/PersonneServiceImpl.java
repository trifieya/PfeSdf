package tn.sdf.pfesdf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.interfaces.IPersonneService;
import tn.sdf.pfesdf.repository.PersonneRepository;

import java.util.List;

@Service
public class PersonneServiceImpl implements IPersonneService {
    @Autowired
    PersonneRepository personneRepository;
    @Override
    public List<Personne> retrieveAllPersonnes() {
        return personneRepository.findAll();
    }

    @Override
    public Personne updatePersonne(Personne per) {
        return personneRepository.save(per);
    }

    @Override
    public Personne addPersonne(Personne per) {
        return personneRepository.save(per);
    }

    @Override
    public Personne retrievePersonne(Long idPersonne) {
        return personneRepository.findById(idPersonne).orElse(null);
    }

    @Override
    public void removePersonne(Long idPersonne) {
        personneRepository.deleteById(idPersonne);

    }
}
