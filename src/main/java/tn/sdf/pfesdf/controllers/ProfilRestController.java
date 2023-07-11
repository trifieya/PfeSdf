package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Profil;
import tn.sdf.pfesdf.interfaces.IProfilService;


import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")

@RestController
@RequestMapping("/api/profil")
public class ProfilRestController {
    @Autowired
    IProfilService profilService;

    @GetMapping("/retrieve-all-profils")
    public List<Profil> getProfils() {
        List<Profil> listProfil = profilService.retrieveAllProfils();
        return listProfil;
    }
    @GetMapping("/retrieve-archived-profils")
    public List<Profil> getArchivedProfils() {
        List<Profil> listProfil = profilService.retrieveArchivedProfils();
        return listProfil;
    }
    @GetMapping("/profils-by-agent-parrain")
    public List<Profil> getProfilsByAgentorParrain() {
        List<Profil> listProfilByAgent = profilService.retrieveProfilsByAgentOrParrain();
        return listProfilByAgent;
    }
    @GetMapping("/retrieve-profil/{idProfil}")
    public Profil retrieveProfil(@PathVariable("idProfil") Long idProfil) {
        return profilService.retrieveProfil(idProfil);
    }


    @PostMapping("/add-Profil")
    public Profil addProfil(@RequestBody Profil pro) {
        Profil profil = profilService.addProfil(pro);
        return profil;
    }


    @DeleteMapping("/remove-profil/{idProfil}")
    public void removeProfil(@PathVariable("idProfil") Long idProfil) {

        profilService.removeProfil(idProfil);
    }

    @PutMapping("/update-profil")
    public Profil updateProfil(@RequestBody Profil pro) {
        Profil Profil= profilService.updateProfil(pro);
        return Profil;
    }

    @PostMapping("/{profilId}/assign-centre")
    public void assignNearestCentre(@PathVariable Long profilId) {
        profilService.assignNearestCentre(profilId);
    }
    @PostMapping("/{profilId}/assign-agent-disponible")
    public void assignrprofilagentdisponibilité(@PathVariable Long profilId) {
        profilService.assignrprofilagentdisponibilité(profilId);
    }
    @PostMapping("/{profilId}/assign-parrain-disponible")
    public void assignrprofilparraindisponibilité(@PathVariable Long profilId) {
        profilService.assignrprofilparraindisponibilité(profilId);
    }
    @PostMapping("/{profilId}/assign-parrain-adequat")
    public void assignprofilparrainadequat(@PathVariable Long profilId) {
        profilService.assignprofilparrainadequat(profilId);
    }
    @PostMapping("/{profilId}/assign-agent-adequat")
    public void assignprofilagentadequat(@PathVariable Long profilId) {
        profilService.assignprofilagentadequat(profilId);
    }
    @PostMapping("/{profilId}/assign-agent-proche")
    public void assignprofilagentproche(@PathVariable Long profilId) {
        profilService.assignprofilagentproche(profilId);
    }
    @PostMapping("/{profilId}/assign-parrain-proche")
    public void assignprofilparrainproche(@PathVariable Long profilId) {
        profilService.assignprofilparrainproche(profilId);
    }
    @PostMapping("/calculate-score/{profilId}")
    public int calculateScore(@PathVariable Long profilId) {

            int score = profilService.calculateScore(profilId);

        return score;
    }

    @PostMapping("/{personneId}/affectation")
    public ResponseEntity<String> affecterProgrammeSelonScore(@PathVariable Long personneId) {
        profilService.affecterProgrammeSelonScore(personneId);
        return ResponseEntity.ok("Affectation de programmes effectuée avec succès.");
    }

        @PostMapping("/{profilId}/archive")
        public ResponseEntity<Profil> archiveProfil(@PathVariable Long profilId) {
            Profil archivedProfil = profilService.archiveProfil(profilId);

            if (archivedProfil != null) {
                return ResponseEntity.ok(archivedProfil);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping("/{profilId}/unarchive")
        public ResponseEntity<Profil> unarchiveProfil(@PathVariable Long profilId) {
            Profil unarchivedProfil = profilService.unarchiveProfil(profilId);

            if (unarchivedProfil != null) {
                return ResponseEntity.ok(unarchivedProfil);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

    @GetMapping("/{profilId}/parrain")
    public ResponseEntity<Parrain> getProfilParrain(@PathVariable Long profilId) {
        Parrain parrain = profilService.getProfilParrain(profilId);
        if (parrain != null) {
            return ResponseEntity.ok(parrain);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{profilId}/agent")
    public ResponseEntity<Agent> getProfilAgent(@PathVariable Long profilId) {
        Agent agent = profilService.getProfilAgent(profilId);
        if (agent != null) {
            return ResponseEntity.ok(agent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
