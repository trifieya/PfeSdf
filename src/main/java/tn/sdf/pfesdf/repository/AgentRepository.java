package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Personne;

import java.util.Optional;


public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
