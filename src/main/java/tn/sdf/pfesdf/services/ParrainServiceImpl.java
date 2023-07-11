package tn.sdf.pfesdf.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.entities.TrancheAge;
import tn.sdf.pfesdf.interfaces.IParrainService;
import tn.sdf.pfesdf.repository.ParrainRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParrainServiceImpl implements IParrainService {
    @Autowired
    ParrainRepository parrainRepository;

    private final JavaMailSender mailSender;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Parrain> retrieveAllParrains() {
        return parrainRepository.findAll();
    }

    /*@Override
    public Parrain updateParrain(Parrain p) {
        return parrainRepository.save(p);
    }*/



    @Override
    public Parrain updateParrain(Long idParrain, Parrain p) {
        return parrainRepository.save(p);
    }

    @Override
    public Parrain addParrain(Parrain p) {
        String password = p.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        p.setPassword(hashedPassword);
        return parrainRepository.save(p);
    }

    @Override
    public Parrain retrieveParrain(Long idParrain) {
        return parrainRepository.findById(idParrain).orElse(null);
    }

    @Override
    public void removeParrain(Long idParrain) {
        parrainRepository.deleteById(idParrain);

    }

    @Override
    public Parrain findUserByPasswordToken(String token) {
        return passwordResetTokenService.findParrainByPasswordToken(token).orElse(null);

    }

    @Override
    public void createPasswordResetTokenForParrain(Parrain parrain, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForParrain(parrain,passwordResetToken);

    }

    @Override
    public Optional<Parrain> findByEmail(String email) {
        return parrainRepository.findByEmail(email);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetTokenParrain(token);
    }

    @Override
    public void resetPassword(Parrain parrain, String newPassword) {
        parrain.setPassword(passwordEncoder.encode(newPassword));
        parrainRepository.save(parrain);
    }
    @Override
    public void calculateAgeAndSetTrancheAge(Parrain parrain, LocalDate age) {
        // Calculate age from date of birth
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(age, currentDate);
        int calculatedAge = period.getYears();

        // Set trancheAge based on age
        if (calculatedAge >= 15 && calculatedAge <= 20) {
            parrain.setTrancheAge(TrancheAge.QUINZ_VINGT);
        } else if (calculatedAge >= 21 && calculatedAge <= 25) {
            parrain.setTrancheAge(TrancheAge.VINGT_VINGT_CINQ);
        } else if (calculatedAge >= 26 && calculatedAge <= 30) {
            parrain.setTrancheAge(TrancheAge.VINGT_CINQ_TRENTE);
        } else if (calculatedAge >= 31 && calculatedAge <= 35) {
            parrain.setTrancheAge(TrancheAge.TRENTE_TRENTE_CINQ);
        } else if (calculatedAge >= 36 && calculatedAge <= 40) {
            parrain.setTrancheAge(TrancheAge.TRENTE_CINQ_QUARNTE);
        } else {
            // Handle the case when the age doesn't fit into any predefined range
        }

        // Save the personne entity
        parrainRepository.save(parrain);
    }


//    public Parrain getUserByEmail(String email){
//        return parrainRepository.findByEmail(email);
//    }

}
