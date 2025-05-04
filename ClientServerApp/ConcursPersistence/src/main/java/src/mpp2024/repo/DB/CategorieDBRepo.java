package src.mpp2024.repo.DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.domain.CategorieVarsta;
import src.mpp2024.repo.Interfete.ICategorieVarstaRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CategorieDBRepo implements ICategorieVarstaRepo {

    JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();


    public CategorieDBRepo(Properties props) {
        logger.info("Initializing CategorieDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public List<CategorieVarsta> getAll() {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"categorii\"")) {
            ResultSet resultSet = ps.executeQuery();
            ArrayList<CategorieVarsta> categories = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int varstaMin = resultSet.getInt("varsta_min");
                int varstaMax = resultSet.getInt("varsta_max");
                CategorieVarsta category = new CategorieVarsta(id, varstaMin, varstaMax);
                logger.info("Successfully retrieved category with id " + id + " from database.");
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            logger.error("Error while retrieving categories from database.");
            return null;
        }
    }

    @Override
    public CategorieVarsta getOne(Integer id) {
    try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"categorii\" WHERE id=" + String.valueOf(id) + ";")) {
//            ps.setInt(0, id);
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
        int varstaMin = resultSet.getInt("varsta_min");
        int varstaMax = resultSet.getInt("varsta_max");
            CategorieVarsta category = new CategorieVarsta(id, varstaMin, varstaMax);
//                Log.d("tag", "restul");


            return category;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

    @Override
    public boolean saveEntity(CategorieVarsta entity) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO \"categoii\" (\"min\", \"max\") VALUES(?,?)")) {
            ps.setInt(0, entity.getVarstaMinima());
            ps.setInt(1, entity.getVarstaMaxima());
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
             PreparedStatement ps = connection.prepareStatement("DELETE FROM \"categorii\" WHERE id=?")) {
            ps.setInt(0, id);
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
    public boolean updateEntity(CategorieVarsta entity) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE \"categorii\" SET (\"min\"=?, \"max\"=?) WHERE id=?")) {
            ps.setInt(0, entity.getVarstaMinima());
            ps.setInt(1, entity.getVarstaMaxima());
            ps.setInt(2, entity.getId());
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
    public CategorieVarsta getCategorieVarstaByAgeGroup(int varstaMin, int varstaMax) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"categorii\" WHERE \"varsta_min\"=? AND \"varsta_max\"=?")) {
            ps.setInt(0, varstaMin);
            ps.setInt(1, varstaMax);
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            int id = resultSet.getInt("id");
            CategorieVarsta category = new CategorieVarsta(id, varstaMin, varstaMax);
//                Log.d("tag", "restul");


            return category;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

   // @Override
    public CategorieVarsta getCategorieVarstaByAge(int varsta) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT * FROM \"categorii\" WHERE \"varsta_min\"<=? AND \"varsta_max\">=?"
             )) {

            ps.setInt(1, varsta);
            ps.setInt(2, varsta);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) { // Verifică dacă există rezultate
                int id = resultSet.getInt("id");
                int varstaMin = resultSet.getInt("varsta_min"); // Extrage din baza de date
                int varstaMax = resultSet.getInt("varsta_max");

                return new CategorieVarsta(id, varstaMin, varstaMax);
            } else {
                System.out.println("⚠️ Nu există categorie pentru vârsta: " + varsta);
                return null; // Sau poți arunca o excepție dacă e mai potrivit
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }




}