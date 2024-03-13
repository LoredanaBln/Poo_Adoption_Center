package app.service.implementation;

import app.model.*;
import app.repository.AddressRepository;
import app.repository.AnimalRepository;
import app.repository.AdopterRepository;
import app.repository.StaffRepository;
import app.service.AdopterService;
import app.single_point_access.RepositorySinglePointAccess;
import org.hibernate.Hibernate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdopterServiceImpl implements AdopterService {

    private AdopterRepository adopterRepository = RepositorySinglePointAccess.getUserRepository();
    private AnimalRepository animalRepository = RepositorySinglePointAccess.getAnimalRepository();
    private StaffRepository staffRepository = RepositorySinglePointAccess.getStaffRepository();
    private AddressRepository addressRepository = RepositorySinglePointAccess.getAddressRepository();


    @Override
    public Adopter save(Adopter adopter) {
        return adopterRepository.save(adopter);
    }

    @Override
    public Adopter update(Adopter adopter) {
        return adopterRepository.update(adopter);
    }

    @Override
    public List<Adopter> findAll() {
        return adopterRepository.findAll();
    }

    @Override
    public Adopter findById(Integer id) {
        return adopterRepository.findById(id);
    }

    @Override
    public boolean delete(Adopter adopter) {
        return adopterRepository.delete(adopter);
    }

    @Transactional
    @Override
    public void createAdoption(Staff staff, Adopter adopter, Animal animal, Date date) {
        if (adopter == null) {
            throw new IllegalArgumentException("Adopter cannot be null");
        }
        adopter = adopterRepository.findById(adopter.getId());
        if (adopter == null) {
            throw new RuntimeException("Adopter not found in the database");
        }
        if (staff == null || animal == null || date == null) {
            throw new IllegalArgumentException("Staff, Animal, or Date cannot be null");
        }
        if (adopter.getAdoptions() == null) {
            adopter.setAdoptions(new ArrayList<>());
        }
        if (staff.getAdoptions() == null) {
            staff.setAdoptions(new ArrayList<>());
        }
        Adoption adoption = new Adoption();
        adoption.setAdopter(adopter);
        adoption.setStaff(staff);
        adoption.setAnimal(animal);
        adoption.setDate(date);

        adopter.getAdoptions().add(adoption);
        staff.getAdoptions().add(adoption);

        if (!animal.getAvailability()) {
            throw new RuntimeException("Animal is not available for adoption");
        }

        animal.setAvailability(false);
        animalRepository.update(animal);

        adopterRepository.update(adopter);
        staffRepository.update(staff);
    }


    @Override
    public List<Animal> getAdoptedAnimalsByAdopter(Integer adopterID) {
        Adopter adopter = adopterRepository.findById(adopterID);

        if (adopter == null) {
            throw new RuntimeException("Adopter not found");
        }
        List<Adoption> adoptions = adopter.getAdoptions();

        List<Animal> adoptedAnimals = new ArrayList<>();
        for (Adoption adoption : adoptions) {
            adoptedAnimals.add(adoption.getAnimal());
        }

        return adoptedAnimals;
    }

    @Override
    public Adopter login(String name, String password) {
        return adopterRepository.findByNameAndPassword(name, password);
    }
}
