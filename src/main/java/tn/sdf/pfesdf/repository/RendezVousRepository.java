package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.RendezVous;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByAgent_IdAgent(Long agentId);
    //verifier s'il ya des rendezvous qui se chavauchent
    @Query("SELECT r FROM RendezVous r WHERE r.agent.idAgent = :agentId " +
            "AND (r.dateRdv BETWEEN :startDate AND :endDate OR r.dateFinRdv BETWEEN :startDate AND :endDate)")
    List<RendezVous> findOverlappingRendezVous(@Param("agentId") Long agentId,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);
}
