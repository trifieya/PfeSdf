package tn.sdf.pfesdf.interfaces;

import org.springframework.web.multipart.MultipartFile;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Personne;

import java.util.List;
import java.util.Optional;

public interface IPersonneService {
    public List<Personne> retrieveAllPersonnes();

    public Personne updatePersonne (Long idPersonne,Personne  per);

    public  Personne addPersonne(Personne per);

    public Personne retrievePersonne (Long  idPersonne);

    public  void removePersonne(Long idPersonne);
    public Optional<Personne>  findByEmail(String email);

    void createPasswordResetTokenForPersonne(Personne personne, String passwordResetToken);

    String validatePasswordResetToken(String token);

    Object findUserByPasswordToken(String token);

    void resetPassword(Personne personne, String newPassword);
    public Personne addFile(MultipartFile file, Long idPersonne);
    public void removeFile(Long idPersonne);
    public String getPhotoUrl(Long idPersonne);

    public void enregistrerCoordonnees(Long idPersonne ,Float latitude, Float longitude);
}
