package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    //@PreAuthorize("hasRole('ROLE_Profil')")
    public List<Profil> getProfils() {
        List<Profil> listProfil = profilService.retrieveAllProfils();
        return listProfil;
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

}
