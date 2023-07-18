package tn.sdf.pfesdf.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.sdf.pfesdf.entities.Agent;


@SpringBootTest
public class AgentServiceImpTest {

    Agent savedAgent;

    @Autowired
    AgentServiceImpl agentService;

    // Create Agent with birthdate



    @BeforeEach
    public void setup() {
        // Initialize the mocks before each test
      // Create Agent


    }

    @Test
    public void testCalculateAgeAndSetTrancheAge() {
    // get Agent with birth date
    // create expected tranche Age to be found
    // assert that agent trancheAge and expectedTrancheAge are the same

    }


    @AfterEach
    public void cleanUp() {

    }
}
