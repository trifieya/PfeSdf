package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.FeedBack;
import tn.sdf.pfesdf.interfaces.IFeedbackService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/feedback")
@RestController
public class FeedBackRestController {
    @Autowired
    IFeedbackService feedbackService;
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
}
