package tn.sdf.pfesdf.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.sdf.pfesdf.controllers.RendezVousRestController;
import tn.sdf.pfesdf.entities.Discipline;
import tn.sdf.pfesdf.entities.RendezVous;
import tn.sdf.pfesdf.repository.AgentRepository;

import java.util.List;

@SpringBootTest
public class RendezVousServiceImplTest {
    RendezVous savedRendezVous;

    @Autowired
    RendezVousServiceImpl rendezVousService;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    RendezVousRestController rendezVousRestController;


    @BeforeEach
    public void setup() {
        // Initialize the mocks before each test
        //  User u =User.builder().address("ariana").email("test@gmail.com").fullname("falten").phone(251203690).sexe(Sexe.MALE).username("test").build();
        RendezVous r = new RendezVous(20223-07-03, "test@gmail.fr", "pwd", 36.89974335764587, 10.189009815391552);
        savedRendezVous = RendezVousService.addRendezVous(p);

    }

    @Test
    public void testGetAllUsers() {

        List<RendezVous> listUsers = RendezVousService.retrieveAllRendezVouss();
        Assertions.assertTrue(listUsers.size() > 0);
        RendezVous p = new RendezVous("testuser1", "test1@gmail.fr", "pwd", 36.89974335764587, 10.189009815391552);
        List<RendezVous> listRendezVouss = RendezVousService.retrieveAllRendezVouss();
        Integer initialSize = listRendezVouss.size();
        RendezVous addedRendezVous = RendezVousService.addRendezVous(p);
        listRendezVouss.add(addedRendezVous);
        Assertions.assertTrue(listRendezVouss.size() > 0);
        Assertions.assertTrue(listRendezVouss.size() == initialSize + 1);
        RendezVousService.removeRendezVous(addedRendezVous.getIdRendezVous());
    }


    @Test
    @Order(2)
    public void testGetCategory() {
        RendezVous RendezVousFind = RendezVousService.retrieveRendezVous(savedRendezVous.getIdRendezVous());
        Assertions.assertTrue(RendezVousFind != null);
        Assertions.assertTrue(RendezVousFind.getUsername().equals("testuser"));
        Assertions.assertTrue(RendezVousFind.getLogitude() == 36.89974335764587);

    }


    @Test
    @Order(3)
    public void testupdateRendezVous() {
        RendezVous RendezVousFind = RendezVousService.retrieveRendezVous(savedRendezVous.getIdRendezVous());
        RendezVousFind.setDiscipline(Discipline.SOUPLE);
        Assertions.assertFalse(RendezVousFind.getDiscipline().equals(Discipline.COMPLIQUEE));
        Assertions.assertTrue(RendezVousFind.getUsername().equals("testuser"));

    }
/*
    @Test
    public void testChangeLocation() {
        // Set up authentication context for testing with ROLE_AGENT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_AGENT"))) {

            Double expectedLongitude = 35.34813849630946;
            Double expectedLatitude = 10.98031479676161;

            // Call changeLocation method on the RendezVousService instance
            RendezVousService.changeLocation(expectedLongitude, expectedLatitude);
            // Retrieve the updated RendezVous after the change
            Agent updatedAgent = agentRepository.findByUsername("seif")
                    .orElseThrow(() -> new NotFoundException("Agent non trouvé"));

            // Assert that expected user locations are the same with the location updated of savedRendezVous
            Assertions.assertEquals(expectedLongitude, updatedAgent.getLogitude());
            Assertions.assertEquals(expectedLatitude, updatedAgent.getLatitude());
        }
    }

*/

    @AfterEach
    public void cleanUp() {
        RendezVousService.removeRendezVous(savedRendezVous.getIdRendezVous());

    }
}
