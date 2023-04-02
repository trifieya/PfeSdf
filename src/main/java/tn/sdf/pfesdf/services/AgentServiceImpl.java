package tn.sdf.pfesdf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.interfaces.IAgentService;
import tn.sdf.pfesdf.repository.AgentRepository;

import java.util.List;
@Service
public class AgentServiceImpl implements IAgentService {
    @Autowired
    AgentRepository agentRepository;
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
}
