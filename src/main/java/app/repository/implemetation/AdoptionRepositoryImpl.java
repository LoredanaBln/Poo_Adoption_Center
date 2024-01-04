package app.repository.implemetation;

import app.configuration.HibernateConfiguration;
import app.model.Adoption;
import app.repository.AdoptionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class AdoptionRepositoryImpl implements AdoptionRepository {
    @Override
    public Adoption save(Adoption entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer idOnSavedAdoption = (Integer) session.save(entity);

        transaction.commit();
        session.close();

        return findById(idOnSavedAdoption);
    }

    @Override
    public Adoption update(Adoption entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating Adoption", e);
        } finally {
            session.close();
        }

        return entity;
    }

    @Override
    public Adoption findById(Integer id) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Adoption adoption;
        try {
            TypedQuery<Adoption> query = session.getNamedQuery("findAdoptionById");
            query.setParameter("id", id);
            adoption = query.getSingleResult();
        } catch (NoResultException e) {
            adoption = null;
        } catch (Exception e) {

            throw new RuntimeException("Error finding Adoption by ID", e);
        } finally {
            transaction.commit();
            session.close();
        }

        return adoption;
    }

    @Override
    public List<Adoption> findAll() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            TypedQuery<Adoption> query = session.createQuery("from Adoption", Adoption.class);
            List<Adoption> adoptions = query.getResultList();
            transaction.commit();
            return adoptions;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding all Adoptions", e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Adoption entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting Adoption", e);
        } finally {
            session.close();
        }

        return findById(entity.getId()) == null;
    }
}
