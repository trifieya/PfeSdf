package tn.sdf.pfesdf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Programme;
import tn.sdf.pfesdf.interfaces.IProgrammeService;
import tn.sdf.pfesdf.repository.ProgrammeRepository;

import java.util.List;

@Service
public class ProgrammeServiceImpl implements IProgrammeService {
    @Autowired
    ProgrammeRepository programmeRepository;
    @Override
    public List<Programme> retrieveAllProgrammes() {
        return programmeRepository.findAll();
    }

    @Override
    public Programme updateProgramme(Programme prog) {
        return programmeRepository.save(prog);
    }

    @Override
    public Programme addProgramme(Programme prog) {
        return programmeRepository.save(prog);
    }

    @Override
    public Programme retrieveProgramme(Long idProgramme) {
        return programmeRepository.findById(idProgramme).orElse(null);
    }

    @Override
    public void removeProgramme(Long idProgramme) {
        programmeRepository.deleteById(idProgramme);

    }
}
