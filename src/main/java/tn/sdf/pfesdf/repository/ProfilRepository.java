package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.entities.Profil;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
}
