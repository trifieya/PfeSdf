package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.sdf.pfesdf.entities.Delegation;
import tn.sdf.pfesdf.interfaces.IDelegationService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/delegation")
public class DelegationRestController {
    @Autowired
    IDelegationService delegationService;

    @GetMapping("/retrieve-all-Delegations")
    //@PreAuthorize("hasRole('ROLE_Delegation')")
    public List<Delegation> getDelegations() {
        List<Delegation> listDelegation = delegationService.retrieveAllDelegations();
        return listDelegation;
    }
}
