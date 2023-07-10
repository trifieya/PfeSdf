package tn.sdf.pfesdf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.Delegation;
import tn.sdf.pfesdf.interfaces.IDelegationService;
import tn.sdf.pfesdf.repository.DelegationRepository;

import java.util.List;

@Service
public class DelegationServiceImpl implements IDelegationService {
    @Autowired
    DelegationRepository delegationRepository;
    @Override
    public List<Delegation> retrieveAllDelegations() {
        return delegationRepository.findAll();
    }
}
