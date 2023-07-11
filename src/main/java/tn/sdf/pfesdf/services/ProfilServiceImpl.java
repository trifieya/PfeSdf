package tn.sdf.pfesdf.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tn.sdf.pfesdf.entities.*;
import tn.sdf.pfesdf.interfaces.IProfilService;
import tn.sdf.pfesdf.repository.*;
import tn.sdf.pfesdf.security.services.UserDetailsImpl;

import java.time.LocalDate;
import java.util.*;

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
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    private ProgrammeRepository programmeRepository;
//profil non archivé
    @Override
    public List<Profil> retrieveAllProfils() {

        return profilRepository.findByArchived(false);

    }
    //profil archivé
    @Override
    public List<Profil> retrieveArchivedProfils() {

        return profilRepository.findByArchived(true);

    }


    @Override
    public List<Profil> retrieveProfilsByAgentOrParrain() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long connecteduser = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        Agent connectedAgent = agentRepository.findById(connecteduser).orElse(null);//car f save je dois passer un agent c pour ça 3ayatet lel agent li ando agentid li connecté
        Parrain connectedParrain = parrainRepository.findById(connecteduser).orElse(null);//car f save je dois passer un agent c pour ça 3ayatet lel agent li ando agentid li connecté

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//authorities heya li feha les roles et les permissions
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_AGENT"))) {
            return profilRepository.findByProfilpresonneAgent(connectedAgent);
        }
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_PARRAIN"))) {
            return profilRepository.findByProfilpresonneParrain(connectedParrain);
        }
        else{
            throw new RuntimeException("L'utilisateur connecté n'a pas le rôle d'agent ou parrain !");
        }

    }





    @Override
    public Profil updateProfil(Profil pro) {
        Set<Profil> profilSet = new HashSet<>();
        profilSet.add(pro);
        for (Document document : pro.getDocuments()) {
            document.setProfildoc(profilSet);
        }
        profilRepository.save(pro);
        return pro;
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
        Double minDistance = Double.MAX_VALUE;

        for (Centre centre : centres) {
            Double distance = calculateDistance(personne.getLogitude(), personne.getLatitude(), centre.getLongitude(), centre.getLatitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearestCentre = centre;
            }
        }

        personne.setCentre(nearestCentre);
        personneRepository.save(personne);
    }
    public double calculateDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double earthRadius = 6371.0; // Radius of the Earth in kilometers

        // Convert latitude and longitude to radians
        double lat1Rad = Math.toRadians(latitude1);
        double lon1Rad = Math.toRadians(longitude1);
        double lat2Rad = Math.toRadians(latitude2);
        double lon2Rad = Math.toRadians(longitude2);

        // Calculate the differences
        double latDiff = lat2Rad - lat1Rad;
        double lonDiff = lon2Rad - lon1Rad;

        // Calculate the Haversine distance
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        return distance;
    }

    //affecter à un agent selon disponibilité
    public ResponseEntity<Agent> assignrprofilagentdisponibilité(Long idProfil){
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
        return ResponseEntity.ok(agentDisponible);
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
            personne.setDate_affect_parrain(LocalDate.now());
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
            personne.setDate_affect_parrain(LocalDate.now());
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


        Double minDistance = Double.MAX_VALUE;

        for (Parrain parrain : parrains) {
            Double distance = calculateDistance(personne.getLogitude(), personne.getLatitude(), parrain.getLogitude(), parrain.getLatitude());
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
            personne.setDate_affect_parrain(LocalDate.now());

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

        Double minDistance = Double.MAX_VALUE;

        for (Agent agent : agents) {
            Double distance = calculateDistance(personne.getLogitude(), personne.getLatitude(), agent.getLogitude(), agent.getLatitude());
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






         @Override
        public int calculateScore(Long profilId) {
            Profil profil = profilRepository.findById(profilId)
                    .orElseThrow(() -> new RuntimeException("Profil non trouvé avec l'ID : " + profilId));
             int maxScore = 20;
             int score = 0;
             Set<FeedBack> feedbacks = profil.getFeedBacks();
             boolean hasFeedback = !feedbacks.isEmpty();


             // Vérification de la discipline
             if (hasFeedback) {
                 Discipline discipline = profil.getProfilpresonne().getDiscipline();
                 if (discipline == Discipline.SOUPLE) {
                     score += 7;
                 } else if (discipline == Discipline.ORDINAIRE) {
                     score += 4;
                 } else if (discipline == Discipline.COMPLIQUEE) {
                     score += 1;
                 }

                 // Vérification des feedbacks d'activité
                 Set<FeedBack> feedbackss = profil.getFeedBacks();
                 int agentFeedbackCount = 0;
                 int parrainFeedbackCount = 0;

                 for (FeedBack feedback : feedbackss) {
                     LocalDate feedbackDate = feedback.getDateajoutFeed();
                     if (feedbackDate.getYear() == LocalDate.now().getYear()) {
                         if (feedback.getIdAgent() != null) {
                             agentFeedbackCount++;
                             score += getFeedbackScore(feedback.getFeedactivite());
                         }
                         if (feedback.getIdParrain() != null) {
                             parrainFeedbackCount++;
                             score += getFeedbackScore(feedback.getFeedactivite());
                         }
                     }
                 }

                 // Vérification des documents
                 Set<Document> documents = profil.getDocuments();
                 for (Document document : documents) {
                     if (document.getTypedocument() == TypeDocument.B3 && document.getValeur()) {
                         score += 3;
                     }
                     if (document.getTypedocument() == TypeDocument.ANTECEDENT_PSYCHIATRIQUE && !document.getValeur()) {
                         score += 2;
                     }
                 }
             }
             else if (!hasFeedback) {
                 Discipline discipline = profil.getProfilpresonne().getDiscipline();
                 if (discipline == Discipline.SOUPLE) {
                     score += 9;
                 } else if (discipline == Discipline.ORDINAIRE) {
                     score += 6;
                 } else if (discipline == Discipline.COMPLIQUEE) {
                     score += 3;
                 }


                 // Vérification des documents
                 Set<Document> documents = profil.getDocuments();
                 for (Document document : documents) {
                     if (document.getTypedocument() == TypeDocument.B3 && document.getValeur()) {
                         score += 6;
                     }
                     if (document.getTypedocument() == TypeDocument.ANTECEDENT_PSYCHIATRIQUE && !document.getValeur()) {
                         score += 5;
                     }
                 }
             }


              profil.setScore(score);
             if(score<15){
                 profil.setClassification(Classification.NOT_READY);
             }else {
                 profil.setClassification(Classification.READY);
             }
profilRepository.save(profil);
             return score;
         }

    private int getFeedbackScore(TypeFeedback feedback) {
        switch (feedback) {
            case TURBULENT:
                return 1;
            case ANTI_SOCIAL:
                return 2;
            case ACCEPTABLE:
                return 3;
            case PARFAIT:
                return 4;
            default:
                return 0;
        }
    }




        @Override
        public void affecterProgrammeSelonScore(Long personneId) {
            Personne personne = personneRepository.findById(personneId)
                    .orElseThrow(() -> new NotFoundException("Personne non trouvée"));

           Profil profil = personne.getProfil();
           if(profil.getScore()>15){
               Set<Programme> programmes = new HashSet<>();

               List<Programme> programmesJournaliers = programmeRepository.findByFrequence(Frequence.JOURNALIERE);
               List<Programme> programmesHebdomadaires = programmeRepository.findByFrequence(Frequence.HEBDOMADAIRE);
               List<Programme> programmesMensuels = programmeRepository.findByFrequence(Frequence.MENSUEL);

               if (!programmesJournaliers.isEmpty()) {
                   Collections.shuffle(programmesJournaliers);
                   programmes.add(programmesJournaliers.get(0));
               }
               if (!programmesHebdomadaires.isEmpty()) {
                   Collections.shuffle(programmesHebdomadaires);
                   programmes.add(programmesHebdomadaires.get(0));
               }
               if (!programmesMensuels.isEmpty()) {
                   Collections.shuffle(programmesMensuels);//nkhalwedh liste des prog bch mbaad nakhtar awal wehed get(0) d'une façon random
                   programmes.add(programmesMensuels.get(0));
               }

               personne.setProgrammes(programmes);
           }else {
               personne.setProgrammes(Collections.emptySet());
           }

            personneRepository.save(personne);
        }





        public Profil archiveProfil(Long profilId) {
        //utiliser optional  pour éviter les problèmes de valeurs null et les exceptions NullPointerException.
            Optional<Profil> optionalProfil = profilRepository.findById(profilId);

            if (optionalProfil.isPresent()) {
                Profil profil = optionalProfil.get();
                profil.setArchived(true);
                return profilRepository.save(profil);
            }

            return null;
        }

        public Profil unarchiveProfil(Long profilId) {
            Optional<Profil> optionalProfil = profilRepository.findById(profilId);

            if (optionalProfil.isPresent()) {
                Profil profil = optionalProfil.get();
                profil.setArchived(false);
                return profilRepository.save(profil);
            }

            return null;
        }
@Override
    public Parrain getProfilParrain(Long profilId) {
        return profilRepository.findParrainByProfilId(profilId);
    }
    @Override
    public Agent getProfilAgent(Long profilId) {
        return profilRepository.findAgentByProfilId(profilId);
    }






}
