package app.service.implementation;

import app.model.Adopter;
import app.model.Adoption;
import app.model.Animal;
import app.repository.AnimalRepository;
import app.repository.AdopterRepository;
import app.service.UserService;
import app.single_point_access.RepositorySinglePointAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {

    private AdopterRepository adopterRepository = RepositorySinglePointAccess.getUserRepository();
    private AnimalRepository animalRepository = RepositorySinglePointAccess.getAnimalRepository();

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

    @Override
    public void createAdoption(Adopter adopter, Animal animal, Date date) {
        Adoption adoption = new Adoption();
        adoption.setAdopter(adopter);
        adoption.setAnimal(animal);
        adoption.setDate(date);

        if (adopter.getAdoptions() == null) {
            adopter.setAdoptions(new ArrayList<>());
        }

        adopter.getAdoptions().add(adoption);
        adopterRepository.update(adopter);
    }

    @Override
    public Adopter login(String name, String password) {
        return adopterRepository.findByNameAndPassword(name, password);
    }
}
