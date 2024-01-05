package app.service.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Address;
import app.model.Adopter;
import app.model.Adoption;
import app.model.Animal;
import app.repository.AdoptionRepository;
import app.service.AdoptionService;
import app.single_point_access.RepositorySinglePointAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class AdoptionServiceImpl implements AdoptionService {

    private AdoptionRepository adoptionRepository = RepositorySinglePointAccess.getAdoptionRepository();

    @Override
    public Adoption save(Adoption adoption) {
        return adoptionRepository.save(adoption);
    }

    @Override
    public Adoption update(Adoption adoption) {
        return adoptionRepository.update(adoption);
    }

    @Override
    public List<Adoption> findAll() {
        return adoptionRepository.findAll();
    }

    @Override
    public Adoption findById(Integer id) {
        return adoptionRepository.findById(id);
    }

    @Override
    public boolean delete(Adoption adoption) {
        return adoptionRepository.delete(adoption);
    }

    @Override
    public List<Adoption> getAdoptionsByDateRange(Date startDate, Date endDate) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            TypedQuery<Adoption> query = session.createQuery(
                    "from Adoption where date between :startDate and :endDate", Adoption.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);

            List<Adoption> adoptions = query.getResultList();
            transaction.commit();
            return adoptions;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding adoptions by date range", e);
        } finally {
            session.close();
        }
    }


}
