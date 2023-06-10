package tn.sdf.pfesdf.services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sdf.pfesdf.entities.*;
import tn.sdf.pfesdf.interfaces.IProfilService;
import tn.sdf.pfesdf.repository.*;

import java.util.List;
@Slf4j
@Service
public class ProfilServiceImpl  implements IProfilService {
    @Autowired
    ProfilRepository profilRepository;
    @Autowired
    CentreRepository centreRepository;
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    ParrainRepository parrainRepository;

    @Override
    public List<Profil> retrieveAllProfils() {

        return profilRepository.findAll();
    }

    @Override
    public Profil updateProfil(Profil pro) {

        return profilRepository.save(pro);
    }

    @Override
    public Profil addProfil(Profil pro) {

        return profilRepository.save(pro);
    }

    @Override
    public Profil retrieveProfil(Long idProfil) {

        return profilRepository.findById(idProfil).orElse(null);
    }

    @Override
    public void removeProfil(Long idProfil) {
        profilRepository.deleteById(idProfil);

    }
    //affectation sdf à un centre
    public void assignNearestCentre(Long profilId) {
        Profil profil = profilRepository.findById(profilId).orElseThrow(() -> new IllegalArgumentException("Invalid profil ID"));

        Personne personne = profil.getProfilpresonne();
        List<Centre> centres = centreRepository.findAll();

        Centre nearestCentre = null;
        Float minDistance = Float.MAX_VALUE;

        for (Centre centre : centres) {
            Float distance = calculateDistance(personne.getLogitude(), personne.getLatitude(), centre.getLongitude(), centre.getLatitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearestCentre = centre;
            }
        }

        personne.setCentre(nearestCentre);
        personneRepository.save(personne);
    }
    public float calculateDistance(float longitude1, float latitude1, float longitude2, float latitude2) {
        float earthRadius = 6371f; // Radius of the Earth in kilometers

        // Convert latitude and longitude to radians
        float lat1Rad = (float) Math.toRadians(latitude1);
        float lon1Rad = (float) Math.toRadians(longitude1);
        float lat2Rad = (float) Math.toRadians(latitude2);
        float lon2Rad = (float) Math.toRadians(longitude2);

        // Calculate the differences
        float latDiff = lat2Rad - lat1Rad;
        float lonDiff = lon2Rad - lon1Rad;

        // Calculate the Haversine distance
        float a = (float) (Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2));
        float c = 2 * (float) Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float distance = earthRadius * c;

