package tn.sdf.pfesdf.interfaces;

import tn.sdf.pfesdf.entities.Admin;
import tn.sdf.pfesdf.entities.Personne;

import java.util.Optional;

public interface IAdminService {
    Object findUserByPasswordToken(String token);
    void createPasswordResetTokenForAdmin(Admin admin, String passwordResetToken);
    public Optional<Admin> findByEmail(String email);
    String validatePasswordResetToken(String token);
    void resetPassword(Admin admin, String newPassword);

}
