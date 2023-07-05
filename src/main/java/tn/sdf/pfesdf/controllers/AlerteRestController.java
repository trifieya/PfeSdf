package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.Alerte;
import tn.sdf.pfesdf.interfaces.IAlerteService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/alerte")
@RestController
public class AlerteRestController {
    @Autowired
    IAlerteService alerteService;

    @GetMapping("/retrieve-all-alertes")
    public List<Alerte> getAlertes() {
        List<Alerte> listAlertes = alerteService.retrieveAllAlertes();
        return listAlertes;
    }
    @GetMapping("/retrieve-Alertes/{idAlerte}")
    public Alerte retrieveAlerte(@PathVariable("idAlerte") Long idAlerte) {
        return alerteService.retrieveAlerte(idAlerte);
    }


    @PostMapping("/add-Alerte")
    public Alerte addAlerte(@RequestBody Alerte al) {
        Alerte alerte = alerteService.addAlerte(al);
        return alerte;
    }


    @DeleteMapping("/remove-Alerte/{idAlerte}")
    public void removeAlerte(@PathVariable("idAlerte") Long idAlerte) {

        alerteService.removeAlerte(idAlerte);
    }
    @PutMapping("/update-Alerte")
    public Alerte updateAlerte(@RequestBody Alerte al) {
        Alerte alerte= alerteService.updateAlerte(al);
        return alerte;
    }

    @PostMapping("/enregistrer")
    public void enregistrerCoordonnees(@RequestParam Long idAlerte,
                                       @RequestParam Double latitude,
                                       @RequestParam Double longitude) {
        alerteService.enregistrerCoordonnees(idAlerte, latitude, longitude);
    }

    @PostMapping("/traite")
    public void updatetraite(@RequestParam Long idAlerte,
                                       @RequestParam Boolean traite
                                      ) {
        alerteService.updatetraite(idAlerte, traite);
    }


}
