package tn.sdf.pfesdf.interfaces;

import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Personne;

import java.util.List;

public interface IPersonneService {
    public List<Personne> retrieveAllPersonnes();

    public Personne updatePersonne (Personne  per);

    public  Personne addPersonne(Personne per);

    public Personne retrievePersonne (Long  idPersonne);

    public  void removePersonne(Long idPersonne);
}
