package tn.sdf.pfesdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sdf.pfesdf.entities.Document;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {
    //List<Document> findDocumentByProfildocIdProfil(Long idProfil);
    List<Document> findByProfildocIdProfil(Long idProfil);

}
