package app.service.performance;

import app.configuration.HibernateConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;

import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLProcedurePerformanceService implements PerformanceService {
    @Override
    @Transactional
    public void applyLogicOnUsers() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        StoredProcedureQuery query = session.createStoredProcedureQuery("logic_cinema");
        query.execute();
    }
}
