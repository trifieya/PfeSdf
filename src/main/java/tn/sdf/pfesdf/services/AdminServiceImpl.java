package tn.sdf.pfesdf.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Admin;
import tn.sdf.pfesdf.interfaces.IAdminService;
import tn.sdf.pfesdf.interfaces.IAgentService;
import tn.sdf.pfesdf.repository.AdminRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AdminServiceImpl implements IAdminService {
    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    AdminRepository adminRepository;

    @Override
    public Admin findUserByPasswordToken(String token) {
        return passwordResetTokenService.findAdminByPasswordToken(token).orElse(null);

    }

    @Override
    public void createPasswordResetTokenForAdmin(Admin admin, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForAdmin(admin,passwordResetToken);

    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        return  adminRepository.findByEmail(email);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetTokenAdmin(token);
    }

    @Override
    public void resetPassword(Admin admin, String newPassword) {
        admin.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);

    }
}
