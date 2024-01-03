package app.repository.implemetation;

import app.configuration.HibernateConfiguration;
import app.model.Animal;
import app.repository.AnimalRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        // TO DO
        return null;
    }

    @Override
    public Animal findById(Integer id) {
        // TO DO
        return null;
    }

    @Override
    public List<Animal> findAll() {
        // TO DO
        return null;
    }

    @Override
    public boolean delete(Animal entity) {
        // TO DO
        return false;
    }
}
