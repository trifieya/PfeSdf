package tn.sdf.pfesdf.interfaces;

import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IParrainService {
    public List<Parrain> retrieveAllParrains();

   public Parrain updateParrain (Long idParrain,Parrain  p);
   //public Parrain updateParrain (Parrain  p);

    public  Parrain addParrain(Parrain p);

    public Parrain retrieveParrain (Long  idParrain);

    public  void removeParrain (Long idParrain);
    Object findUserByPasswordToken(String token);
    void createPasswordResetTokenForParrain(Parrain parrain, String passwordResetToken);
    public Optional<Parrain> findByEmail(String email);
    String validatePasswordResetToken(String token);
    void resetPassword(Parrain parrain, String newPassword);
 public void calculateAgeAndSetTrancheAge(Parrain parrain, LocalDate age);


}
