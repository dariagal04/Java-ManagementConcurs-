package src.mpp2024.repo.DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.domain.PersoanaOficiu;
import src.mpp2024.repo.Interfete.IPersoanaOficiuRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class PersoanaOficiuDBRepo implements IPersoanaOficiuRepo {
    JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();


    public PersoanaOficiuDBRepo(Properties props) {
        logger.info("Initializing PersoanaOficiuDBRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
//    public PersoanaOficiu getOneByUsername(String username) {
//        try (Connection connection = dbUtils.getConnection();
//             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"persoana_oficiu\" WHERE username=?")) {
//            ResultSet resultSet = ps.executeQuery();
//            resultSet.next();
//            String password = resultSet.getString("password");
//            int id = resultSet.getInt("id");
//            String locatie = resultSet.getString("locatie_oficiu");
//            PersoanaOficiu persoanaOficiu = new PersoanaOficiu(id, username,password, locatie);
////                Log.d("tag", "restul");
//
//
//            return persoanaOficiu;
//        } catch (SQLException e) {
////            throw new RuntimeException(e);
////            Log.E("ErRROR","ERR");
//            return null;
//        }
//    }

    public PersoanaOficiu getOneByUsername(String username) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"persoana_oficiu\" WHERE username=?")) {

            ps.setString(1, username);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String password = resultSet.getString("password");
                    String locatie = resultSet.getString("locatie_oficiu");

                    return new PersoanaOficiu(id, username, password, locatie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<PersoanaOficiu> getAll() {
        return null;
    }

    @Override
    public PersoanaOficiu getOne(Integer integer) {
        return null;
    }

    @Override
    public boolean saveEntity(PersoanaOficiu entity) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO \"persoana_oficiu\" (\"id\", \"username\",\"password\",\"locatie_oficiu\") VALUES(?,?,?,?)")) {
            ps.setInt(1, entity.getId());
            ps.setString(2, entity.getUsername());
            ps.setString(3, entity.getPassword());
            ps.setString(4, entity.getLocatie_oficiu());
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
    public boolean deleteEntity(Integer integer) {
        return false;
    }

    @Override
    public boolean updateEntity(PersoanaOficiu entity) {
        return false;
    }
}
