package tn.sdf.pfesdf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.interfaces.IParrainService;
import tn.sdf.pfesdf.repository.ParrainRepository;

import java.util.List;

@Service
public class ParrainServiceImpl implements IParrainService {
    @Autowired
    ParrainRepository parrainRepository;
    @Override
    public List<Parrain> retrieveAllParrains() {
        return parrainRepository.findAll();
    }

    @Override
    public Parrain updateParrain(Parrain p) {
        return parrainRepository.save(p);
    }

    @Override
    public Parrain addParrain(Parrain p) {
        return parrainRepository.save(p);
    }

    @Override
    public Parrain retrieveParrain(Long idParrain) {
        return parrainRepository.findById(idParrain).orElse(null);
    }

    @Override
    public void removeParrain(Long idParrain) {
        parrainRepository.deleteById(idParrain);

    }
}
