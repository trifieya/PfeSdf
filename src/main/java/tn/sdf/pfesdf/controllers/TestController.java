package tn.sdf.pfesdf.controllers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* In TestController.java */
// @CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all") //without signin contnent can ve vizualize
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/personne")
    @PreAuthorize("hasRole('ROLE_PERSONNE')")
    public String personneAccess() {
        return "Person Content.";
    }

    @GetMapping("/agent")
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public String agentAccess() {
        return "Agent Board.";
    }

    @GetMapping("/parrain")
    @PreAuthorize("hasRole('ROLE_PARRAIN')")
    public String parrainAccess() {
        return "Parrain Board.";
    }
}
