package app.service;

import app.model.Adopter;
import app.model.Animal;

import java.util.List;

public interface AnimalService {

    Animal save(Animal animal);

    Animal update(Animal animal);

    List<Animal> findAll();

    Animal findById(Integer id);

    boolean delete(Animal animal);
    public List<Animal> findAllAvailableAnimals();
}
