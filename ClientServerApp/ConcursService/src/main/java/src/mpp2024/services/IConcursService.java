package src.mpp2024.services;

import src.mpp2024.domain.*;

import java.util.List;
import java.util.Map;

public interface IConcursService {

    /**
     * Autentifică o persoană de la oficiu.
     *
     * @param username Numele de utilizator
     * @param password Parola
     * @return Persoana autentificată
     * @throws Exception dacă autentificarea eșuează
     */
    PersoanaOficiu login(String username, String password) throws Exception;

    /**
     * Returnează toate înscrierile pentru o probă dată.
     *
     * @param probaId ID-ul probei
     * @return Lista cu înscrierile
     * @throws Exception dacă apare o eroare la accesarea datelor
     */
    List<Inscriere> getInscrieriProba(int probaId) throws Exception;

    Map<String, Map<String, Integer>> getCompetitionsWithParticipants();

    NumeProba getNumeProba(String selectedProba);

    CategorieVarsta getCategorie(int i);

    List<Participant> getParticipantsByProbaAndCategorie(NumeProba proba, CategorieVarsta categorie);

    CategorieVarsta getCategorieVarstaByAge(int varsta);

    Participant getParticipantByCNP(String cnp);

    boolean saveEntity(Participant p);

    boolean saveEntityi(Inscriere i);

    void logout(PersoanaOficiu persoanaOficiu, IConcursObserver oficiuLoggedIn);

    // Alte metode pot fi adăugate, de exemplu:
    // void inscriereCopil(Copil copil, List<Proba> probe) throws Exception;
    // List<Proba> getProbeDisponibile();
    // List<Categorie> getCategoriiDisponibile();
}
