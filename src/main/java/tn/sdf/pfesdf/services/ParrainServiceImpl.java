package tn.sdf.pfesdf.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.interfaces.IParrainService;
import tn.sdf.pfesdf.repository.ParrainRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ParrainServiceImpl implements IParrainService {
    @Autowired
    ParrainRepository parrainRepository;
    private final JavaMailSender mailSender;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<Parrain> retrieveAllParrains() {
        return parrainRepository.findAll();
    }

    /*@Override
    public Parrain updateParrain(Parrain p) {
        return parrainRepository.save(p);
    }*/



    @Override
    public Parrain updateParrain(Long idParrain, Parrain p) {
        return parrainRepository.save(p);
    }

    @Override
    public Parrain addParrain(Parrain p) {


        return parrainRepository.save(p);
    }

    @Override
    public Parrain retrieveParrain(Long idParrain) {
        return parrainRepository.findById(idParrain).orElse(null);
    }

    @Override
    public void removeParrain(Long idParrain) {
        parrainRepository.deleteById(idParrain);

    }



    public Parrain getUserByEmail(String email){
        return parrainRepository.findByEmail(email);
    }

}
