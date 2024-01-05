package app.repository.implemetation;

import app.configuration.HibernateConfiguration;
import app.model.Animal;
import app.repository.AnimalRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class AnimalRepositoryImpl implements AnimalRepository {

    @Override
    public Animal save(Animal entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer idOnCarSaved = (Integer) session.save(entity);

        transaction.commit();
        session.close();

        return findById(idOnCarSaved);
    }

    @Override
    public Animal update(Animal entity) {
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
            throw new RuntimeException("Error updating Animal", e);
        } finally {
            session.close();
        }

        return entity;
    }

    @Override
    public Animal findById(Integer id) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Animal animal;
        try {
            TypedQuery<Animal> query = session.createNamedQuery("findAnimalById", Animal.class);
            query.setParameter("id", id);
            animal = query.getSingleResult();
        } catch (NoResultException e) {
            animal = null;
        } catch (Exception e) {
            throw new RuntimeException("Error finding Animal by ID", e);
        } finally {
            transaction.commit();
            session.close();
        }
        return animal;
    }

    @Override
    public List<Animal> findAll() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            TypedQuery<Animal> query = session.createQuery("from Animal", Animal.class);
            List<Animal> animals = query.getResultList();
            transaction.commit();
            return animals;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding all Animals", e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Animal entity) {
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
            throw new RuntimeException("Error deleting Animal", e);
        } finally {
            session.close();
        }

        return findById(entity.getId()) == null;
    }


}
