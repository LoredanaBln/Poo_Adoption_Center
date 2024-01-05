package app.service;

import app.model.Address;
import app.model.Adoption;
import app.model.Animal;

import java.util.List;

public interface AddressService {
    Address save(Address address);

    Address update(Address address);

    List<Address> findAll();

    Address findById(Integer id);

    boolean delete(Address address);
    List <Adoption> getAdoptionsFromAddress(Integer addressID);
}
