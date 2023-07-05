package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.entities.RendezVous;
import tn.sdf.pfesdf.interfaces.IRendezVousService;
import tn.sdf.pfesdf.repository.PersonneRepository;
import tn.sdf.pfesdf.repository.RendezVousRepository;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")

@RestController
@RequestMapping("/api/rdv")
public class RendezVousRestController {
    @Autowired
    IRendezVousService rendezVousService;
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    RendezVousRepository rendezVousRepository;

    @GetMapping("/retrieve-all-rendezVous")
    //@PreAuthorize("hasRole('ROLE_Profil')")
    public List<RendezVous> getlistRendezVousByAgent() {
        List<RendezVous> listrendezVous = rendezVousService.retrieveAllRendezVousbyAgent();
        return listrendezVous;
    }
    @GetMapping("/retrieve-all-rendezVous-For-admin/{idAgent}")
    //@PreAuthorize("hasRole('ROLE_Profil')")
    public List<RendezVous> getlistRendezVousByAgenForAdmint(@PathVariable("idAgent") Long idAgent) {
        List<RendezVous> listrendezVous = rendezVousService.retrieveAllRendezVousbyAgentForAdmin(idAgent);
        return listrendezVous;
    }
    @GetMapping("/retrieve-rendezVous/{idRdv}")
    public RendezVous retrieveRendezVous(@PathVariable("idRdv") Long idRdv) {
        return rendezVousService.retrieveRendezVous(idRdv);
    }

    @PostMapping("/add-rendezVous")
    public ResponseEntity<String> ajouterRendezvousPourProfil(@RequestBody RendezVous rendezvous) throws MessagingException, UnsupportedEncodingException {

        rendezVousService.addRendezVous(rendezvous);

        return ResponseEntity.ok("Rendez-vous ajouté avec succès");
    }

    @PostMapping("/check-overlap")
    public boolean checkRendezVousOverlap(@RequestBody RendezVous newRdv) {
       return rendezVousService.checkRendezVousOverlap(newRdv);
    }


    @DeleteMapping("/remove-rendezVous/{idRdv}")
    public void removeRendezVous(@PathVariable("idRdv") Long idRdv) {

        rendezVousService.removeRendezVous(idRdv);
    }

//    @PutMapping("/update-rendezVous/{idRdv}")
//    public RendezVous updateRendezVous(@PathVariable("idRdv") Long idRdv, @RequestBody RendezVous rdv) {
//        RendezVous rendezVous= rendezVousService.updateRendezVous(rdv);
//        return rendezVous;
//    }

    @PutMapping("/update-rdv/{idRdv}")
    public ResponseEntity<RendezVous> updateRendezVous(@PathVariable("idRdv")Long idrdv, @RequestBody RendezVous rdv) {
        Optional<RendezVous> RendezVousData =rendezVousRepository.findById(idrdv);

        if (RendezVousData.isPresent()) {
            RendezVous _rendezVous = RendezVousData.get();
            _rendezVous.setDateRdv(rdv.getDateRdv());
            _rendezVous.setDateFinRdv(rdv.getDateFinRdv());
            _rendezVous.setProfilrdv(rdv.getProfilrdv());
            _rendezVous.setConclu(rdv.getConclu());

            return new ResponseEntity<>(rendezVousRepository.save(_rendezVous), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
