package tn.sdf.pfesdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.interfaces.IAgentService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/agent")
public class AgentRestController {
    @Autowired
    IAgentService agentService;

    @GetMapping("/retrieve-all-agents")
   // @PreAuthorize("hasRole('ROLE_AGENT')")
    //@PreAuthorize("hasRole('ROLE_AGENT')")
    public List<Agent> getAgents() {
        List<Agent> listAgents = agentService.retrieveAllAgents();
        return listAgents;
    }
    @GetMapping("/retrieve-agents/{idAgent}")
    public Agent retrieveAgent(@PathVariable("idAgent") Long idAgent) {
        return agentService.retrieveAgent(idAgent);
    }


    @PostMapping("/add-agent")
    public Agent addAgent(@RequestBody Agent ag) {
        Agent agent = agentService.addAgent(ag);
        return agent;
    }


    @DeleteMapping("/remove-agent/{idAgent}")
    public void removeAgent(@PathVariable("idAgent") Long idAgent) {

        agentService.removeAgent(idAgent);
    }

    // http://localhost:8089/Kaddem/contrat/update-contrat
    @PutMapping("/update-agent/{idAgent}")
    public Agent updateAgent(@RequestBody Agent ag, @PathVariable("idAgent") Long idAgent) {
        Agent agent= agentService.updateAgent(ag,idAgent);
        return agent;
    }

}
