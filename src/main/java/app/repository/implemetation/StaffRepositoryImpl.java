package app.repository.implemetation;

import app.configuration.HibernateConfiguration;
import app.model.Adopter;
import app.model.Staff;
import app.repository.StaffRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;
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

        // HQL - Hibernate Query Language, but use named query instead to reuse them
//        Query query = session.createQuery("from Adopter adopter where adopter.id=:id");
//        query.setParameter("id", id);

        TypedQuery<Staff> query = session.getNamedQuery("findStaffById");
        query.setParameter("id", id);

        Staff staff;
        try {
            staff = (Staff) query.getSingleResult();
        } catch (NoResultException e) {
            staff = null;
        }

        transaction.commit();
        session.close();

        return staff;
    }

    @Override
    public List<Staff> findAll() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Native SQL - select specific columns
        Query query = session.createSQLQuery("select id, name, phone, address_id from staff")
                .addEntity(Staff.class);


        List<Staff> staffs = query.getResultList();

        transaction.commit();
        session.close();

        return staffs;
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
