package tn.sdf.pfesdf.interfaces;

import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Personne;

import java.util.List;
import java.util.Optional;

public interface IAgentService {
    public List<Agent> retrieveAllAgents();

    public Agent updateAgent (Agent  ag , Long  idAgent);

    public  Agent addAgent(Agent ag);

    public Agent retrieveAgent (Long  idAgent);

    public  void removeAgent(Long idAgent);
    Object findUserByPasswordToken(String token);
    void createPasswordResetTokenForAgent(Agent agent, String passwordResetToken);
    public Optional<Agent> findByEmail(String email);
    String validatePasswordResetToken(String token);
    void resetPassword(Agent agent, String newPassword);


}
