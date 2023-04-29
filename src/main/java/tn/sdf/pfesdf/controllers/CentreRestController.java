package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.Centre;
import tn.sdf.pfesdf.interfaces.ICentreService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")

@RestController
@RequestMapping("/api/centre")
public class CentreRestController {
    @Autowired
    ICentreService centreService;
    
    @GetMapping("/retrieve-all-Centres")
    //@PreAuthorize("hasRole('ROLE_Centre')")
    public List<Centre> getCentres() {
        List<Centre> listCentre = centreService.retrieveAllCentres();
        return listCentre;
    }
    @GetMapping("/retrieve-Centre/{idCentre}")
    public Centre retrieveCentre(@PathVariable("idCentre") Long idCentre) {
        return centreService.retrieveCentre(idCentre);
    }


    @PostMapping("/add-Centre")
    public Centre addCentre(@RequestBody Centre c) {
        Centre centre = centreService.addCentre(c);
        return centre;
    }


    @DeleteMapping("/remove-Centre/{idCentre}")
    public void removeCentre(@PathVariable("idCentre") Long idCentre) {

        centreService.removeCentre(idCentre);
    }
}
