package tn.sdf.pfesdf.interfaces;

import tn.sdf.pfesdf.entities.Programme;

import java.util.List;

public interface IProgrammeService {
    
    public List<Programme> retrieveAllProgrammes();

    public Programme updateProgramme (Programme  prog );

    public  Programme addProgramme(Programme prog);

    public Programme retrieveProgramme (Long  idProgramme);

    public  void removeProgramme(Long idProgramme);
}