        return distance;
    }
    //affecter à un agent selon disponibilité
    public void assignrprofilagentdisponibilité(Long idProfil){
            Profil profil = profilRepository.findById(idProfil).orElse(null);
            Personne personne = profil.getProfilpresonne();
            Agent agentDisponible =null;
            Agent agentPrecedent = personne.getAgent();
            int plusPetitNbEncours= Integer.MAX_VALUE;//en initialisant plusPetitNbEncours avec Integer.MAX_VALUE, nous nous assurons qu'il sera remplacé dès que nous trouverons un agent dont le nombre d'encours est inférieur, ce qui nous permettra de trouver l'agent ayant le plus petit nombre d'encours parmi tous les agents disponibles.

        List<Agent> agents = agentRepository.findAll();
        for(Agent agent:agents) {
            if (agent.getNbencours() < agent.getNbmax() && agent.getNbencours() < plusPetitNbEncours) {

                agentDisponible = agent;
                plusPetitNbEncours = agent.getNbencours();
            }
        }
            if (agentDisponible!= null){
                if (agentPrecedent != null) {
                    agentPrecedent.setNbencours(agentPrecedent.getNbencours() - 1); //l'effectation peut se faire pluqieurs fois en deccrmentant -1 ml agent l9dim li affecté lih w nzido 1 lel agent jdid
                }
                personne.setAgent(agentDisponible);
                agentDisponible.setNbencours(agentDisponible.getNbencours()+1);
                personneRepository.save(personne);
            }
            else {
                log.info("aucun agent n'est disponible pour le moment");
            }

        }

    //affecter à un agent selon disponibilité
    public void assignrprofilparraindisponibilité(Long idProfil){
        Profil profil = profilRepository.findById(idProfil).orElse(null);
        Personne personne = profil.getProfilpresonne();
        Parrain parrainDisponible =null;
        Parrain parrainPrecedent = personne.getParrain();
        int plusPetitNbEncours= Integer.MAX_VALUE;//en initialisant plusPetitNbEncours avec Integer.MAX_VALUE, nous nous assurons qu'il sera remplacé dès que nous trouverons un agent dont le nombre d'encours est inférieur, ce qui nous permettra de trouver l'agent ayant le plus petit nombre d'encours parmi tous les agents disponibles.

        List<Parrain> parrains = parrainRepository.findAll();
        for(Parrain parrain:parrains) {
            if (parrain.getNbencours() < parrain.getNbmax() && parrain.getNbencours() < plusPetitNbEncours) {

                parrainDisponible = parrain;
                plusPetitNbEncours = parrainDisponible.getNbencours();
            }
        }
        if (parrainDisponible!= null){
            if (parrainPrecedent != null) {
                parrainPrecedent.setNbencours(parrainPrecedent.getNbencours() - 1); //l'effectation peut se faire pluqieurs fois en deccrmentant -1 ml agent l9dim li affecté lih w nzido 1 lel agent jdid
            }
            personne.setParrain(parrainDisponible);
            parrainDisponible.setNbencours(parrainDisponible.getNbencours()+1);
            personneRepository.save(personne);
        }
        else{
            log.info("aucun parrain n'est disponible pour le moment");
        }

    }
    //affecter à un parrain selon discipline adequat
    public void assignprofilparrainadequat(Long idProfil) {
        Profil profil = profilRepository.findById(idProfil).orElse(null);
        Personne personne = profil.getProfilpresonne();
        Parrain parrainadequatetdisponible =null;
        Parrain parrainPrecedent = personne.getParrain();
        int plusPetitNbEncours= Integer.MAX_VALUE;

        List<Parrain> parrains = parrainRepository.findAll();
        for (Parrain parrain : parrains) {
            if (parrain.getDiscipline() == personne.getDiscipline() && parrain.getNbencours() < parrain.getNbmax() && parrain.getNbencours() < plusPetitNbEncours) {
                parrainadequatetdisponible = parrain;
                plusPetitNbEncours = parrainadequatetdisponible.getNbencours();

            }
        }
        if ( parrainadequatetdisponible != null) {
            if (parrainPrecedent != null) {
                parrainPrecedent.setNbencours(parrainPrecedent.getNbencours() - 1); //l'effectation peut se faire pluqieurs fois en deccrmentant -1 ml agent l9dim li affecté lih w nzido 1 lel agent jdid
            }
            personne.setParrain(parrainadequatetdisponible);
            parrainadequatetdisponible.setNbencours(parrainadequatetdisponible.getNbencours()+1);

            personneRepository.save(personne);
        } else {
            log.info("aucun parrain n'est adéquat pour le moment");
        }
    }

        //affecter à un agent selon discipline adequat
        public void assignprofilagentadequat(Long idProfil){
            Profil profil = profilRepository.findById(idProfil).orElse(null);
            Personne personne=profil.getProfilpresonne();
            Agent agentDisponibleetadequat =null;
            Agent agentPrecedent = personne.getAgent();
            int plusPetitNbEncours= Integer.MAX_VALUE;
            List<Agent>agents =agentRepository.findAll();
            for (Agent agent:agents){
                if(agent.getDiscipline()==personne.getDiscipline() && agent.getNbencours() < agent.getNbmax() && agent.getNbencours() < plusPetitNbEncours){
                    agentDisponibleetadequat=agent;
                    plusPetitNbEncours = agent.getNbencours();

                }
            }
            if(agentDisponibleetadequat!=null){
                if (agentPrecedent != null) {
                    agentPrecedent.setNbencours(agentPrecedent.getNbencours() - 1); //l'effectation peut se faire pluqieurs fois en deccrmentant -1 ml agent l9dim li affecté lih w nzido 1 lel agent jdid
                }
                personne.setAgent(agentDisponibleetadequat);
                agentDisponibleetadequat.setNbencours(agentDisponibleetadequat.getNbencours()+1);
                personneRepository.save(personne);
            }
            else{
                log.info("Aucun agent n'est adéquat pour le moment");
            }
    }
    //affecter à un parrain selon distance proche
    @Override
    public void assignprofilparrainproche(Long idProfil) {
        Profil profil = profilRepository.findById(idProfil).orElseThrow(() -> new IllegalArgumentException("Invalid profil ID"));
        Personne personne = profil.getProfilpresonne();
        Parrain parrainprocheetdisponible =null;
        Parrain parrainPrecedent = personne.getParrain();
        int plusPetitNbEncours= Integer.MAX_VALUE;
        List<Parrain> parrains = parrainRepository.findAll();


        Float minDistance = Float.MAX_VALUE;

        for (Parrain parrain : parrains) {
            Float distance = calculateDistance(personne.getLogitude(), personne.getLatitude(), parrain.getLogitude(), parrain.getLatitude());
            if (distance < minDistance && parrain.getNbencours() < parrain.getNbmax() && parrain.getNbencours() < plusPetitNbEncours) {
                minDistance = distance;
                parrainprocheetdisponible = parrain;
                plusPetitNbEncours = parrainprocheetdisponible.getNbencours();
            }
        }
        if(parrainprocheetdisponible !=null){
            if (parrainPrecedent != null) {
                parrainPrecedent.setNbencours(parrainPrecedent.getNbencours() - 1); //l'effectation peut se faire pluqieurs fois en deccrmentant -1 ml agent l9dim li affecté lih w nzido 1 lel agent jdid
            }

        personne.setParrain(parrainprocheetdisponible);
            parrainprocheetdisponible.setNbencours(parrainprocheetdisponible.getNbencours()+1);
        personneRepository.save(personne);
        } else {
            log.info("aucun parrain n'est proche pour le moment");
        }

    }

    //affecter à un agent selon distance proche
    @Override
    public void assignprofilagentproche(Long idProfil) {
        Profil profil = profilRepository.findById(idProfil).orElseThrow(() -> new IllegalArgumentException("Invalid profil ID"));

        Personne personne = profil.getProfilpresonne();
        List<Agent> agents = agentRepository.findAll();
        Agent agentDisponibleetproche =null;
        Agent agentPrecedent = personne.getAgent();
        int plusPetitNbEncours= Integer.MAX_VALUE;

        Float minDistance = Float.MAX_VALUE;

        for (Agent agent : agents) {
            Float distance = calculateDistance(personne.getLogitude(), personne.getLatitude(), agent.getLogitude(), agent.getLatitude());
            if (distance < minDistance && agent.getNbencours() < agent.getNbmax() && agent.getNbencours() < plusPetitNbEncours ) {
                minDistance = distance;
                agentDisponibleetproche = agent;
                plusPetitNbEncours = agent.getNbencours();

            }
        }
        if (agentDisponibleetproche!= null){
            if (agentPrecedent != null) {
                agentPrecedent.setNbencours(agentPrecedent.getNbencours() - 1); //l'effectation peut se faire pluqieurs fois en deccrmentant -1 ml agent l9dim li affecté lih w nzido 1 lel agent jdid
            }

            personne.setAgent(agentDisponibleetproche);
            agentDisponibleetproche.setNbencours(agentDisponibleetproche.getNbencours()+1);
            personneRepository.save(personne);

        }
        else {
            log.info("aucun agent n'est proche pour le moment");
        }

    }




}
