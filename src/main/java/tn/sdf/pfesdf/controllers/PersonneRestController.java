package tn.sdf.pfesdf.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import tn.sdf.pfesdf.entities.*;
import tn.sdf.pfesdf.interfaces.IPersonneService;
import tn.sdf.pfesdf.repository.PersonneRepository;
import tn.sdf.pfesdf.services.FTPServiceImp;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@AllArgsConstructor
@RestController
@RequestMapping("/api/personne")
public class PersonneRestController {
    @Autowired
    IPersonneService personneService;
    @Autowired
    PersonneRepository personneRepository;

    //FTPServiceImp ftpServiceImp;

    @GetMapping("/retrieve-all-personnes")
    //@PreAuthorize("hasRole('ROLE_PERSONNE')")
    public List<Personne> getPersonnes() {
        List<Personne> listPersonne = personneService.retrieveAllPersonnes();
        return listPersonne;
    }
    @GetMapping("/retrieve-personne")
    public Object retrievePersonne() {
        return personneService.retrievePersonne();
    }


    @PostMapping("/add-personne")
    public Personne addPersonne(@RequestBody Personne per) {
        Personne personne = personneService.addPersonne(per);
        return personne;
    }
    @GetMapping("/get-coordinates")
    public ResponseEntity<Double[]> getUserCoordinates() {
        try {
            Double[] coordinates = personneService.getUserCoordinates();
            return ResponseEntity.ok(coordinates);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/remove-personne/{idPersonne}")
    public void removePersonne(@PathVariable("idPersonne") Long idPersonne) {

        personneService.removePersonne(idPersonne);
    }

    @PutMapping("/update-personne/{idPersonne}")
    public ResponseEntity <Personne> updatePersonne(@PathVariable("idPersonne")Long idPersonne,@RequestBody Personne p) {
        Optional<Personne> personneData = personneRepository.findById(idPersonne);
        if (personneData.isPresent()) {
            Personne _personne = personneData.get();
            _personne.setUsername(p.getUsername());
            _personne.setEmail(p.getEmail());
            _personne.setPassword(p.getPassword());
            _personne.setNom(p.getNom());


            return new ResponseEntity<>(personneRepository.save(_personne), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




    @PostMapping("/upload/{idPersonne}")
    public ResponseEntity<Personne> uploadFile(@RequestParam("file") MultipartFile file,
                                               @PathVariable Long idPersonne) {
        Personne personne = personneService.addFile(file, idPersonne);
        if (personne == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personne);
    }
    @DeleteMapping("/deletefile/{idPersonne}/file")
    public void uploadFile(@PathVariable Long idPersonne) {
         personneService.removeFile(idPersonne);

    }

//    @PostMapping("/enregistrer")
//    public void enregistrerCoordonnees(
//                                       @RequestParam Long id,
//                                       @RequestParam Float latitude,
//                                       @RequestParam Float longitude) {
//        personneService.enregistrerCoordonnees(id,latitude, longitude);
//    }
    @GetMapping("/current")
    //@PreAuthorize("hasRole('ROLE_PERSONNE')")
    public Object getCurrentUser(){

        return personneService.getCurrentUser() ;
    }


//    @GetMapping("/personnes/{id}/photo")
//    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
//        String photoUrl = personneService.getPhotoUrl(id);
//        if (photoUrl == null) {
//            return ResponseEntity.notFound().build();
//        }
//        byte[] photoBytes = ftpServiceImp.getFileBytes(photoUrl);
//        if (photoBytes == null) {
//            return ResponseEntity.notFound().build();
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        headers.setContentLength(photoBytes.length);
//        return new ResponseEntity<>(photoBytes, headers, HttpStatus.OK);
//    }

    @PostMapping("/change-location")
    public ResponseEntity<String> changeLocation(@RequestParam Double newLongitude, @RequestParam Double newLatitude) {
        personneService.changeLocation(newLongitude, newLatitude);
        return ResponseEntity.ok("Longitude et latitude modifiées avec succès");
    }
//    @PutMapping("/update-personne/{idPersonne}")
//    public ResponseEntity<String> editProfile() {
//        personneService.editProfile();
//        return ResponseEntity.ok("USER modifiées avec succès");
//    }


    @PutMapping("/editprofileAgent")
    public void editprofileForAgent(@RequestParam("age") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate age,
                            @RequestParam("username") String username,
                            @RequestParam("email") String email,
                            @RequestParam("nom") String nom,
                            @RequestParam("prenom") String prenom,
                            @RequestParam("delegation") String delegationId,
                            @RequestParam("cin") Integer cin,
                            @RequestParam("discipline") Discipline discipline,
                            @RequestParam("phnum") Integer phnum,
                            @RequestParam("gender") Gender gender,
                            @RequestParam ("idCentre")Long idCentre){

        personneService.editprofileForAgent(username, email, nom, prenom, age, delegationId, cin, discipline, phnum, gender,idCentre);
    }

    @PutMapping("/editprofile")
    public void editProfile(@RequestParam("age") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate age,
                            @RequestParam("username") String username,
                            @RequestParam("email") String email,
                            @RequestParam("nom") String nom,
                            @RequestParam("prenom") String prenom,
                            @RequestParam("delegation") String delegationId,
                            @RequestParam("cin") Integer cin,
                            @RequestParam("discipline") Discipline discipline,
                            @RequestParam("phnum") Integer phnum,
                            @RequestParam("gender") Gender gender) {

        personneService.editprofile(username, email, nom, prenom, age, delegationId, cin, discipline, phnum, gender);
    }





}
