package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.sdf.pfesdf.entities.PasswordResetToken;
import tn.sdf.pfesdf.entities.Personne;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String theToken);
}
