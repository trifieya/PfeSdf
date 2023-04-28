package tn.sdf.pfesdf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.PasswordResetToken;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.repository.PasswordResetTokenRepository;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public void createPasswordResetTokenForUser(Personne personne, String passwordToken ){
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, personne);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public String validatePasswordResetToken(String theToken){
        PasswordResetToken token = passwordResetTokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid password reset token";
        }
        Personne personne = token.getPersonne();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "link already expired, resend link";
        }

        return "valid";
    }

    public Optional<Personne> findUserByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getPersonne());
    }

    public PasswordResetToken findPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }

}
