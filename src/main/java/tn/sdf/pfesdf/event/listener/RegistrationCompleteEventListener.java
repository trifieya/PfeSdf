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

    private Personne personne;

    public void sendPasswordResetVerificationEmail(Personne personne,String url) throws MessagingException, UnsupportedEncodingException {

            String subject = "Password Reset Request Verification";
            String senderName = "User Registration Portal Service";
            String mailContent = "";
        if(personne == null) {
            log.error("Personne object is null");
            return;
        }
            mailContent = "<p> Hi, " + personne.getEmail() + ", </p>" +
                    "<p><b>You recently requested to reset your password,</b>" + "" +
                    "Please, follow the link below to complete the action.</p>" +
                    "<a href=\"" + url + "\">Reset password</a>" +
                    "<p> Users Registration Portal Service";
            MimeMessage message = mailSender.createMimeMessage();
            var messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("eya.trifi.23@gmail.com", senderName);
            messageHelper.setTo(personne.getEmail());
            System.out.println("holaaaaa"+personne.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            mailSender.send(message);
            log.info("Email does not exist2"+personne.getEmail());
            //System.out.println("hhhhhhhhhh"+personne.getEmail());


    }





    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
      return;

    }
}
