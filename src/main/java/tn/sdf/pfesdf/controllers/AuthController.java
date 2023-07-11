package tn.sdf.pfesdf.controllers;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.configuration.SecurityUtility;
import tn.sdf.pfesdf.entities.*;
import tn.sdf.pfesdf.event.listener.RegistrationCompleteEventListener;
import tn.sdf.pfesdf.payload.request.LoginRequest;
import tn.sdf.pfesdf.payload.request.SignupRequest;
import tn.sdf.pfesdf.payload.response.MessageResponse;
import tn.sdf.pfesdf.payload.response.UserInfoResponse;
import tn.sdf.pfesdf.repository.*;
import tn.sdf.pfesdf.security.jwt.JwtUtils;
import tn.sdf.pfesdf.security.services.UserDetailsImpl;
import tn.sdf.pfesdf.services.AdminServiceImpl;
import tn.sdf.pfesdf.services.AgentServiceImpl;
import tn.sdf.pfesdf.services.ParrainServiceImpl;
import tn.sdf.pfesdf.services.PersonneServiceImpl;


/* In AuthController.java */
// @CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor

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
    AdminRepository adminRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ParrainServiceImpl parrainService;
    @Autowired
    PersonneServiceImpl personneService;
    @Autowired
    AgentServiceImpl agentService;
    @Autowired
    AdminServiceImpl adminService;
    @Autowired
    RegistrationCompleteEventListener eventListener;
    @Autowired
    DocumentRepository documentRepository;



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails.isBloque()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Votre compte a été bloqué. Veuillez contacter l'administrateur.");
        }
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
        if (adminRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Admin Username is already taken!"));
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
        if (adminRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Admin Email is already in use!"));
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
            personne.setDate_ajout(LocalDate.now());
            Profil profile = new Profil();
            personne.setProfil(profile);
            profile.setProfilpresonne(personne);
           // Set<Document> documentSet = new HashSet<>();
            //

            //



            Role personneRole = roleRepository.findByName(ERole.ROLE_PERSONNE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            personne.setRoles(Collections.singleton(personneRole));
            personneRepository.save(personne);
            Document document1 = new Document();
            Document document2= new Document();
            document1.setTypedocument(TypeDocument.B3);
            document2.setTypedocument(TypeDocument.ANTECEDENT_PSYCHIATRIQUE);
            //profile.setDocuments(documentSet);
            Set<Profil> profileSet = new HashSet<>();
            profileSet.add(profile);
            document1.setProfildoc(profileSet);
            document2.setProfildoc(profileSet);

            documentRepository.save(document1);
            documentRepository.save(document2);

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
        else if (signUpRequest.getRole().contains("ROLE_ADMIN")) {
            Admin admin = new Admin(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            admin.setRoles(Collections.singleton(adminRole));
            adminRepository.save(admin);
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest,
                                       final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {

        Optional<Personne> personne = personneService.findByEmail(passwordResetRequest.getEmail());
        Optional<Admin> admin = adminService.findByEmail(passwordResetRequest.getEmail());
        Optional<Agent> agent = agentService.findByEmail(passwordResetRequest.getEmail());
        Optional<Parrain> parrain = parrainService.findByEmail(passwordResetRequest.getEmail());
        String passwordResetUrl = "";
        if (personne.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            personneService.createPasswordResetTokenForPersonne(personne.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLinkPersonne(personne.get(), applicationUrl(servletRequest), passwordResetToken);
        }
        if (admin.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            adminService.createPasswordResetTokenForAdmin(admin.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLinkAdmin(admin.get(), applicationUrl(servletRequest), passwordResetToken);
        }
        if (agent.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            agentService.createPasswordResetTokenForAgent(agent.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLinkAgent(agent.get(), applicationUrl(servletRequest), passwordResetToken);
        }
        if (parrain.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            parrainService.createPasswordResetTokenForParrain(parrain.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLinkParrain(parrain.get(), applicationUrl(servletRequest), passwordResetToken);
        }

        return passwordResetUrl;

    }


    private String passwordResetEmailLinkPersonne(Personne user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {


        String   url = applicationUrl + "/api/auth/reset-password?token=" + passwordToken;

            eventListener.sendPasswordResetVerificationEmailPersonne(user,url);
            log.info("Click the link to reset your password :  {}", url);


        return url;
    }
    private String passwordResetEmailLinkAdmin(Admin user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {


        String   url = applicationUrl + "/api/auth/reset-password?token=" + passwordToken;

        eventListener.sendPasswordResetVerificationEmailAdmin(user,url);
        log.info("Click the link to reset your password :  {}", url);


        return url;
    }
    private String passwordResetEmailLinkAgent(Agent user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {


        String   url = applicationUrl + "/api/auth/reset-password?token=" + passwordToken;

        eventListener.sendPasswordResetVerificationEmailAgent(user,url);
        log.info("Click the link to reset your password :  {}", url);


        return url;
    }
    private String passwordResetEmailLinkParrain(Parrain user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {


        String   url = applicationUrl + "/api/auth/reset-password?token=" + passwordToken;

        eventListener.sendPasswordResetVerificationEmailParrain(user,url);
        log.info("Click the link to reset your password :  {}", url);


        return url;
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest passwordResetRequest,
                                @RequestParam("token") String token){
        String tokenVerificationResult1 = personneService.validatePasswordResetToken(token);
        String tokenVerificationResult2 = parrainService.validatePasswordResetToken(token);
        String tokenVerificationResult3 = agentService.validatePasswordResetToken(token);
        String tokenVerificationResult4 = adminService.validatePasswordResetToken(token);

        //Personne
        if (!tokenVerificationResult1.equalsIgnoreCase("valid")) {
            return "Invalid token password reset token";
        }
        Optional<Personne> personne = Optional.ofNullable(personneService.findUserByPasswordToken(token));
        if (personne.isPresent()) {
            personneService.resetPassword(personne.get(), passwordResetRequest.getNewPassword());
            return "Password has been reset successfully";
        }
        //Parrain

        if (!tokenVerificationResult2.equalsIgnoreCase("valid")) {
            return "Invalid token password reset token";
        }
        Optional<Parrain> parrain = Optional.ofNullable(parrainService.findUserByPasswordToken(token));
        if (parrain.isPresent()) {
            parrainService.resetPassword(parrain.get(), passwordResetRequest.getNewPassword());
            return "Password has been reset successfully";
        }
        //Agent

        if (!tokenVerificationResult3.equalsIgnoreCase("valid")) {
            return "Invalid token password reset token";
        }
        Optional<Agent> agent = Optional.ofNullable(agentService.findUserByPasswordToken(token));
        if (agent.isPresent()) {
            agentService.resetPassword(agent.get(), passwordResetRequest.getNewPassword());
            return "Password has been reset successfully";
        }

        //Admin

        if (!tokenVerificationResult4.equalsIgnoreCase("valid")) {
            return "Invalid token password reset token";
        }
        Optional<Admin> admin = Optional.ofNullable(adminService.findUserByPasswordToken(token));
        if (admin.isPresent()) {
            adminService.resetPassword(admin.get(), passwordResetRequest.getNewPassword());
            return "Password has been reset successfully";
        }


        return "Invalid password reset token";
    }


    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath();
    }

    @PutMapping("/{id}/bloquer")
    public ResponseEntity<?> bloquerUtilisateur(@PathVariable("id") Long utilisateurId) {
        // Récupérer l'utilisateur à partir de son ID
        Optional<Personne> personneOptional = personneRepository.findById(utilisateurId);
        Optional<Parrain> parrainOptional = parrainRepository.findById(utilisateurId);
        Optional<Agent> agentOptional = agentRepository.findById(utilisateurId);

        if (personneOptional.isPresent()) {
            Personne personne = personneOptional.get();
            personne.setBloque(true);
            personneRepository.save(personne);
        } else if (parrainOptional.isPresent()) {
            Parrain parrain = parrainOptional.get();
            parrain.setBloque(true);
            parrainRepository.save(parrain);
        } else if (agentOptional.isPresent()) {
            Agent agent = agentOptional.get();
            agent.setBloque(true);
            agentRepository.save(agent);
        } else {
            // L'utilisateur n'a pas été trouvé
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/debloquer")
    public ResponseEntity<?> debloquerUtilisateur(@PathVariable("id") Long utilisateurId) {
        // Récupérer l'utilisateur à partir de son ID
        Optional<Personne> personneOptional = personneRepository.findById(utilisateurId);
        Optional<Parrain> parrainOptional = parrainRepository.findById(utilisateurId);
        Optional<Agent> agentOptional = agentRepository.findById(utilisateurId);

        if (personneOptional.isPresent()) {
            Personne personne = personneOptional.get();
            personne.setBloque(false);
            personneRepository.save(personne);
        } else if (parrainOptional.isPresent()) {
            Parrain parrain = parrainOptional.get();
            parrain.setBloque(false);
            parrainRepository.save(parrain);
        } else if (agentOptional.isPresent()) {
            Agent agent = agentOptional.get();
            agent.setBloque(false);
            agentRepository.save(agent);
        } else {
            // L'utilisateur n'a pas été trouvé
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }



}