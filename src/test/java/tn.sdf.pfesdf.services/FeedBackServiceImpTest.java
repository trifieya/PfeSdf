package tn.sdf.pfesdf.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.FeedBack;


@SpringBootTest
public class FeedBackServiceImpTest {

    FeedBack savedFeedBack;

    @Autowired
    FeedBackServiceImpl feedBackService;

    // Create SDF and persist in the database
    // Create Agent and persist in the database
    // Create feedback of the Agent to this SDF



    @BeforeEach
    public void setup() {
        // Initialize the mocks before each test


    }

    @Test
    public void testAddFeedBack() {
    // persist feedbacks already created
        // create expectedResult (here in case of we have already a feedback of the agent
        // call method AddFeedBack to add annother feedback from the agent
        // result expected here is to have the msg
        // throw new RuntimeException("Vous avez déjà ajouté un feedback en tant qu'agent pour ce profil cette année !");
        // Assert that the expectedResult and findResult are the same
        // clean database


    }


    @Test
    public void testAddFeedBackSecondSenario() {
        // create expectedResult (here in case of we don't have a feedback of the agent)
        // call method AddFeedBack to add  feedback from the agent
        // result expected here is to have a new feedback in the database
        // Assert that the expectedResult and findResult are the same (persisted data is the same as the expected data)
        // clean database


    }

    @AfterEach
    public void cleanUp() {

    }
}
