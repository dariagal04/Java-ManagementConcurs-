package src.mpp2024.repo.DB;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import src.mpp2024.domain.Participant;
import src.mpp2024.repo.DB.HibernateUtils;
import src.mpp2024.repo.Interfete.IParticipantRepo;

import java.util.List;

public class ParticipantHibernateDBRepo implements IParticipantRepo {

    @Override
    public boolean saveEntity(Participant participant) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(participant);
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
            Participant participant = session.get(Participant.class, id);
            if (participant != null) {
                session.delete(participant);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateEntity(Participant entity) {
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

    @Override
    public List<Participant> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Participant", Participant.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Participant getOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(Participant.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Participant getParticipantByName(String name) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Participant> query = session.createQuery("from Participant where nume = :name", Participant.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Participant> getParticipantsByProba(int proba) {
        return List.of();
    }

    @Override
    public List<Participant> getParticipantsByCategory(int IdCategorie) {
        return List.of();
    }

    @Override
    public Participant getParticipantByCNP(String cnp) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Participant> query = session.createQuery("from Participant where cnp = :cnp", Participant.class);
            query.setParameter("cnp", cnp);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isCnpExists(String cnp) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Long count = session.createQuery("select count(*) from Participant where cnp = :cnp", Long.class)
                    .setParameter("cnp", cnp)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
