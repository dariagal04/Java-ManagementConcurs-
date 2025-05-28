package src.mpp2024.repo.DB;

import org.hibernate.Session;
import org.hibernate.Transaction;
import src.mpp2024.domain.PersoanaOficiu;
import src.mpp2024.repo.Interfete.IPersoanaOficiuRepo;
import src.mpp2024.repo.DB.HibernateUtils;

import java.util.List;

public class PersoanaOficiuHibernateDBRepo implements IPersoanaOficiuRepo {

    @Override
    public PersoanaOficiu getOneByUsername(String username) {
        Transaction transaction = null;
        PersoanaOficiu persoana = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            persoana = session.createQuery("from PersoanaOficiu where username = :username", PersoanaOficiu.class)
                    .setParameter("username", username)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return persoana;
    }

    @Override
    public List<PersoanaOficiu> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from PersoanaOficiu", PersoanaOficiu.class).list();
        }
    }

    @Override
    public PersoanaOficiu getOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(PersoanaOficiu.class, id);
        }
    }

    @Override
    public boolean saveEntity(PersoanaOficiu entity) {
        Transaction transaction = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEntity(Integer id) {
        Transaction transaction = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            PersoanaOficiu persoana = session.get(PersoanaOficiu.class, id);
            if (persoana != null) {
                session.delete(persoana);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateEntity(PersoanaOficiu entity) {
        Transaction transaction = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
