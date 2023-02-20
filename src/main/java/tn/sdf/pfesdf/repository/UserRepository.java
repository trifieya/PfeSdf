package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.Personne;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Personne, Long> {
    //L'utilisation de l'interface Optional permet de gérer facilement les cas où aucun résultat n'est retourné lors de la recherche dans la base de données. Elle permet également de mieux gérer les exceptions qui peuvent survenir lors de l'exécution de la requête.
    Optional<Personne> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
