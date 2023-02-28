package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;

import java.util.Optional;


public interface ParrainRepository extends JpaRepository<Parrain, Long> {
    Optional<Parrain> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
