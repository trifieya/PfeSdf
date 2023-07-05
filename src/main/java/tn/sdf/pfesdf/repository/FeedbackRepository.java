package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.FeedBack;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedBack,Long> {
}
