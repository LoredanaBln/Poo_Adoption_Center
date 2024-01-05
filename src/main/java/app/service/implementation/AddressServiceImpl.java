package app.service.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Address;
import app.model.Adopter;
import app.model.Adoption;
import app.model.Animal;
import app.repository.AddressRepository;
import app.service.AddressService;
import app.single_point_access.RepositorySinglePointAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AddressServiceImpl implements AddressService {

    AddressRepository addressRepository = RepositorySinglePointAccess.getAddressRepository();

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address update(Address address) {
        return addressRepository.update(address);
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(Integer id) {
        return addressRepository.findById(id);
    }

    @Override
    public boolean delete(Address address) {
        return addressRepository.delete(address);
    }

    @Override
    public List<Adoption> getAdoptionsFromAddress(Integer addressID) {
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                TypedQuery<Adoption> query = session.createQuery(
                        "FROM Adoption a WHERE a.adopter.address.id = :addressId",
                        Adoption.class
                );
                query.setParameter("addressId", addressID);

                List<Adoption> adoptionsFromAddress = query.getResultList();
                transaction.commit();
                return adoptionsFromAddress;
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Error getting adoptions from address", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error opening/closing session", e);
        }
    }

}
