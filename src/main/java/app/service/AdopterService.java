package app.service;

import app.model.Adopter;
import app.model.Adoption;
import app.model.Animal;
import app.model.Staff;

import java.util.Date;
import java.util.List;

public interface AdopterService {

    Adopter save(Adopter adopter);

    Adopter update(Adopter adopter);

    List<Adopter> findAll();

    Adopter findById(Integer id);

    boolean delete(Adopter adopter);

    void createAdoption(Staff staff, Adopter adopter, Animal animal, Date date);

    List<Animal> getAdoptedAnimalsByAdopter(Integer adopterID);
    Adopter login(String name, String password);
}
