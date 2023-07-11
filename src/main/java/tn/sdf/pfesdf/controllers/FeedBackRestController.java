package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.FeedBack;
import tn.sdf.pfesdf.entities.Profil;
import tn.sdf.pfesdf.interfaces.IFeedbackService;
import tn.sdf.pfesdf.repository.ProfilRepository;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/feedback")
@RestController
public class FeedBackRestController {
    @Autowired
    IFeedbackService feedbackService;
    @Autowired
    ProfilRepository profilRepository;
    @GetMapping("/retrieve-all-FeedBacks")
    public List<FeedBack> getFeedBacks() {
        List<FeedBack> listFeedBacks = feedbackService.retrieveAllFeedBacks();
        return listFeedBacks;
    }
    @GetMapping("/retrieve-FeedBacks/{idFeedBack}")
    public FeedBack retrieveFeedBack(@PathVariable("idFeedBack") Long idFeedBack) {
        return feedbackService.retrieveFeedBack(idFeedBack);
    }


    @PostMapping("/add-FeedBack")
    public FeedBack addFeedBack(@RequestBody FeedBack feedBack) {
        FeedBack FeedBack = feedbackService.addFeedBack(feedBack);
        return FeedBack;
    }


    @DeleteMapping("/remove-FeedBack/{idFeedBack}")
    public void removeFeedBack(@PathVariable("idFeedBack") Long idFeedBack) {

        feedbackService.removeFeedBack(idFeedBack);
    }
    @PutMapping("/update-FeedBack")
    public FeedBack updateFeedBack(@RequestBody FeedBack feedBack) {
        FeedBack FeedBack= feedbackService.updateFeedBack(feedBack);
        return FeedBack;
    }
    @GetMapping("/{id}/feedbacks/agentNotNull")
    public List<FeedBack> getFeedbacksWithAgentNotNull(@PathVariable Long id) {
        Profil profil = profilRepository.findById(id).orElse(null);
        return feedbackService.getFeedbacksByAgentNotNull(profil);
    }
    @GetMapping("/{id}/feedbacks/parrainNotNull")
    public List<FeedBack> getFeedbacksWithParrainNotNull(@PathVariable Long id) {
        Profil profil = profilRepository.findById(id).orElse(null);
        return feedbackService.getFeedbacksByParraintNotNull(profil);
    }
}
