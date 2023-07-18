package tn.sdf.pfesdf.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.sdf.pfesdf.entities.Profil;

@SpringBootTest
public class ProfilServiceImpTest {

    Profil savedProfil;

    // create user
    // create profil associated to user with distinct lacation
    // create 3 centres with different location


    @Autowired
    ProfilServiceImpl profilService;

    @BeforeEach
    public void setup() {
        // Initialize the mocks before each test
    }

    @Test
    public void testAssignNearestCentre() {
        // create expected nearest center
        // call service AssignNearestCentre
        // assert that the expected nearest center and the result are the same

    }
        @Test
        public void testCalculateDistance() {
            // create expected distance result
            // call service CalculateDistance
            // assert that the expected nearest center and the result are the same



    }

    @AfterEach
    public void cleanUp() {

    }
}
