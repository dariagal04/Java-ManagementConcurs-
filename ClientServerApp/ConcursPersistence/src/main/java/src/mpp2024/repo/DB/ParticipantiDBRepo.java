package src.mpp2024.repo.DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.domain.Participant;
import src.mpp2024.repo.Interfete.IParticipantRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantiDBRepo implements IParticipantRepo {

    JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();


    public ParticipantiDBRepo(Properties props) {
        logger.info("Initializing ParticipantiDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public boolean saveEntity(Participant entity) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO \"participanti\" (\"nume\",\"varsta\", \"cnp\", \"persoanaOficiu_id\") VALUES(?, ?,?,?)")) {
            ps.setString(1, entity.getNume());
            ps.setInt(2, entity.getVarsta());
            ps.setString(3, entity.getCnp());
            ps.setInt(4,entity.getIdPersoanaOficiu());
            ps.executeUpdate();
//                Log.d("tag", "restul");
            return true;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return false;
        }
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM \"participanti\" WHERE \"id\" = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
//                Log.d("tag", "restul");
            return true;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return false;
        }
    }

    @Override
    public boolean updateEntity(Participant entity) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE \"participanti\" SET \"nume\" = ?,  \"varsta\" = ? ,\"cnp\", \"persoanaOficiu_id\" WHERE \"id\" = ?")) {
            ps.setString(2, entity.getNume());
            ps.setInt(3, entity.getVarsta());
            ps.setInt(1, entity.getId());
            ps.setString(4, entity.getCnp());
            ps.setInt(5, entity.getIdPersoanaOficiu());
            ps.executeUpdate();
//                Log.d("tag", "restul");
            return true;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return false;
        }
    }

    @Override
    public List<Participant> getAll() {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"participanti\"")) {
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Participant> participants = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("nume");
                int age = resultSet.getInt("varsta");
                String cnp = resultSet.getString("cnp");
                int persoanaOficiuId = resultSet.getInt("persoanaOficiu_id");
                Participant participant = new Participant(id,name, age,cnp,persoanaOficiuId);
//                Log.d("tag", "restul");
                participants.add(participant);
            }
            return participants;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

    @Override
    public Participant getOne(Integer id) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"participanti\" WHERE id=?")) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            String name = resultSet.getString("nume");
            int age = resultSet.getInt("varsta");
            String cnp = resultSet.getString("cnp");
            int persoanaOficiuId = resultSet.getInt("persoanaOficiu_id");
            Participant participant = new Participant(id,name, age,cnp,persoanaOficiuId);
//                Log.d("tag", "restul");


            return participant;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

    @Override
    public Participant getParticipantByName(String name) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"participanti\" WHERE \"nume\"=?")) {
            ps.setString(2, name);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            Integer id = resultSet.getInt("id");
            int age = resultSet.getInt("varsta");
            String cnp = resultSet.getString("cnp");
            int persoanaOficiuId = resultSet.getInt("persoanaOficiu_id");
            Participant participant = new Participant(id,name, age,cnp,persoanaOficiuId);
//                Log.d("tag", "restul");


            return participant;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

   // @Override
    public Participant getParticipantByCNP(String cnp) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"participanti\" WHERE \"cnp\"=?")) {
            ps.setString(1,cnp);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            Integer id = resultSet.getInt("id");
            int age = resultSet.getInt("varsta");
            String name = resultSet.getString("nume");
            int persoanaOficiuId = resultSet.getInt("persoanaOficiu_id");
            Participant participant = new Participant(id,name, age,cnp,persoanaOficiuId);
//                Log.d("tag", "restul");


            return participant;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }



    public List<Participant> getParticipantsByProba(int idProba) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT \"P\".\"id\", \"P\".\"nume\", \"P\".\"varsta\",\"P\".\"cnp\",\"P\".\"persoanaOficiu_id\" FROM \"participanti\" \"P\" INNER JOIN \"inscriere_proba\" ON \"P\".\"id\"=\"inscriere_proba\".\"participant_id\" WHERE \"inscriere_proba\".\"proba_id\"=?")) {
            ps.setInt(3, idProba);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Participant> participants = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("nume");
                int age = resultSet.getInt("varsta");
                String cnp = resultSet.getString("cnp");
                int persoanaOficiuId = resultSet.getInt("persoanaOficiu_id");
                Participant participant = new Participant(id,name, age,cnp,persoanaOficiuId);
                participants.add(participant);
            }
            return participants;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            logger.error("Error", e);
        }
        return null;
    }

    public List<Participant> getParticipantsByCategory(int idCategory) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT \"P\".\"id\", \"P\".\"nume\", \"P\".\"varsta\",\"P\".\"cnp\",\"P\".\"persoanaOficiu_id\" FROM \"participanti\" \"P\" INNER JOIN \"inscriere_proba\" ON \"P\".\"id\"=\"inscriere_proba\".\"participant_id\" WHERE \"inscriere_proba\".\"categorie_id\"=?")) {
            ps.setInt(4, idCategory);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Participant> participants = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("nume");
                int age = resultSet.getInt("varsta");
                String cnp = resultSet.getString("cnp");
                int persoanaOficiuId = resultSet.getInt("persoanaOficiu_id");
                Participant participant = new Participant(id,name, age,cnp,persoanaOficiuId);
                participants.add(participant);
            }
            return participants;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            logger.error("Error", e);
        }
        return null;
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




}
