package tn.sdf.pfesdf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.entities.TrancheAge;
import tn.sdf.pfesdf.interfaces.IAgentService;
import tn.sdf.pfesdf.repository.AgentRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements IAgentService {
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<Agent> retrieveAllAgents() {
        return agentRepository.findAll() ;
    }

    @Override
    public Agent updateAgent(Agent ag, Long  idAgent) {
        return agentRepository.save(ag);
    }

    @Override
    public Agent addAgent(Agent ag) {
        String password = ag.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        ag.setPassword(hashedPassword);
        return agentRepository.save(ag);
    }

    @Override
    public Agent retrieveAgent(Long idAgent) {
        return agentRepository.findById(idAgent).orElse(null);
    }

    @Override
    public void removeAgent(Long idAgent) {
        agentRepository.deleteById(idAgent);

    }

    @Override
    public Agent findUserByPasswordToken(String token) {
        return passwordResetTokenService.findAgentByPasswordToken(token).orElse(null);
    }

    @Override
    public void createPasswordResetTokenForAgent(Agent agent, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForAgent(agent,passwordResetToken);

    }

    @Override
    public Optional<Agent> findByEmail(String email) {
        return agentRepository.findByEmail(email);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetTokenAgent(token);
    }

    @Override
    public void resetPassword(Agent agent, String newPassword) {
        agent.setPassword(passwordEncoder.encode(newPassword));
        agentRepository.save(agent);
    }
    @Override
    public void calculateAgeAndSetTrancheAge(Agent agent, LocalDate age) {
        // Calculate age from date of birth
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(age, currentDate);
        int calculatedAge = period.getYears();

        // Set trancheAge based on age
        if (calculatedAge >= 15 && calculatedAge <= 20) {
            agent.setTrancheAge(TrancheAge.QUINZ_VINGT);
        } else if (calculatedAge >= 21 && calculatedAge <= 25) {
            agent.setTrancheAge(TrancheAge.VINGT_VINGT_CINQ);
        } else if (calculatedAge >= 26 && calculatedAge <= 30) {
            agent.setTrancheAge(TrancheAge.VINGT_CINQ_TRENTE);
        } else if (calculatedAge >= 31 && calculatedAge <= 35) {
            agent.setTrancheAge(TrancheAge.TRENTE_TRENTE_CINQ);
        } else if (calculatedAge >= 36 && calculatedAge <= 40) {
            agent.setTrancheAge(TrancheAge.TRENTE_CINQ_QUARNTE);
        } else {
            // Handle the case when the age doesn't fit into any predefined range
        }

        // Save the personne entity
        agentRepository.save(agent);
    }

}
