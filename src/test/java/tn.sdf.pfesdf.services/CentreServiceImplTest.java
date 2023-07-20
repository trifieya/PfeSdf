package tn.sdf.pfesdf.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.sdf.pfesdf.controllers.PersonneRestController;
import tn.sdf.pfesdf.entities.Centre;
import tn.sdf.pfesdf.entities.Discipline;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.repository.AgentRepository;

import java.util.List;
@SpringBootTest
public class CentreServiceImplTest {
    Centre savedCentre;

  @Autowired
  CentreServiceImpl centreService;


    @BeforeEach
    public void setup() {
        // Initialize the mocks before each test
        //  User u =User.builder().address("ariana").email("test@gmail.com").fullname("falten").phone(251203690).sexe(Sexe.MALE).username("test").build();
        Centre c = new Centre("SauverSDF", 58285007, 30);
        savedCentre = centreService.addCentre(c);

    }

    @Test
    public void testGetAllCentre() {

        List<Centre> listCentre = centreService.retrieveAllCentres();
        Assertions.assertTrue(listCentre.size() > 0);
        Centre c = new Centre("ForSDF", 58285507, 20);
        List<Centre> centreList = centreService.retrieveAllCentres();
        Integer initialSize = centreList.size();
        Centre addedCentre = centreService.addCentre(c);
        centreList.add(addedCentre);
        Assertions.assertTrue(centreList.size() > 0);
        Assertions.assertTrue(centreList.size() == initialSize + 1);
        centreService.removeCentre(addedCentre.getIdCentre());
    }


    @Test
    @Order(2)
    public void testGetCategory() {
        Centre centreFind = centreService.retrieveCentre(savedCentre.getIdCentre());
        Assertions.assertTrue(centreFind != null);
        Assertions.assertTrue(centreFind.getNom().equals("SauverSDF"));
        Assertions.assertTrue(centreFind.getTelephone()==58285007);

    }


    @Test
    @Order(3)
    public void testupdatePersonne() {
        Centre centreFind = centreService.retrieveCentre(savedCentre.getIdCentre());
        centreFind.setTelephone(28128426);
        Assertions.assertTrue(centreFind.getTelephone().equals(28128426));
        Assertions.assertTrue(centreFind.getNom().equals("SauverSDF"));

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
        centreService.removeCentre(savedCentre.getIdCentre());

    }
}
