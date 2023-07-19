package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.*;

import java.util.List;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
    List<Profil> findByProfilpresonneAgent(Agent agent);//c pour afficher les profil affecté à  un agent donné
    List<Profil> findByProfilpresonneParrain(Parrain parrain);//c pour afficher les profil affecté à  un parrain donné
    List<Profil> findByArchived(boolean archived);

    @Query("SELECT p.profilpresonne.parrain FROM Profil p WHERE p.idProfil = :profilId")
    Parrain findParrainByProfilId(@Param("profilId") Long profilId);

    @Query("SELECT p.profilpresonne.agent FROM Profil p WHERE p.idProfil = :profilId")
    Agent findAgentByProfilId(@Param("profilId") Long profilId);

    @Query("SELECT p.profilpresonne.centre FROM Profil p WHERE p.idProfil = :profilId")
    Centre findCentreByProfilId(@Param("profilId") Long profilId);

    @Query("SELECT p.profilpresonne.programmes FROM Profil p WHERE p.idProfil = :profilId")
    List<Programme> findProgrammeByProfilId(@Param("profilId") Long profilId);
}
