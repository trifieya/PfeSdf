package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.Alerte;
@Repository
public interface AlerteRepository extends JpaRepository<Alerte,Long> {
}
