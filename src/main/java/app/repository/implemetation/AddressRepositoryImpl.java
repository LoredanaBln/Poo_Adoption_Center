package app.repository.implemetation;

import app.configuration.HibernateConfiguration;
import app.model.Address;
import app.repository.AddressRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class AddressRepositoryImpl implements AddressRepository {
    @Override
    public Address save(Address entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer idOnSavedAddress = (Integer) session.save(entity);

        transaction.commit();
        session.close();

        return findById(idOnSavedAddress);
    }

    @Override
    public Address update(Address entity) {
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
            // Handle the exception
            throw new RuntimeException("Error updating Address", e);
        } finally {
            session.close();
        }

        return entity;
    }

    @Override
    public Address findById(Integer id) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Address address;
        try {
            TypedQuery<Address> query = session.getNamedQuery("findAddressById");
            query.setParameter("id", id);
            address = query.getSingleResult();
        } catch (NoResultException e) {
            address = null;
        } catch (Exception e) {
            throw new RuntimeException("Error finding Address by ID", e);
        } finally {
            transaction.commit();
            session.close();
        }

        return address;
    }

    @Override
    public List<Address> findAll() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            TypedQuery<Address> query = session.createQuery("from Address", Address.class);
            List<Address> addresses = query.getResultList();
            transaction.commit();
            return addresses;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding all Addresses", e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Address entity) {
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
            throw new RuntimeException("Error deleting Address", e);
        } finally {
            session.close();
        }

        return findById(entity.getId()) == null;
    }
}
