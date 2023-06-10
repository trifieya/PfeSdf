package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.RendezVous;
@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
}
