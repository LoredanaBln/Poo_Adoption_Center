package app.repository.implemetation;

import app.configuration.HibernateConfiguration;
import app.model.Adopter;
import app.repository.AdopterRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class AdopterRepositoryImpl implements AdopterRepository {
    @Override
    public Adopter save(Adopter entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer idOnPersonSaved = (Integer) session.save(entity);

        transaction.commit();
        session.close();

        return findById(idOnPersonSaved);
    }

    @Override
    public Adopter update(Adopter entity) {
        // TO DO
        // Same logic - extract it somehow
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(entity);
            Integer id = entity.getId();
            transaction.commit();
            return findById(id);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating Adopter", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Adopter> findAll() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Native SQL - not preferred
        Query query = session.createSQLQuery("select * from user");

        //TypedQuery<Adopter> query = session.getNamedQuery("findAllUsers");

        List<Adopter> adopters = query.getResultList();

        transaction.commit();
        session.close();

        return adopters;
    }

    @Override
    public Adopter findById(Integer id) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // HQL - Hibernate Query Language, but use named query instead to reuse them
        Query query = session.createQuery("from Adopter where id=:id");
        query.setParameter("id", id);

//        TypedQuery<Adopter> query = session.getNamedQuery("findPersonById");
//        query.setParameter("id", id);

        Adopter adopter;
        try {
            adopter = (Adopter) query.getSingleResult();
        } catch (NoResultException e) {
            adopter = null;
        }

        transaction.commit();
        session.close();

        return adopter;
    }


    @Override
    public boolean delete(Adopter entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer id = entity.getId();
        session.delete(entity);

        transaction.commit();
        session.close();

        return findById(id) == null;
    }

    @Override
    public Adopter findByName(String name) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Used a Named Query - best solution
        TypedQuery<Adopter> query = session.getNamedQuery("findUserByName");
        query.setParameter("name", name);
        Adopter adopter;
        try {
            adopter = query.getSingleResult();
        } catch (NoResultException e) {
            adopter = null;
        }


        transaction.commit();
        session.close();

        return adopter;
    }

    @Override
    public Adopter findByNameAndPassword(String name, String password) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            TypedQuery<Adopter> query = session.getNamedQuery("findUserByNameAndPassword");
            query.setParameter("name", name);
            query.setParameter("password", password);

            Adopter adopter;
            try {
                adopter = query.getSingleResult();
            } catch (NoResultException e) {
                adopter = null;
            }

            transaction.commit();
            return adopter;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding Adopter by name and password", e);
        } finally {
            session.close();
        }
    }
}
