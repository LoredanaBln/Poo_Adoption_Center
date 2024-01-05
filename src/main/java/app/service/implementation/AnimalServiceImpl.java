package app.service.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Animal;
import app.repository.AnimalRepository;
import app.service.AnimalService;
import app.single_point_access.RepositorySinglePointAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import java.util.List;

public class AnimalServiceImpl implements AnimalService {
    private AnimalRepository animalRepository = RepositorySinglePointAccess.getAnimalRepository();
    @Override
    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }

    @Override
    public Animal update(Animal animal) {
        return animalRepository.update(animal);
    }

    @Override
    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Override
    public Animal findById(Integer id) {
        return animalRepository.findById(id);
    }

    @Override
    public boolean delete(Animal animal) {
        return animalRepository.delete(animal);
    }

    @Override
    public List<Animal> findAllAvailableAnimals() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            TypedQuery<Animal> query = session.createQuery("from Animal animal where availability =: true", Animal.class);
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
}
