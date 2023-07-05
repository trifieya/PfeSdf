package tn.sdf.pfesdf.interfaces;

import tn.sdf.pfesdf.entities.FeedBack;

import java.util.List;

public interface IFeedbackService {
    public List<FeedBack> retrieveAllFeedBacks();

    public FeedBack updateFeedBack(FeedBack feedBack);

    public FeedBack addFeedBack(FeedBack feedBack);

    public FeedBack retrieveFeedBack(Long idFeedBack);

    public void removeFeedBack(Long idFeedBack);
}
