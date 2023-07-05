package tn.sdf.pfesdf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.FeedBack;
import tn.sdf.pfesdf.interfaces.IFeedbackService;
import tn.sdf.pfesdf.repository.FeedbackRepository;

import java.util.List;

@Service
public class FeedBackServiceImpl implements IFeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
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
}
