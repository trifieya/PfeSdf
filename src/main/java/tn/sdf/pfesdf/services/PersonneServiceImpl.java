package tn.sdf.pfesdf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.interfaces.IPersonneService;
import tn.sdf.pfesdf.repository.PersonneRepository;
import tn.sdf.pfesdf.security.services.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonneServiceImpl implements IPersonneService {
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<Personne> retrieveAllPersonnes() {
        return personneRepository.findAll();
    }

    @Override
    public Personne updatePersonne(Long idPersonne,Personne p) {return personneRepository.save(p);
    }




    @Override
    public Personne addPersonne(Personne per) {
        String password = per.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        per.setPassword(hashedPassword);
        return personneRepository.save(per);
    }




    @Override
    public Personne retrievePersonne(Long idPersonne) {
        return personneRepository.findById(idPersonne).orElse(null);
    }

    @Override
    public void removePersonne(Long idPersonne) {
        personneRepository.deleteById(idPersonne);

    }

    @Override
    public Optional<Personne> findByEmail(String email) {
        return personneRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForPersonne(Personne personne, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForPersonne(personne,passwordResetToken);

    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetTokenPersonne(token);

    }

    @Override
    public Personne findUserByPasswordToken(String token) {
        return passwordResetTokenService.findPersoneByPasswordToken(token).orElse(null);
    }

    @Override
    public void resetPassword(Personne personne, String newPassword) {
        personne.setPassword(passwordEncoder.encode(newPassword));
        personneRepository.save(personne);
    }

    @Override
    public Personne addFile(MultipartFile file, Long idPersonne) {
        Personne personne = personneRepository.findById(idPersonne).orElse(null);
        if (personne == null) return null;
        try {
            removeFile(idPersonne);
            personne.setPhoto("ftp://eya@127.0.0.1/profile" + personne.getIdPersonne() + "/photoProfil/" + file.getOriginalFilename());
            personne = personneRepository.save(personne);
            FTPServiceImp.uFileUpload(file, "photoProfil", idPersonne);
            return personne;
        } catch (Exception e) {
            System.out.println("Error Uploading file");
        }
        return null;
    }

    public String getPhotoUrl(Long idPersonne) {
        Personne personne = personneRepository.findById(idPersonne).orElse(null);
        if (personne == null || personne.getPhoto() == null) {
            return null;
        }
        return personne.getPhoto();
    }



    @Override
    public void removeFile(Long idPersonne) {
        Personne personne = personneRepository.findById(idPersonne).orElse(null);
        if (!(personne == null)) {
            personne.setPhoto(null);
            try {
                FTPServiceImp.uFileremove(personne.getPhoto(), "photoProfil", personne.getIdPersonne());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @Override
    public void enregistrerCoordonnees(Long idPersonne,Float latitude, Float longitude) {

        Personne personne = personneRepository.findById(idPersonne).orElse(null);
        personne.setLatitude(latitude);
        personne.setLogitude(longitude);
        personneRepository.save(personne);
    }



}
