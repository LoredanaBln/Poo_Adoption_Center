package app.repository.implemetation;

import app.configuration.HibernateConfiguration;
import app.model.Staff;
import app.repository.StaffRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class StaffRepositoryImpl implements StaffRepository {
    @Override
    public Staff save(Staff entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer idOnSavedStaff = (Integer) session.save(entity);

        transaction.commit();
        session.close();

        return findById(idOnSavedStaff);
    }

    @Override
    public Staff update(Staff entity) {
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
            throw new RuntimeException("Error updating Staff", e);
        } finally {
            session.close();
        }

        return entity;
    }

    @Override
    public Staff findById(Integer id) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Staff staff;
        try {
            TypedQuery<Staff> query = session.getNamedQuery("findStaffById");
            query.setParameter("id", id);
            staff = query.getSingleResult();
        } catch (NoResultException e) {
            staff = null;
        } catch (Exception e) {
            throw new RuntimeException("Error finding Staff by ID", e);
        } finally {
            transaction.commit();
            session.close();
        }

        return staff;
    }

    @Override
    public List<Staff> findAll() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            TypedQuery<Staff> query = session.createQuery("from Staff", Staff.class);
            List<Staff> staffMembers = query.getResultList();
            transaction.commit();
            return staffMembers;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error finding all Staff members", e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Staff entity) {
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
            throw new RuntimeException("Error deleting Staff", e);
        } finally {
            session.close();
        }

        return findById(entity.getId()) == null;
    }
}
