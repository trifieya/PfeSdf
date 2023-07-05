package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.Document;
import tn.sdf.pfesdf.interfaces.IDocumentService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/document")
public class DocumentRestController {
    @Autowired
    IDocumentService documentService;


    @GetMapping("/retrieve-all-Documents")
    //@PreAuthorize("hasRole('ROLE_Document')")
    public List<Document> getDocuments() {
        List<Document> listDocument = documentService.retrieveAllDocuments();
        return listDocument;
    }
    @GetMapping("/documents/{idProfil}")
    //@PreAuthorize("hasRole('ROLE_Document')")
    public List<Document> getDocumentsByProfil(@PathVariable("idProfil") Long idProfil) {

        return documentService.retrieveDocumentsByProfil(idProfil);
    }
    @GetMapping("/retrieve-Document/{idDocument}")
    public Document retrieveDocument(@PathVariable("idDocument") Long idDocument) {
        return documentService.retrieveDocument(idDocument);
    }


    @PostMapping("/add-Document")
    public Document addDocument(@RequestBody Document c) {
        Document Document = documentService.addDocument(c);
        return Document;
    }


    @DeleteMapping("/remove-Document/{idDocument}")
    public void removeDocument(@PathVariable("idDocument") Long idDocument) {

        documentService.removeDocument(idDocument);
    }

    @PutMapping("/update-Document")
    public Document updateDocument(@RequestBody Document c) {
        Document Document= documentService.updateDocument(c);
        return Document;
    }
}
