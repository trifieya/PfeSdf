package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.Delegation;
@Repository
public interface DelegationRepository  extends JpaRepository<Delegation,Long> {
}
