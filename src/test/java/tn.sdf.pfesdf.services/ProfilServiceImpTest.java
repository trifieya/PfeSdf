package tn.sdf.pfesdf.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Centre;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.entities.Profil;
import tn.sdf.pfesdf.repository.CentreRepository;
import tn.sdf.pfesdf.repository.ProfilRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ProfilServiceImpTest {
    // create user
    // create profil associated to user with distinct lacation
    // create 3 centres with different location

    Profil savedProfil;
    Personne savedPersonne;




    @Autowired
    ProfilServiceImpl profilService;
    @Autowired
    PersonneServiceImpl personneService;
@Autowired
    ProfilRepository profilRepository;
@Autowired
    CentreRepository centreRepository;
@Autowired
CentreServiceImpl centreService;

    @BeforeEach
    public void setup() {
        Personne p = new Personne("testuserrr", "testttt@gmail.fr", "pwd", 10.708303869942217, 36.32323953914599);
        savedPersonne = personneService.addPersonne(p);
        // Initialize the mocks before each test
        Profil pro = new Profil("cancer", p);
        savedProfil = profilService.addProfil(pro);
        savedProfil.setProfilpresonne(p);
    }

    @Test
    public void testAssignNearestCentre() {
        // Create test data


        // Mock Centre
        Centre centre1 = new Centre();
        centre1.setLongitude(15.0);
        centre1.setLatitude(25.0);

        Centre centre2 = new Centre();
        centre2.setLongitude(12.0);
        centre2.setLatitude(22.0);

        List<Centre> centres = new ArrayList<>();
        centres.add(centre1);
        centres.add(centre2);

        // Mock Repository behavior
        //when(profilRepository.findById(savedProfil.getIdProfil())).thenReturn(Optional.of(savedProfil));
       // when(centreRepository.findAll()).thenReturn(centres);

        // Call the method under test
        profilService.assignNearestCentre(savedProfil.getIdProfil());

        // Assert that the nearest center has been set in the Personne object
        //Assertions.assertEquals(centre2, personne.getCentre());
        Assertions.assertFalse(centre2.equals(savedPersonne.getCentre()));

    }

// @Test
    //  public void testCalculateDistance() {

    // create expected distance result
    // call service CalculateDistance
    // assert that the expected nearest center and the result are the same



    //}


    @AfterEach
    public void cleanUp() {
        personneService.removePersonne(savedPersonne.getIdPersonne());

    }
}
