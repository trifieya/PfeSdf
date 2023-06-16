package tn.sdf.pfesdf.services;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.entities.Profil;
import tn.sdf.pfesdf.entities.RendezVous;
import tn.sdf.pfesdf.interfaces.IRendezVousService;
import tn.sdf.pfesdf.repository.AgentRepository;
import tn.sdf.pfesdf.repository.PersonneRepository;
import tn.sdf.pfesdf.repository.RendezVousRepository;
import tn.sdf.pfesdf.security.services.UserDetailsImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
@Slf4j
@Service
public class RendezVousServiceImpl implements IRendezVousService {
    @Autowired
    RendezVousRepository rendezVousRepository;
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public List<RendezVous> retrieveAllRendezVousbyAgent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long agentId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_AGENT"))) {
            return rendezVousRepository.findByAgent_IdAgent(agentId);
        }

        return Collections.emptyList(); // Retourner une liste vide si l'utilisateur n'a pas le rôle d'agent
    }


    @Override
    public RendezVous updateRendezVous(Long idrdv, RendezVous rdv) {
        RendezVous existingRdv = rendezVousRepository.findById(rdv.getIdRdv())
                .orElseThrow(() -> new IllegalArgumentException("Invalid rendezVous id: " + rdv.getIdRdv()));
        existingRdv.setDateRdv(rdv.getDateRdv());
        existingRdv.setDateFinRdv(rdv.getDateFinRdv());
        existingRdv.setProfilrdv(rdv.getProfilrdv());
        existingRdv.setConclu(rdv.getConclu());
        return rendezVousRepository.save(existingRdv);
    }

    public boolean checkRendezVousOverlap(RendezVous newRdv) {
        // Récupérer l'ID de l'agent connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long agentId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        // Vérifier les rendez-vous qui se chevauchent
        List<RendezVous> overlappingRdvs = rendezVousRepository.findOverlappingRendezVous(agentId, newRdv.getDateRdv(), newRdv.getDateFinRdv());

        // Vérifier s'il y a des rendez-vous qui se chevauchent
        return !overlappingRdvs.isEmpty();
    }


    @Override
    public RendezVous addRendezVous( RendezVous rdv) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long agentId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        Agent connectedAgent = agentRepository.findById(agentId).orElse(null);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_AGENT"))) {
            if (checkRendezVousOverlap(rdv)) {
                throw new RuntimeException("Le rendez-vous se chevauche avec un autre rendez-vous existant !");
            }

                rdv.setAgent(connectedAgent);

            RendezVous savedRdv = rendezVousRepository.save(rdv);
            sendNotificationEmail(savedRdv);
            return savedRdv;
        }
        else{
            throw new RuntimeException("L'utilisateur connecté n'a pas le rôle d'agent !");
        }
        }
//melowl kont hata addrendezvous w mailing fard method ama lmochekla eno add rendezvous tabta tab9a tstana email bch yetb3ath bch yetzed rendezvous donc aamlt separée w zedt @async to enable asynchronous execution. This means that when you call this method, it will be executed in a separate thread, allowing the saving of the appointment to proceed without waiting for the email to be sent.
    @Async
    public void sendNotificationEmail(RendezVous rdv) {
        try {
            String prenomNomAgent = rdv.getAgent().getPrenom() + " " + rdv.getAgent().getNom();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = rdv.getDateRdv().format(formatter);
            String time = rdv.getDateRdv().format(DateTimeFormatter.ofPattern("HH:mm"));
            String dateFin = rdv.getDateFinRdv().format(formatter);
            String timeFin = rdv.getDateFinRdv().format(DateTimeFormatter.ofPattern("HH:mm"));
            String subject = "Rendez-vous avec votre agent sur terrain";
            String senderName = "Home & Hope";
            String mailContent = "<html><body"
                    + "<h2 style='color: lightseagreen;'>Rendez-vous avec votre agent sur terrain</h2>"
                    + "<p>Cher(e) jeune,</p>"
                    + "<p>Nous avons le plaisir de vous informer que votre agent sur terrain, <strong>" + prenomNomAgent +
                    "</strong>, a planifié un rendez-vous avec vous.</p>"
                    + "<p>Date : <strong>" + date + "</strong></p>"
                    + "<p>Heure : <strong>" + time + "</strong> jusqu'à <strong>" + timeFin + "</strong></p>"
                    + "<p>Cordialement,<br>L'équipe Home & Hope</p>"
                    + "</body></html>";



            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("eya.trifi.19@gmail.com", senderName);
            messageHelper.setTo(rdv.getProfilrdv().getProfilpresonne().getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            javaMailSender.send(message);
            log.info("Email sent: " + rdv.getProfilrdv().getProfilpresonne().getEmail());
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to send email notification: " + e.getMessage());
        }
    }


    @Async
    public void sendNotificationEmailDelete(RendezVous rdv) {
        try {
            String prenomNomAgent = rdv.getAgent().getPrenom() + " " + rdv.getAgent().getNom();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = rdv.getDateRdv().format(formatter);
            String time = rdv.getDateRdv().format(DateTimeFormatter.ofPattern("HH:mm"));
            String dateFin = rdv.getDateFinRdv().format(formatter);
            String timeFin = rdv.getDateFinRdv().format(DateTimeFormatter.ofPattern("HH:mm"));
            String subject = "Annulation du Rendez-vous avec votre agent sur terrain";
            String senderName = "Home & Hope";
            String mailContent = "<html><body"
                    + "<p>Cher(e) jeune,</p>"
                    + "<p>Nous tenons par le présent mail à vous informer que votre agent sur terrain, <strong>" + prenomNomAgent +
                    "</strong>, a annulé votre rendez-vous prévu.</p>"
                    + "<p> Le : <strong>" + date + "</strong></p>"
                    + "<p> À : <strong>" + time + "</strong> jusqu'à <strong>" + timeFin + "</strong></p>"
                    + "<p>Cordialement,<br>L'équipe Home & Hope</p>"
                    + "</body></html>";



            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("eya.trifi.19@gmail.com", senderName);
            messageHelper.setTo(rdv.getProfilrdv().getProfilpresonne().getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            javaMailSender.send(message);
            log.info("Email sent: " + rdv.getProfilrdv().getProfilpresonne().getEmail());
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to send email notification: " + e.getMessage());
        }
    }




    @Override
    public RendezVous retrieveRendezVous(Long idRdv) {
        return rendezVousRepository.findById(idRdv).orElse(null);
    }

    @Override
    public void removeRendezVous(Long idRdv) {
        RendezVous rdv= rendezVousRepository.findById(idRdv).orElse(null);
        sendNotificationEmailDelete(rdv);
        rendezVousRepository.deleteById(idRdv);


    }
}
