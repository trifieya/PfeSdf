package tn.sdf.pfesdf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.interfaces.IAgentService;
import tn.sdf.pfesdf.repository.AgentRepository;

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


}
