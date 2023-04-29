package tn.sdf.pfesdf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.interfaces.IPersonneService;
import tn.sdf.pfesdf.repository.PersonneRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonneServiceImpl implements IPersonneService {
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<Personne> retrieveAllPersonnes() {
        return personneRepository.findAll();
    }

    @Override
    public Personne updatePersonne(Personne per) {
        return personneRepository.save(per);
    }

    @Override
    public Personne addPersonne(Personne per) {
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
        passwordResetTokenService.createPasswordResetTokenForPersonne(personne,passwordResetToken);

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

}
