package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.Centre;
import tn.sdf.pfesdf.entities.Parrain;

@Repository
public interface CentreRepository extends JpaRepository<Centre, Long> {
}
