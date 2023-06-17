package tn.sdf.pfesdf.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.entities.TrancheAge;
import tn.sdf.pfesdf.interfaces.IAgentService;
import tn.sdf.pfesdf.interfaces.IParrainService;
import tn.sdf.pfesdf.interfaces.IPersonneService;
import tn.sdf.pfesdf.repository.AgentRepository;
import tn.sdf.pfesdf.repository.ParrainRepository;
import tn.sdf.pfesdf.repository.PersonneRepository;
import tn.sdf.pfesdf.security.services.UserDetailsImpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class PersonneServiceImpl implements IPersonneService {
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    ParrainRepository parrainRepository;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    IParrainService parrainService;
    @Autowired
    IAgentService agentService;
    @Autowired
    PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Personne> retrieveAllPersonnes() {
        return personneRepository.findAll();
    }

    @Override
    public Personne updatePersonne(Long idPersonne, Personne p) {
        return personneRepository.save(p);
    }


    @Override
    public Personne addPersonne(Personne per) {
        String password = per.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        per.setPassword(hashedPassword);
        return personneRepository.save(per);
    }


    @Override
    public Personne retrievePersonne(Long idPersonne) {
        return personneRepository.findById(idPersonne).orElse(null);
    }

    @Override
    public void removePersonne(Long idPersonne) {
        personneRepository.deleteById(idPersonne);

    }

    @Override
    public Optional<Personne> findByEmail(String email) {
        return personneRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForPersonne(Personne personne, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForPersonne(personne, passwordResetToken);

    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetTokenPersonne(token);

    }

    @Override
    public Personne findUserByPasswordToken(String token) {
        return passwordResetTokenService.findPersoneByPasswordToken(token).orElse(null);
    }

    @Override
    public void resetPassword(Personne personne, String newPassword) {
        personne.setPassword(passwordEncoder.encode(newPassword));
        personneRepository.save(personne);
    }

    @Override
    public Personne addFile(MultipartFile file, Long idPersonne) {
        Personne personne = personneRepository.findById(idPersonne).orElse(null);
        if (personne == null) return null;
        try {
            removeFile(idPersonne);
            personne.setPhoto("ftp://eya@127.0.0.1/profile" + personne.getIdPersonne() + "/photoProfil/" + file.getOriginalFilename());
            personne = personneRepository.save(personne);
            FTPServiceImp.uFileUpload(file, "photoProfil", idPersonne);
            return personne;
        } catch (Exception e) {
            System.out.println("Error Uploading file");
        }
        return null;
    }

    public String getPhotoUrl(Long idPersonne) {
        Personne personne = personneRepository.findById(idPersonne).orElse(null);
        if (personne == null || personne.getPhoto() == null) {
            return null;
        }
        return personne.getPhoto();
    }

    @Override
    public void enregistrerCoordonnees(Long id, Float latitude, Float longitude) {

    }


    @Override
    public void removeFile(Long idPersonne) {
        Personne personne = personneRepository.findById(idPersonne).orElse(null);
        if (!(personne == null)) {
            personne.setPhoto(null);
            try {
                FTPServiceImp.uFileremove(personne.getPhoto(), "photoProfil", personne.getIdPersonne());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

//    @Override
//    public void enregistrerCoordonnees(Long idPersonne,Long idParrain,Long idAgent,Float latitude, Float longitude) {
//
//        Personne personne = personneRepository.findById(idPersonne).orElse(null);
//        Agent agent = agentRepository.findById(idAgent).orElse(null);
//        Parrain parrain = parrainRepository.findById(idParrain).orElse(null);
//        if(personne!=null){
//        personne.setLatitude(latitude);
//        personne.setLogitude(longitude);
//        personneRepository.save(personne);
//        }  if (agent!=null) {
//            agent.setLatitude(latitude);
//            agent.setLogitude(longitude);
//            agentRepository.save(agent);
//
//        }  if (parrain!=null) {
//            parrain.setLatitude(latitude);
//            parrain.setLogitude(longitude);
//            parrainRepository.save(parrain);
//        }
//        else {
//            log.info("no id found");
//        }
//
//    }


// ...

    public Object getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            Object principal = authentication.getPrincipal();

            return principal;
        }

        // Gérer le cas où l'utilisateur n'est pas authentifié ou n'est pas une instance des classes utilisateur attendues
        // Vous pouvez retourner null ou lancer une exception selon vos besoins.
        // Par exemple :
        throw new RuntimeException("Utilisateur actuel non trouvé ou n'est pas une instance des classes utilisateur attendues.");
    }


    @Override
    public void changeLocation(Float newLongitude, Float newLatitude) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_AGENT"))) {
            Optional<Agent> agentOptional = agentRepository.findByUsername(username);
            if (agentOptional.isPresent()) {
                Agent agent = agentOptional.get();
                agent.setLogitude(newLongitude);
                agent.setLatitude(newLatitude);
                agentRepository.save(agent);
            } else {
                throw new NotFoundException("Agent non trouvé");
                // Gérer le cas où l'agent n'est pas trouvé
            }
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_PARRAIN"))) {
            Optional<Parrain> parrainOptional = parrainRepository.findByUsername(username);
            if (parrainOptional.isPresent()) {
                Parrain parrain = parrainOptional.get();
                parrain.setLogitude(newLongitude);
                parrain.setLatitude(newLatitude);
                parrainRepository.save(parrain);
            } else {
                throw new NotFoundException("Parrain non trouvé");
                // Gérer le cas où le parrain n'est pas trouvé
            }
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_PERSONNE"))) {
            Optional<Personne> personneOptional = personneRepository.findByUsername(username);
            if (personneOptional.isPresent()) {
                Personne personne = personneOptional.get();
                personne.setLogitude(newLongitude);
                personne.setLatitude(newLatitude);
                personneRepository.save(personne);
            } else {
                throw new NotFoundException("Personne non trouvée");
                // Gérer le cas où la personne n'est pas trouvée
            }
        }
    }

    @Override
    public void editprofile(LocalDate age) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_AGENT"))) {
            Optional<Agent> agentOptional = agentRepository.findByUsername(username);
            if (agentOptional.isPresent()) {
                Agent agent = agentOptional.get();
                if(agent.getAge()==null) {
                    // Update agent properties here

                    // Calculate age and set trancheAge
                    agent.setAge(age);
                    agent.setCin(agent.getCin());
                    agentService.calculateAgeAndSetTrancheAge(agent, age);

                    agentRepository.save(agent);
                }
            } else {
                throw new NotFoundException("Agent non trouvé");
            }
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_PARRAIN"))) {
            Optional<Parrain> parrainOptional = parrainRepository.findByUsername(username);
            if (parrainOptional.isPresent()) {
                Parrain parrain = parrainOptional.get();
                // Update parrain properties here
                parrain.setAge(age);

                // Calculate age and set trancheAge
                parrainService.calculateAgeAndSetTrancheAge(parrain,age);

                parrainRepository.save(parrain);
            } else {
                throw new NotFoundException("Parrain non trouvé");
            }
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_PERSONNE"))) {
            Optional<Personne> personneOptional = personneRepository.findByUsername(username);
            if (personneOptional.isPresent()) {
                Personne personne = personneOptional.get();
                // Update personne properties here
                personne.setAge(age);

                // Calculate age and set trancheAge
                calculateAgeAndSetTrancheAge(personne,age);

                personneRepository.save(personne);
            } else {
                throw new NotFoundException("Personne non trouvée");
            }
        }
    }
@Override
    public void calculateAgeAndSetTrancheAge(Personne personne, LocalDate age) {
        // Calculate age from date of birth
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(age, currentDate);
        int calculatedAge = period.getYears();

        // Set trancheAge based on age
        if (calculatedAge >= 15 && calculatedAge <= 20) {
            personne.setTrancheAge(TrancheAge.QUINZ_VINGT);
        } else if (calculatedAge >= 21 && calculatedAge <= 25) {
            personne.setTrancheAge(TrancheAge.VINGT_VINGT_CINQ);
        } else if (calculatedAge >= 26 && calculatedAge <= 30) {
            personne.setTrancheAge(TrancheAge.VINGT_CINQ_TRENTE);
        } else if (calculatedAge >= 31 && calculatedAge <= 35) {
            personne.setTrancheAge(TrancheAge.TRENTE_TRENTE_CINQ);
        } else if (calculatedAge >= 36 && calculatedAge <= 40) {
            personne.setTrancheAge(TrancheAge.TRENTE_CINQ_QUARNTE);
        } else {
            // Handle the case when the age doesn't fit into any predefined range
        }

        // Save the personne entity
        personneRepository.save(personne);
    }

    // ...


}