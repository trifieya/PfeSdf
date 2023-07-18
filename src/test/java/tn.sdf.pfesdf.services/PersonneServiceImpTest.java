package tn.sdf.pfesdf.services;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.sdf.pfesdf.entities.Discipline;
import tn.sdf.pfesdf.entities.Personne;
import java.util.List;

@SpringBootTest
public class PersonneServiceImpTest {

    Personne savedPersonne;

    @Autowired
     PersonneServiceImpl personneService;



    @BeforeEach
    public void setup() {
        // Initialize the mocks before each test
      //  User u =User.builder().address("ariana").email("test@gmail.com").fullname("falten").phone(251203690).sexe(Sexe.MALE).username("test").build();
       Personne p = new Personne("testuser","test@gmail.fr","pwd",36.89974335764587, 10.189009815391552);
        savedPersonne= personneService.addPersonne(p);

    }

    @Test
    public void testGetAllUsers() {

       List<Personne> listUsers= personneService.retrieveAllPersonnes();
       Assertions.assertTrue(listUsers.size()>0);
        Personne p = new Personne("testuser","test@gmail.fr","pwd",36.89974335764587, 10.189009815391552);
        List<Personne> listPersonnes= personneService.retrieveAllPersonnes();
        Integer initialSize = listPersonnes.size();
        Personne addedPersonne = personneService.addPersonne(p);
        listPersonnes.add(addedPersonne);
        Assertions.assertTrue(listPersonnes.size()>0);
        Assertions.assertTrue(listPersonnes.size()==initialSize+1);
        personneService.removePersonne(addedPersonne.getIdPersonne());
    }



    @Test
    @Order(2)
    public void testGetCategory() {
        Personne personneFind= personneService.retrievePersonne(savedPersonne.getIdPersonne());
        Assertions.assertTrue(personneFind !=null);
        Assertions.assertTrue(personneFind.getUsername().equals("testuser"));
        Assertions.assertTrue(personneFind.getLogitude()==36.89974335764587);

    }


    @Test
    @Order(3)
    public void testupdatePersonne()
    {
        Personne personneFind= personneService.retrievePersonne(savedPersonne.getIdPersonne());
        personneFind.setDiscipline(Discipline.SOUPLE);
        Assertions.assertFalse(personneFind.getDiscipline().equals(Discipline.COMPLIQUEE));
        Assertions.assertTrue(personneFind.getUsername().equals("testuser"));

    }

    @Test
    public void testChangeLocation() {
   // create expected user with specific location
        // call ChangeLocation method on the savedPersonne object
        // assert that expected user locations are the same with the location updated of savedPersonne
    }


    @AfterEach
    public void cleanUp() {
       personneService.removePersonne(savedPersonne.getIdPersonne());

    }
}
