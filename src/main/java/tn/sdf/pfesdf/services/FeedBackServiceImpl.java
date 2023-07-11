package tn.sdf.pfesdf.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.FeedBack;
import tn.sdf.pfesdf.entities.Profil;
import tn.sdf.pfesdf.entities.RendezVous;
import tn.sdf.pfesdf.interfaces.IFeedbackService;
import tn.sdf.pfesdf.repository.AgentRepository;
import tn.sdf.pfesdf.repository.FeedbackRepository;
import tn.sdf.pfesdf.security.services.UserDetailsImpl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
@Slf4j
@Service
public class FeedBackServiceImpl implements IFeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    AgentRepository agentRepository;
    @Override
    public List<FeedBack> retrieveAllFeedBacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public FeedBack updateFeedBack(FeedBack feedBack) {
        return feedbackRepository.save(feedBack);
    }

    @Override
    public FeedBack addFeedBack(FeedBack feedBack) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long connectedperson = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        LocalDate currentDate = LocalDate.now();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_AGENT"))) {
            feedBack.setIdAgent(connectedperson);
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_PARRAIN"))) {
            feedBack.setIdParrain(connectedperson);
        } else {
            log.info("L'utilisateur connecté n'a pas le rôle d'agent ou parrain !");
        }

        feedBack.setDateajoutFeed(currentDate);
        Profil profil = feedBack.getProfilf();
        List<FeedBack> existingFeedbacks = feedbackRepository.findByProfilf(profil);

        boolean agentFeedbackExists = false;
        boolean parrainFeedbackExists = false;

        for (FeedBack existingFeedback : existingFeedbacks) {
            LocalDate feedbackDate = existingFeedback.getDateajoutFeed();
            if (feedbackDate.getYear() == currentDate.getYear()) {
                if (existingFeedback.getIdAgent() != null) {
                    agentFeedbackExists = true;
                }
                if (existingFeedback.getIdParrain() != null) {
                    parrainFeedbackExists = true;
                }
            }
        }

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_AGENT")) && agentFeedbackExists) {
            throw new RuntimeException("Vous avez déjà ajouté un feedback en tant qu'agent pour ce profil cette année !");
        }

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_PARRAIN")) && parrainFeedbackExists) {
            throw new RuntimeException("Vous avez déjà ajouté un feedback en tant que parrain pour ce profil cette année !");
        }

        return feedbackRepository.save(feedBack);
    }



    @Override
    public FeedBack retrieveFeedBack(Long idFeedBack) {
        return feedbackRepository.findById(idFeedBack).orElse(null);
    }

    @Override
    public void removeFeedBack(Long idFeedBack) {
        feedbackRepository.deleteById(idFeedBack);

    }
@Override
    public List<FeedBack> getFeedbacksByAgentNotNull(Profil profil) {
        return feedbackRepository.findByProfilfAndIdAgentNotNull(profil);
    }
    @Override
    public List<FeedBack> getFeedbacksByParraintNotNull(Profil profil) {
        return feedbackRepository.findByProfilfAndIdParrainNotNull( profil);
    }
}
