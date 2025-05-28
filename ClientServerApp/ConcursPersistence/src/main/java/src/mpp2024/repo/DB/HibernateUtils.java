package src.mpp2024.repo.DB;

import src.mpp2024.domain.Participant;
import src.mpp2024.domain.PersoanaOficiu;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.management.relation.Role;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory == null) || (sessionFactory.isClosed()))
            sessionFactory = createNewSessionFactory();
        return sessionFactory;
    }

    private static SessionFactory createNewSessionFactory() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(PersoanaOficiu.class)
                .addAnnotatedClass(Participant.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static void closeSessionFactory(){
        if (sessionFactory != null)
            sessionFactory.close();
    }
}