package src.mpp2024.services;

import src.mpp2024.domain.*;

import java.util.List;
import java.util.Map;

public interface IConcursService {


    void login(PersoanaOficiu persoanaOficiu,IConcursObserver client) throws ConcursException;

    PersoanaOficiu getPersoanaOficiuByUsernamePassword(String username, String password) throws ConcursException;
//    Map<String, Map<String, Integer>> getCompetitionsWithParticipants()throws ConcursException;
//
//    List<Participant> getParticipantsByProbaAndCategorie(NumeProba proba, CategorieVarsta categorie)throws ConcursException;


    void logout(PersoanaOficiu persoanaOficiu, IConcursObserver client)throws ConcursException;

    Map<String, Map<String, Integer>> getCompetitionsWithParticipants() throws ConcursException;

    NumeProba getNumeProba(String selectedProba) throws ConcursException;

    CategorieVarsta getCategorie(int i) throws ConcursException;

    List<Participant> getParticipantsByProbaAndCategorie(NumeProba proba, CategorieVarsta categorie) throws ConcursException;

    CategorieVarsta getCategorieVarstaByAge(int varsta)throws ConcursException;

    Participant getParticipantByCNP(String cnp) throws ConcursException;

    boolean saveEntity(Participant p)throws ConcursException;

    boolean saveEntityi(Inscriere i) throws ConcursException;
}
