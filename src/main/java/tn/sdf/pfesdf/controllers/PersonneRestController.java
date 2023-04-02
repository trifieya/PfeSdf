package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.interfaces.IPersonneService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/personne")
public class PersonneRestController {
    @Autowired
    IPersonneService personneService;

    @GetMapping("/retrieve-all-personnes")
    public List<Personne> getPersonnes() {
        List<Personne> listPersonne = personneService.retrieveAllPersonnes();
        return listPersonne;
    }
    @GetMapping("/retrieve-personne/{idPersonne}")
    public Personne retrievePersonne(@PathVariable("idPersonne") Long idPersonne) {
        return personneService.retrievePersonne(idPersonne);
    }


    @PostMapping("/add-personne")
    public Personne addPersonne(@RequestBody Personne per) {
        Personne personne = personneService.addPersonne(per);
        return personne;
    }


    @DeleteMapping("/remove-personne/{idPersonne}")
    public void removePersonne(@PathVariable("idPersonne") Long idPersonne) {

        personneService.removePersonne(idPersonne);
    }

    @PutMapping("/update-personne")
    public Personne updatePersonne(@RequestBody Personne per) {
        Personne personne= personneService.updatePersonne(per);
        return personne;
    }
}
