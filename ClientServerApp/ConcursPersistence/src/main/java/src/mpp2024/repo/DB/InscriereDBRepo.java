package src.mpp2024.repo.DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.domain.CategorieVarsta;
import src.mpp2024.domain.Inscriere;
import src.mpp2024.domain.NumeProba;
import src.mpp2024.domain.Participant;
import src.mpp2024.repo.Interfete.IInscriereRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InscriereDBRepo implements IInscriereRepo {



    JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public InscriereDBRepo(Properties props) {
        logger.info("Initializing InscriereDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public List<Inscriere> getAll() {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"inscriere_proba\"")) {
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Inscriere> inscrieri = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idParticipant = resultSet.getInt("participant_id");
                int idProba = resultSet.getInt("proba_id");
                int idCategorie = resultSet.getInt("categorie_id");
                Inscriere inscriere = new Inscriere(idParticipant, idProba,idCategorie);
                inscrieri.add(inscriere);
            }
            return inscrieri;
        } catch (SQLException e) {
            return null;

        }
    }

    @Override
    public Inscriere getOne(Integer integer) {
        return null;
    }

    @Override
    public boolean saveEntity(Inscriere entity) {

        if (isParticipantInscribedToTwoEvents(entity.getIdParticipant())) {
            logger.warn("Participant is already inscribed to 2 events.");
            return false;
        }

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO \"inscriere_proba\" (participant_id, proba_id, categorie_id) VALUES (?,?,?)")) {

            ps.setInt(1, entity.getIdParticipant());
            ps.setInt(2, entity.getIdProba());
            ps.setInt(3, entity.getIdCategorie());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            logger.error("Eroare la inserarea inscrierii: " + e.getMessage(), e);
            return false;
        }
    }


    @Override
    public boolean deleteEntity(Integer integer) {
        return false;
    }


    public boolean deleteEntity(int idParticipant, int idProba, int idCategorie) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM \"inscriere_proba\" WHERE participant_id=? AND proba_id=? AND categorie_id=?")) {
            ps.setInt(0, idParticipant);
            ps.setInt(1, idProba);
            ps.setInt(2, idCategorie);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateEntity(Inscriere entity) {
        return false;
    }

    @Override
    public Inscriere getInscriereByParticipantAndProbaAndCategorie(int idParticipant, int idProba, int idCategorie) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"inscriere_proba\" WHERE \"participant_id\" = ? AND \"proba_id\"= ? AND \"categorie_id\" = ? ")) {
            ps.setInt(0, idParticipant);
            ps.setInt(1, idProba);
            ps.setInt(2, idCategorie);
            ResultSet resultSet = ps.executeQuery();


            Inscriere inscriere = new Inscriere(idParticipant, idProba,idCategorie);
            return inscriere;
        } catch (SQLException e) {
            return null;
        }
    }

    public int getNumRegistrations(Integer idProba) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT COUNT(\"participant_id\") FROM \"inscriere_proba\" WHERE \"proba_id\"= " + String.valueOf(idProba))) {
//            ps.setInt(0, idProba.intValue());
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return -1;
        }
    }

    public List<Inscriere> getInscrieriByParticipantId(Integer idParticipant) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"inscriere_proba\" WHERE \"participant_id\"= ?")){
            ps.setInt(1, idParticipant.intValue());
            ResultSet resultSet = ps.executeQuery();
            List<Inscriere> inscrieri = new ArrayList<>();
            while(resultSet.next()){
                int idProba = resultSet.getInt("proba_id");
                int idCategorie = resultSet.getInt("categorie_id");
                Inscriere inscriere = new Inscriere(idParticipant, idProba, idCategorie);
                inscrieri.add(inscriere);
            }
            return inscrieri;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }


    public Map<String, Map<String, Integer>> getCompetitionsWithParticipants() {
        Map<String, Map<String, Integer>> result = new HashMap<>();

        String sql = "SELECT p.nume AS proba, c.varsta_min, c.varsta_max, \n" +
                "       COUNT(i.id) AS numar_inscrisi \n" +
                "FROM nume_probe p\n" +
                "JOIN categorii c ON 1=1\n" +
                "LEFT JOIN inscriere_proba i ON i.proba_id = p.id AND i.categorie_id = c.id\n" +
                "GROUP BY p.nume, c.varsta_min, c.varsta_max;\n";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String proba = rs.getString("proba");
                String categorie = String.format("%d-%d", rs.getInt("varsta_min"), rs.getInt("varsta_max"));
                int numarInscrisi = rs.getInt("numar_inscrisi");

                result.putIfAbsent(proba, new HashMap<>());
                result.get(proba).put(categorie, numarInscrisi);
            }
        } catch (SQLException e) {
            logger.error("Error fetching competitions with participants: ", e);
        }

        return result;
    }

    public List<Participant> getParticipantsByProbaAndCategorie(NumeProba proba, CategorieVarsta categorie) {
        List<Participant> participants = new ArrayList<>();

        String intervalVarsta = categorie.getVarstaMinima() + "-" + categorie.getVarstaMaxima();

        String sql = "SELECT p.id, p.nume, p.varsta, p.cnp, p.persoanaOficiu_id FROM inscriere_proba i " +
                "JOIN nume_probe np ON i.proba_id = np.id " +
                "JOIN categorii c ON i.categorie_id = c.id " +
                "JOIN participanti p ON i.participant_id = p.id " +
                "WHERE np.nume = ? AND c.id = ?";



        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, proba.getNumeProba());
//            ps.setInt(2, categorie.getVarstaMinima());
//            ps.setInt(3, categorie.getVarstaMaxima());
            ps.setInt(2, categorie.getId());


            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                int varsta = rs.getInt("varsta");
                String cnp = rs.getString("cnp");
                int idPersoanaOficiu = rs.getInt("persoanaOficiu_id");

                participants.add(new Participant(id,nume, varsta, cnp, idPersoanaOficiu));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return participants;
    }


    public boolean isCnpExists(String cnp) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM \"participanti\" WHERE cnp = ?")) {
            ps.setString(1, cnp);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            logger.error("Error checking CNP: ", e);
            return false;
        }
    }

    public boolean isParticipantInscribedToTwoEvents(int idParticipant) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM \"inscriere_proba\" WHERE participant_id = ?")) {
            ps.setInt(1, idParticipant);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) >= 2;
            }
            return false;
        } catch (SQLException e) {
            logger.error("Error checking participant inscriptions: ", e);
            return false;
        }
    }


}
