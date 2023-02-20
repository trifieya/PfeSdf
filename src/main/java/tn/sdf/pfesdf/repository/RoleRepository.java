package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.sdf.pfesdf.entities.ERole;
import tn.sdf.pfesdf.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
