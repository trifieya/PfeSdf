package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.sdf.pfesdf.entities.Admin;
import tn.sdf.pfesdf.entities.Agent;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

     Admin findByEmail(String email);
}
