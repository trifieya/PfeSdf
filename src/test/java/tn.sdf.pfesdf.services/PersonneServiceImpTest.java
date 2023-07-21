package tn.sdf.pfesdf.services;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.webjars.NotFoundException;
import tn.sdf.pfesdf.controllers.PersonneRestController;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Discipline;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.repository.AgentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class PersonneServiceImpTest {

    Personne savedPersonne;

    @Autowired
    PersonneServiceImpl personneService;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    PersonneRestController personneRestController;


    @BeforeEach
    public void setup() {
        // Initialize the mocks before each test
        //  User u =User.builder().address("ariana").email("test@gmail.com").fullname("falten").phone(251203690).sexe(Sexe.MALE).username("test").build();
        Personne p = new Personne("testuser", "test@gmail.fr", "pwd", 36.89974335764587, 10.189009815391552);
        savedPersonne = personneService.addPersonne(p);

    }

    @Test
    public void testGetAllUsers() {

        List<Personne> listUsers = personneService.retrieveAllPersonnes();
        Assertions.assertTrue(listUsers.size() > 0);
        Personne p = new Personne("testuser1", "test1@gmail.fr", "pwd", 36.89974335764587, 10.189009815391552);
        List<Personne> listPersonnes = personneService.retrieveAllPersonnes();
        Integer initialSize = listPersonnes.size();
        Personne addedPersonne = personneService.addPersonne(p);
        listPersonnes.add(addedPersonne);
        Assertions.assertTrue(listPersonnes.size() > 0);
        Assertions.assertTrue(listPersonnes.size() == initialSize + 1);
        personneService.removePersonne(addedPersonne.getIdPersonne());
    }

/*
    @Test
    @Order(2)
    public void testGetCategory() {
        Personne personneFind = personneService.retrievePersonne(savedPersonne.getIdPersonne());
        Assertions.assertTrue(personneFind != null);
        Assertions.assertTrue(personneFind.getUsername().equals("testuser"));
        Assertions.assertTrue(personneFind.getLogitude() == 36.89974335764587);

    }
    
 */


    @Test
    @Order(3)
    public void testupdatePersonne() {
        Personne personneFind = personneService.retrievePersonne(savedPersonne.getIdPersonne());
        personneFind.setDiscipline(Discipline.SOUPLE);
        Assertions.assertFalse(personneFind.getDiscipline().equals(Discipline.COMPLIQUEE));
        Assertions.assertTrue(personneFind.getUsername().equals("testuser"));

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

            // Call changeLocation method on the personneService instance
            personneService.changeLocation(expectedLongitude, expectedLatitude);
            // Retrieve the updated Personne after the change
            Agent updatedAgent = agentRepository.findByUsername("seif")
                    .orElseThrow(() -> new NotFoundException("Agent non trouvé"));

            // Assert that expected user locations are the same with the location updated of savedPersonne
            Assertions.assertEquals(expectedLongitude, updatedAgent.getLogitude());
            Assertions.assertEquals(expectedLatitude, updatedAgent.getLatitude());
        }
    }

*/

    @AfterEach
    public void cleanUp() {
       personneService.removePersonne(savedPersonne.getIdPersonne());

    }
}
