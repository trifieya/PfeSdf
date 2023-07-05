package tn.sdf.pfesdf.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Document;
import tn.sdf.pfesdf.entities.Profil;
import tn.sdf.pfesdf.interfaces.IDocumentService;
import tn.sdf.pfesdf.repository.DocumentRepository;
import tn.sdf.pfesdf.repository.ProfilRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Slf4j
@Service
public class DocumentServiceImpl implements IDocumentService {
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    ProfilRepository profilRepository;
    @Override
    public List<Document> retrieveAllDocuments() {
        return documentRepository.findAll();
    }



    @Override
    public Document updateDocument(Document d) {
        Set<Profil> profilSet = d.getProfildoc();
        if (profilSet != null && !profilSet.isEmpty()) {
            Profil profile = profilSet.iterator().next(); // Retrieve the first Profil object from the set
            Set<Document> documents = profile.getDocuments();
            documents.add(d);
            return documentRepository.save(d);
        } else {
            throw new IllegalArgumentException("No Profil object found for the document. Cannot update.");
        }
    }



    @Override
    public Document addDocument(Document d) {
        return documentRepository.save(d);
    }

    @Override
    public Document retrieveDocument(Long idDoc) {
        return documentRepository.findById(idDoc).orElse(null);
    }

    @Override
    public void removeDocument(Long idDoc) {
        documentRepository.deleteById(idDoc);

    }

    @Override
    public List<Document> retrieveDocumentsByProfil(Long idProfil) {
        return documentRepository.findByProfildocIdProfil(idProfil);
    }
}
