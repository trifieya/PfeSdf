package tn.sdf.pfesdf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.*;
import tn.sdf.pfesdf.repository.PasswordResetTokenRepository;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public void createPasswordResetTokenForPersonne(Personne personne,  String passwordToken ){
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, personne);
        passwordResetTokenRepository.save(passwordResetToken);
    }
    public void createPasswordResetTokenForAgent( Agent agent, String passwordToken ){
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, agent);
        passwordResetTokenRepository.save(passwordResetToken);
    }  public void createPasswordResetTokenForParrain(
                                                   Parrain parrain,String passwordToken ){
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, parrain);
        passwordResetTokenRepository.save(passwordResetToken);
    }  public void createPasswordResetTokenForAdmin( Admin admin, String passwordToken ){
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, admin);
        passwordResetTokenRepository.save(passwordResetToken);
    }


//public void createPasswordResetTokenForUser(Personne personne, Agent agent, Parrain parrain, Admin admin, String passwordToken) {
//    if (personne != null) {
//        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, personne);
//        passwordResetTokenRepository.save(passwordResetToken);
//    } else if (agent != null) {
//        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, agent);
//        passwordResetTokenRepository.save(passwordResetToken);
//    } else if (parrain != null) {
//        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, parrain);
//        passwordResetTokenRepository.save(passwordResetToken);
//    } else if (admin != null) {
//        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, admin);
//        passwordResetTokenRepository.save(passwordResetToken);
//    } else {
//        throw new IllegalArgumentException("At least one user entity must be provided.");
//    }
//}


    public String validatePasswordResetTokenPersonne(String theToken){
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
    public String validatePasswordResetTokenAdmin(String theToken){
        PasswordResetToken token = passwordResetTokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid password reset token";
        }


        Admin admin = token.getAdmin();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "link already expired, resend link";
        }

        return "valid";
    }
    public String validatePasswordResetTokenAgent(String theToken){
        PasswordResetToken token = passwordResetTokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid password reset token";
        }


        Agent agent = token.getAgent();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "link already expired, resend link";
        }

        return "valid";
    }
    public String validatePasswordResetTokenParrain(String theToken){
        PasswordResetToken token = passwordResetTokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid password reset token";
        }


        Parrain parrain = token.getParrain();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "link already expired, resend link";
        }

        return "valid";
    }
//public String validatePasswordResetToken(String theToken) {
//    PasswordResetToken token = passwordResetTokenRepository.findByToken(theToken);
//    if (token == null) {
//        return "Invalid password reset token";
//    }
//
//    if (token.getPersonne() != null) {
//        Personne personne = token.getPersonne();
//    } else if (token.getAgent() != null) {
//        Agent agent = token.getAgent();
//    } else if (token.getParrain() != null) {
//        Parrain parrain = token.getParrain();
//    } else if (token.getAdmin() != null) {
//        Admin admin = token.getAdmin();
//    }
//
//    Calendar calendar = Calendar.getInstance();
//    if ((token.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
//        return "link already expired, resend link";
//    }
//
//    return "valid";
//}


    public Optional<Personne> findPersoneByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getPersonne());
    }
    public Optional<Admin> findAdminByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getAdmin());
    }
    public Optional<Agent> findAgentByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getAgent());
    }
    public Optional<Parrain> findParrainByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getParrain());
    }



    public PasswordResetToken findPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }

}
