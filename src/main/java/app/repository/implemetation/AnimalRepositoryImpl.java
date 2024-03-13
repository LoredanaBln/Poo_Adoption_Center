package app.repository.implemetation;

import app.configuration.HibernateConfiguration;
import app.model.Animal;
import app.model.Staff;
import app.repository.AnimalRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;
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

        // HQL - Hibernate Query Language, but use named query instead to reuse them
//        Query query = session.createQuery("from Animal animal where animal.id=:id");
//        query.setParameter("id", id);

        TypedQuery<Animal> query = session.getNamedQuery("findAnimalById");
        query.setParameter("id", id);

        Animal animal;
        try {
            animal = (Animal)query.getSingleResult();
        } catch (NoResultException e) {
            animal = null;
        }

        transaction.commit();
        session.close();

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
