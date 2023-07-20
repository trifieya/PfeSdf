package tn.sdf.pfesdf.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Discipline;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.entities.TrancheAge;

import java.time.LocalDate;


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
        Agent a = new Agent("testagent", "test@gmail.fr", "pwd", 36.89974335764587, 10.189009815391552);
        savedAgent = agentService.addAgent(a);


    }

    @Test
    public void testCalculateAgeAndSetTrancheAge() {
    // get Agent with birth date
        Agent agentFind = agentService.retrieveAgent(savedAgent.getIdAgent());
        LocalDate birthDate = LocalDate.of(1997, 7, 11);
        // create expected tranche Age to be found
        TrancheAge trancheAge = TrancheAge.VINGT_CINQ_TRENTE;
        agentService.calculateAgeAndSetTrancheAge(agentFind, birthDate);
    // assert that agent trancheAge and expectedTrancheAge are the same
        Assertions.assertTrue(agentFind.getTrancheAge().equals(trancheAge));


    }


    @AfterEach
    public void cleanUp() {
        agentService.removeAgent(savedAgent.getIdAgent());

    }
}
