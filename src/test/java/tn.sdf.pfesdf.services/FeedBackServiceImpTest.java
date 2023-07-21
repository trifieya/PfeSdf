package tn.sdf.pfesdf.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.FeedBack;
import tn.sdf.pfesdf.entities.Profil;
import tn.sdf.pfesdf.repository.FeedbackRepository;
import tn.sdf.pfesdf.repository.ProfilRepository;
import tn.sdf.pfesdf.security.services.UserDetailsImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static tn.sdf.pfesdf.entities.TypeFeedback.*;


@SpringBootTest
public class FeedBackServiceImpTest {

    FeedBack savedFeedBack;

    @Autowired
    FeedBackServiceImpl feedBackService;
    @Autowired
    ProfilRepository profilRepository;
    @Autowired
    FeedbackRepository feedbackRepository;

    // Create SDF and persist in the database
    // Create Agent and persist in the database
    // Create feedback of the Agent to this SDF



    @BeforeEach
    public void setup() {
        // Set up your mock authentication here if needed
       // UserDetailsImpl userDetails = new UserDetailsImpl(2L, "oumayma", "12345678", Collections.singleton(new SimpleGrantedAuthority("ROLE_AGENT")));
     //   Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "dummy_credentials");
       // SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Test
    @WithMockUser(username = "seif", authorities = "ROLE_AGENT")
    public void testAddFeedBack() {
    // persist feedbacks already created
        // create expectedResult (here in case of we have already a feedback of the agent
        // call method AddFeedBack to add annother feedback from the agent
        // result expected here is to have the msg
        // throw new RuntimeException("Vous avez déjà ajouté un feedback en tant qu'agent pour ce profil cette année !");
        // Assert that the expectedResult and findResult are the same
        // clean database
        Profil existingProfil = profilRepository.findById(1L).orElse(null); // Replace 1L with the specific ID of an existing Profil in the database
        //Assertions.assertNotNull(existingProfil, "Profil with ID 1 should exist in the database.");

        FeedBack feedback = new FeedBack();
        feedback.setProfilf(existingProfil); // Set required properties

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            feedBackService.addFeedBack(feedback);
        }, "Vous avez déjà ajouté un feedback en tant qu'agent pour ce profil cette année !");
    }



/*
    @Test
    @WithMockUser(username = "oumayma", authorities = "ROLE_AGENT")
    void testAddFeedbackAsAgent_Success() {
        // Arrange
        Long connectedPersonId = 2L;
        Profil existingProfil = profilRepository.findById(3L).orElse(null);
        FeedBack feedback = new FeedBack();
        feedback.setIdAgent(connectedPersonId);
        feedback.setDateajoutFeed(LocalDate.now());
        feedback.setProfilf(existingProfil);

        List<FeedBack> existingFeedbacks = new ArrayList<>();
        existingFeedbacks.add(feedback);

        when(feedbackRepository.findByProfilf(any(Profil.class))).thenReturn(existingFeedbacks); // Use any() matcher

        // Act
        FeedBack result = feedBackService.addFeedBack(feedback);

        // Assert
        Assertions.assertTrue(feedback.equals(result));

        // Optionally, you can use verify to check interactions
        verify(feedbackRepository, times(1)).findByProfilf(any(Profil.class));
        verify(feedbackRepository, times(1)).save(any(FeedBack.class));
        feedBackService.removeFeedBack(feedback.getIdFeedback());
    }
*/


    @AfterEach
    public void cleanUp() {

    }
}
