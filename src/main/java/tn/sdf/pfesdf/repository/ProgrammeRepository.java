package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.Frequence;
import tn.sdf.pfesdf.entities.Programme;

import java.util.List;
import java.util.Set;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme,Long> {

    List<Programme> findByFrequence(Frequence frequence);
}
