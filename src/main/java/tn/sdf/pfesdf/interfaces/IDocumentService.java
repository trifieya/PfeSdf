package tn.sdf.pfesdf.interfaces;

import tn.sdf.pfesdf.entities.Document;

import java.util.List;

public interface IDocumentService {
    public List<Document> retrieveAllDocuments();

    public Document updateDocument (Document d );

    public  Document addDocument(Document d);

    public Document retrieveDocument (Long  idDoc);

    public  void removeDocument(Long idDoc);
    public List<Document> retrieveDocumentsByProfil(Long idProfil);
}
