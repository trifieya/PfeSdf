package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.interfaces.IParrainService;
import tn.sdf.pfesdf.repository.ParrainRepository;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")

@RestController
@RequestMapping("/api/parrain")
public class ParrainRestController {
    @Autowired
    IParrainService parrainService;

    @Autowired
    ParrainRepository parrainRepository;


    @GetMapping("/retrieve-all-parrains")
    //@PreAuthorize("hasRole('ROLE_PARRAIN')")
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

    @PutMapping("/update-parrain/{idParrain}")
    public ResponseEntity<Parrain> updateParrain(@PathVariable("idParrain")Long idParrain, @RequestBody Parrain p) {
        Optional<Parrain> parrainData = parrainRepository.findById(idParrain);

        if (parrainData.isPresent()) {
            Parrain _parrain = parrainData.get();
            _parrain.setUsername(p.getUsername());
            _parrain.setEmail(p.getEmail());
            _parrain.setPassword(p.getPassword());
            return new ResponseEntity<>(parrainRepository.save(_parrain), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   /* @PutMapping("/update-parrain")
    public Parrain editProduct(@RequestBody Parrain p) {
        return parrainService.updateParrain(p);
    }*/


}
