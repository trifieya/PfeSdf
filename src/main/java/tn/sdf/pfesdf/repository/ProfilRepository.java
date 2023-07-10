package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.entities.Profil;

import java.util.List;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
    List<Profil> findByProfilpresonneAgent(Agent agent);//c pour afficher les profil affecté à  un agent donné
    List<Profil> findByProfilpresonneParrain(Parrain parrain);//c pour afficher les profil affecté à  un parrain donné

}
