package tn.sdf.pfesdf.controllers;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.sdf.pfesdf.entities.*;
import tn.sdf.pfesdf.payload.request.LoginRequest;
import tn.sdf.pfesdf.payload.request.SignupRequest;
import tn.sdf.pfesdf.payload.response.MessageResponse;
import tn.sdf.pfesdf.payload.response.UserInfoResponse;
import tn.sdf.pfesdf.repository.AgentRepository;
import tn.sdf.pfesdf.repository.ParrainRepository;
import tn.sdf.pfesdf.repository.RoleRepository;
import tn.sdf.pfesdf.repository.PersonneRepository;
import tn.sdf.pfesdf.security.jwt.JwtUtils;
import tn.sdf.pfesdf.security.services.UserDetailsImpl;


/* In AuthController.java */
// @CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    ParrainRepository parrainRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        userDetails.getNom(),
                        userDetails.getPhoto(),
                        userDetails.getPrenom(),
                        userDetails.getAge(),
                        userDetails.getTrancheAge(),
                        userDetails.getPassword(),
                        userDetails.getGender(),
                        userDetails.getPhnum(),
                        userDetails.getCin(),
                        userDetails.getLogitude(),
                        userDetails.getLatitude(),
                        roles));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (personneRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Person Username is already taken!"));
        }
        if (agentRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Agent Username of  is already taken!"));
        }
        if (parrainRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Parrain Username is already taken!"));
        }

        if (personneRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Person Email is already in use!"));
        }
        if (agentRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Agent Email is already in use!"));
        }
        if (parrainRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Parrain Email is already in use!"));
        }
//        Role prsRole = roleRepository.findByName(ERole.ROLE_PERSONNE).orElse(null).;
//        Role parRole = roleRepository.findByName(ERole.ROLE_PARRAIN).orElse(null);
//        Role agRole = roleRepository.findByName(ERole.ROLE_AGENT).orElse(null);

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        // Create new user's account

        if (signUpRequest.getRole().contains("ROLE_PERSONNE")) {
            Personne personne = new Personne(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));
            Role personneRole = roleRepository.findByName(ERole.ROLE_PERSONNE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            personne.setRoles(Collections.singleton(personneRole));
            personneRepository.save(personne);
        } else if (signUpRequest.getRole().contains("ROLE_AGENT")) {
            Agent agent = new Agent(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));
            Role agentRole = roleRepository.findByName(ERole.ROLE_AGENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            agent.setRoles(Collections.singleton(agentRole));
            agentRepository.save(agent);
        } else if (signUpRequest.getRole().contains("ROLE_PARRAIN")) {
            Parrain parrain = new Parrain(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));
            Role parrainRole = roleRepository.findByName(ERole.ROLE_PARRAIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            parrain.setRoles(Collections.singleton(parrainRole));
            parrainRepository.save(parrain);
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}