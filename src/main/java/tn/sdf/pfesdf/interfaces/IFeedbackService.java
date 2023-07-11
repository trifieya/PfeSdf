package tn.sdf.pfesdf.interfaces;

import tn.sdf.pfesdf.entities.FeedBack;
import tn.sdf.pfesdf.entities.Profil;

import java.util.List;

public interface IFeedbackService {
    public List<FeedBack> retrieveAllFeedBacks();

    public FeedBack updateFeedBack(FeedBack feedBack);

    public FeedBack addFeedBack(FeedBack feedBack);

    public FeedBack retrieveFeedBack(Long idFeedBack);

    public void removeFeedBack(Long idFeedBack);
    public List<FeedBack> getFeedbacksByAgentNotNull(Profil profil);
        public List<FeedBack> getFeedbacksByParraintNotNull(Profil profil);

    }
