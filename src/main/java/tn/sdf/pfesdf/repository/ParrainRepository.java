package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;

import java.util.Optional;

@Repository
public interface ParrainRepository extends JpaRepository<Parrain, Long> {
    Optional<Parrain> findByUsername(String username);

    Boolean existsByUsername(String username);


    Boolean existsByEmail(String email);
    Optional<Parrain> findByEmail (String email);






}
