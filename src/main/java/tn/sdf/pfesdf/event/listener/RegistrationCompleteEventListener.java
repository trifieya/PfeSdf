package tn.sdf.pfesdf.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tn.sdf.pfesdf.entities.Admin;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.services.PersonneServiceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final JavaMailSender mailSender;

   // private Personne personne;

    public void sendPasswordResetVerificationEmailPersonne(Personne personne,String url) throws MessagingException, UnsupportedEncodingException {

        String subject = "Password Reset Request Verification";
        String senderName = "Home & Hope";
        String mailContent = "";
        if(personne == null) {
            log.error("Personne object is null");
            return;
        }

        mailContent = "<div style='font-family: Arial, sans-serif;'>" +
                "<div style='background-color: #F2F2F2; padding: 20px;'>" +
                "<h2 style='color: #008CBA;'>Password Reset Request Verification</h2>" +
                "<p>Hi, " + personne.getUsername() + ",</p>" +
                "<p>You recently requested to reset your password. Please follow the link below to complete the action:</p>" +
                "<p style='text-align: center;'><a href='" + url + "' style='background-color: #008CBA; color: #FFFFFF; padding: 10px 20px; border-radius: 5px; text-decoration: none;'>Reset Password</a></p>" +
                "<p>If you did not make this request, you can ignore this message and your password will remain unchanged.</p>" +
                "<p>Regards,</p>" +
                "<p>User Registration Portal Service of Home & Hope</p>" +
                "</div>" +
                "</div>";

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("eya.trifi.19@gmail.com", senderName);
        messageHelper.setTo(personne.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
        log.info("Email exist2" + personne.getEmail());


    }
    public void sendPasswordResetVerificationEmailAdmin(Admin admin, String url) throws MessagingException, UnsupportedEncodingException {

        String subject = "Password Reset Request Verification";
        String senderName = "Home & Hope";
        String mailContent = "";
        if(admin == null) {
            log.error("Admin object is null");
            return;
        }

        mailContent = "<div style='font-family: Arial, sans-serif;'>" +
                "<div style='background-color: #F2F2F2; padding: 20px;'>" +
                "<h2 style='color: #008CBA;'>Password Reset Request Verification</h2>" +
                "<p>Hi, " + admin.getUsername() + ",</p>" +
                "<p>You recently requested to reset your password. Please follow the link below to complete the action:</p>" +
                "<p style='text-align: center;'><a href='" + url + "' style='background-color: #008CBA; color: #FFFFFF; padding: 10px 20px; border-radius: 5px; text-decoration: none;'>Reset Password</a></p>" +
                "<p>If you did not make this request, you can ignore this message and your password will remain unchanged.</p>" +
                "<p>Regards,</p>" +
                "<p>User Registration Portal Service of Home & Hope</p>" +
                "</div>" +
                "</div>";

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("eya.trifi.19@gmail.com", senderName);
        messageHelper.setTo(admin.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
        log.info("Email exist2" + admin.getEmail());


    }
    public void sendPasswordResetVerificationEmailAgent(Agent agent, String url) throws MessagingException, UnsupportedEncodingException {

        String subject = "Password Reset Request Verification";
        String senderName = "Home & Hope";
        String mailContent = "";
        if(agent == null) {
            log.error("Agent object is null");
            return;
        }

        mailContent = "<div style='font-family: Arial, sans-serif;'>" +
                "<div style='background-color: #F2F2F2; padding: 20px;'>" +
                "<h2 style='color: #008CBA;'>Password Reset Request Verification</h2>" +
                "<p>Hi, " + agent.getUsername() + ",</p>" +
                "<p>You recently requested to reset your password. Please follow the link below to complete the action:</p>" +
                "<p style='text-align: center;'><a href='" + url + "' style='background-color: #008CBA; color: #FFFFFF; padding: 10px 20px; border-radius: 5px; text-decoration: none;'>Reset Password</a></p>" +
                "<p>If you did not make this request, you can ignore this message and your password will remain unchanged.</p>" +
                "<p>Regards,</p>" +
                "<p>User Registration Portal Service of Home & Hope</p>" +
                "</div>" +
                "</div>";

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("eya.trifi.19@gmail.com", senderName);
        messageHelper.setTo(agent.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
        log.info("Email exist2" + agent.getEmail());


    }
    public void sendPasswordResetVerificationEmailParrain(Parrain parrain, String url) throws MessagingException, UnsupportedEncodingException {

        String subject = "Password Reset Request Verification";
        String senderName = "Home & Hope";
        String mailContent = "";
        if(parrain == null) {
            log.error("Parrain object is null");
            return;
        }

        mailContent = "<div style='font-family: Arial, sans-serif;'>" +
                "<div style='background-color: #F2F2F2; padding: 20px;'>" +
                "<h2 style='color: #008CBA;'>Password Reset Request Verification</h2>" +
                "<p>Hi, " + parrain.getUsername() + ",</p>" +
                "<p>You recently requested to reset your password. Please follow the link below to complete the action:</p>" +
                "<p style='text-align: center;'><a href='" + url + "' style='background-color: #008CBA; color: #FFFFFF; padding: 10px 20px; border-radius: 5px; text-decoration: none;'>Reset Password</a></p>" +
                "<p>If you did not make this request, you can ignore this message and your password will remain unchanged.</p>" +
                "<p>Regards,</p>" +
                "<p>User Registration Portal Service of Home & Hope</p>" +
                "</div>" +
                "</div>";

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("eya.trifi.19@gmail.com", senderName);
        messageHelper.setTo(parrain.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
        log.info("Email exist2" + parrain.getEmail());


    }





    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
      return;

    }
}
