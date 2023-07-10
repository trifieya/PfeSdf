package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.Programme;
import tn.sdf.pfesdf.interfaces.IProgrammeService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/programme")

public class ProgrammeRestController {
    @Autowired
    IProgrammeService programmeService;

    @GetMapping("/retrieve-all-Programmes")
    //@PreAuthorize("hasRole('ROLE_Programme')")
    public List<Programme> getProgrammes() {
        List<Programme> listProgramme = programmeService.retrieveAllProgrammes();
        return listProgramme;
    }
    @GetMapping("/retrieve-Programme/{idProgramme}")
    public Programme retrieveProgramme(@PathVariable("idProgramme") Long idProgramme) {
        return programmeService.retrieveProgramme(idProgramme);
    }

    @PostMapping("/add-Programme")
    public Programme addProgramme(@RequestBody Programme prog) {
        Programme Programme = programmeService.addProgramme(prog);
        return Programme;
    }


    @DeleteMapping("/remove-Programme/{idProgramme}")
    public void removeProgramme(@PathVariable("idProgramme") Long idProgramme) {

        programmeService.removeProgramme(idProgramme);
    }

    @PutMapping("/update-Programme")
    public Programme updateProgramme(@RequestBody Programme prog) {
        Programme Programme= programmeService.updateProgramme(prog);
        return Programme;
    }
}
