package tn.sdf.pfesdf.services;



import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.sdf.pfesdf.entities.Personne;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;



//@SpringJUnitConfig
@SpringBootTest
public class PersonneServiceImpTest {

    Personne savedPersonne;

    @Autowired
     PersonneServiceImpl personneService;

    @BeforeEach
    public void setup() {
        // Initialize the mocks before each test
      //  User u =User.builder().address("ariana").email("test@gmail.com").fullname("falten").phone(251203690).sexe(Sexe.MALE).username("test").build();
       Personne p = new Personne("testuser","test@gmail.fr","pwd");
        savedPersonne= personneService.addPersonne(p);

    }

    @Test
    public void testGetAllUsers() {

       List<Personne> listUsers= personneService.retrieveAllPersonnes();
       Assertions.assertTrue(listUsers.size()>0);
        // Verify that the JavaMailSender's send method was called with the correct arguments
       // verify(javaMailSender, times(1)).send(any());
    }

    @Test
    public void testFindById() {
       Personne personneFind= personneService.retrievePersonne(savedPersonne.getIdPersonne());
       Assertions.assertTrue(personneFind.getUsername().equals("testuser"));

    }

    @AfterEach
    public void cleanUp() {
       personneService.removePersonne(savedPersonne.getIdPersonne());

    }
}
