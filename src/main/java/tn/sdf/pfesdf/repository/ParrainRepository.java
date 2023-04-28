package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;

import java.util.Optional;


public interface ParrainRepository extends JpaRepository<Parrain, Long> {
    Optional<Parrain> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    Parrain findParrainByResettoken(String token);
    @Query("Select p from Parrain p where p.username= :urname")
    public Parrain findOneByUsername(@Param("urname")String urname );

   // Parrain findByEmail(String email);
    Parrain findByEmail(String email);

}
