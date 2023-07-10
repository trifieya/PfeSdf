package tn.sdf.pfesdf.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.sdf.pfesdf.entities.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPersonneService {
    public List<Personne> retrieveAllPersonnes();

    public Personne updatePersonne(Long idPersonne, Personne per);

    public Personne addPersonne(Personne per);

    public Object retrievePersonne() ;

    public void removePersonne(Long idPersonne);

    public Optional<Personne> findByEmail(String email);

    void createPasswordResetTokenForPersonne(Personne personne, String passwordResetToken);

    String validatePasswordResetToken(String token);

    Object findUserByPasswordToken(String token);

    void resetPassword(Personne personne, String newPassword);

    public Personne addFile(MultipartFile file, Long idPersonne);

    public void removeFile(Long idPersonne);

    public String getPhotoUrl(Long idPersonne);

    public void enregistrerCoordonnees(Long id, Double latitude, Double longitude);
    public Object getCurrentUser();
    public void changeLocation(Double newLongitude, Double newLatitude);
    public Double[] getUserCoordinates() ;
    public void calculateAgeAndSetTrancheAge(Personne personne, LocalDate age);
    public void editprofile(String username,String email,String nom,String prenom,LocalDate age,String delegationId,
                            Integer cin,Discipline discipline,Integer phnum,Gender gender) ;
    public void editprofileForAgent(String username,String email,String nom,String prenom,LocalDate age,String delegationId,
                                    Integer cin,Discipline discipline,Integer phnum,Gender gender,Long idCentre);


    }