package src.mpp2024.service;

import src.mpp2024.domain.*;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursObserver;
import src.mpp2024.services.IConcursService;

import java.util.List;
import java.util.Map;

public class Services implements IConcursService {

    private final CategorieService categorieService;
    private final InscriereService inscriereService;
    private final NumeProbaService numeProbaService;
    private final ParticipantiService participantiService;
    private final PersoanaOficiuService persoanaOficiuService;

    public Services(CategorieService categorieService,
                          InscriereService inscriereService,
                          NumeProbaService numeProbaService,
                          ParticipantiService participantiService,
                          PersoanaOficiuService persoanaOficiuService) {
        this.categorieService = categorieService;
        this.inscriereService = inscriereService;
        this.numeProbaService = numeProbaService;
        this.participantiService = participantiService;
        this.persoanaOficiuService = persoanaOficiuService;
    }

    // Serviciu pentru a obține categoria pe baza unui ID
    public CategorieVarsta getCategorie(int id) {
        return categorieService.getCategorie(id);
    }

    // Serviciu pentru a obține un participant după CNP
    @Override
    public Participant getParticipantByCNP(String cnp) {
        return participantiService.getParticipantByCNP(cnp);
    }

    // Obține lista de participanți pe baza probei și categoriei
    @Override
    public List<Participant> getParticipantsByProbaAndCategorie(NumeProba proba, CategorieVarsta categorie) {
        return inscriereService.getParticipantsByProbaAndCategorie(proba, categorie);
    }

    // Funcție pentru a obține categoria de vârstă pe baza unei vârste
    @Override
    public CategorieVarsta getCategorieVarstaByAge(int varsta) {
        return categorieService.getCategorieVarstaByAge(varsta);
    }

    @Override
    public boolean saveEntity(Participant p) {
        return participantiService.saveEntity(p);
    }

    @Override
    public boolean saveEntityi(Inscriere i) {
        return inscriereService.saveEntityi(i);
    }

    // Funcții de autentificare
    @Override
    public void login(PersoanaOficiu persoanaOficiu, IConcursObserver client) throws ConcursException {
        persoanaOficiuService.login(persoanaOficiu, client);
    }

    @Override
    public PersoanaOficiu getPersoanaOficiuByUsernamePassword(String username, String password) throws ConcursException {
        return persoanaOficiuService.getPersoanaOficiuByUsernamePassword(username, password);
    }

    @Override
    public void logout(PersoanaOficiu persoanaOficiu, IConcursObserver client) throws ConcursException {
        persoanaOficiuService.logout(persoanaOficiu, client);
    }

    @Override
    public Map<String, Map<String, Integer>> getCompetitionsWithParticipants() {
        return inscriereService.getCompetitionsWithParticipants();
    }

    @Override
    public NumeProba getNumeProba(String selectedProba) {
        return numeProbaService.getNumeProba(selectedProba);
    }
}
