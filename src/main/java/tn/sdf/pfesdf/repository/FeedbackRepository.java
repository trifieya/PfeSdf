package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.FeedBack;
import tn.sdf.pfesdf.entities.Profil;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedBack,Long> {
    //List<FeedBack>  findByProfilf(Profil profil);
    List<FeedBack>  findByProfilf(Profil profil);

    List<FeedBack> findByProfilfAndIdAgentNotNull(Profil profil);
    List<FeedBack> findByProfilfAndIdParrainNotNull(Profil profil);




}
