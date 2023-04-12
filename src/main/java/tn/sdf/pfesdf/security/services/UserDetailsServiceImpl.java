package tn.sdf.pfesdf.security.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.sdf.pfesdf.entities.Admin;
import tn.sdf.pfesdf.entities.Agent;
import tn.sdf.pfesdf.entities.Parrain;
import tn.sdf.pfesdf.entities.Personne;
import tn.sdf.pfesdf.repository.AdminRepository;
import tn.sdf.pfesdf.repository.AgentRepository;
import tn.sdf.pfesdf.repository.ParrainRepository;
import tn.sdf.pfesdf.repository.PersonneRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    ParrainRepository parrainRepository;
    @Autowired
    AdminRepository adminRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Personne personne = personneRepository.findByUsername(username)
                .orElse(null);

        if(personne != null) {
            return UserDetailsImpl.build(personne);
        }

        Parrain parrain = parrainRepository.findByUsername(username)
                .orElse(null);

        if(parrain != null) {
            return UserDetailsImpl.build(parrain);
        }

        Agent agent = agentRepository.findByUsername(username)
                .orElse(null);

        if(agent != null) {
            return UserDetailsImpl.build(agent);
        }
        Admin admin = adminRepository.findByUsername(username)
                .orElse(null);

        if(admin != null) {
            return UserDetailsImpl.build(admin);
        }

        throw new UsernameNotFoundException("User Not Found with username: " + username);
    }

}

