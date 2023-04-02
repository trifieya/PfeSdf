package tn.sdf.pfesdf.interfaces;

import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;

import java.util.List;

public interface IParrainService {
    public List<Parrain> retrieveAllParrains();

    public Parrain updateParrain (Parrain  p);

    public  Parrain addParrain(Parrain p);

    public Parrain retrieveParrain (Long  idParrain);

    public  void removeParrain (Long idParrain);

}
