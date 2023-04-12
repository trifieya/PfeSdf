package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.interfaces.IParrainService;

import java.util.List;
//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")

@RestController
@RequestMapping("/api/parrain")
public class ParrainRestController {
    @Autowired
    IParrainService parrainService;


    @GetMapping("/retrieve-all-parrains")
    @PreAuthorize("hasRole('ROLE_PARRAIN')")
    public List<Parrain> getParrains() {
        List<Parrain> listParrain = parrainService.retrieveAllParrains();
        return listParrain;
    }
    @GetMapping("/retrieve-Parrain/{idParrain}")
    public Parrain retrieveParrain(@PathVariable("idParrain") Long idParrain) {
        return parrainService.retrieveParrain(idParrain);
    }


    @PostMapping("/add-parrain")
    public Parrain addParrain(@RequestBody Parrain p) {
        Parrain parrain = parrainService.addParrain(p);
        return parrain;
    }


    @DeleteMapping("/remove-parrain/{idParrain}")
    public void removeParrain(@PathVariable("idParrain") Long idParrain) {

        parrainService.removeParrain(idParrain);
    }

    @PutMapping("/update-parrain")
    public Parrain updateParrain(@RequestBody Parrain p) {
        Parrain parrain= parrainService.updateParrain(p);
        return parrain;
    }
}
