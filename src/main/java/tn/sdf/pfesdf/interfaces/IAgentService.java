package tn.sdf.pfesdf.interfaces;

import tn.sdf.pfesdf.entities.Agent;

import java.util.List;

public interface IAgentService {
    public List<Agent> retrieveAllAgents();

    public Agent updateAgent (Agent  ag , Long  idAgent);

    public  Agent addAgent(Agent ag);

    public Agent retrieveAgent (Long  idAgent);

    public  void removeAgent(Long idAgent);

}
