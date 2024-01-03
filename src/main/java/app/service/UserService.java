package app.service;

import app.model.Adopter;
import app.model.Animal;

import java.util.Date;
import java.util.List;

public interface UserService {

    Adopter save(Adopter adopter);

    Adopter update(Adopter adopter);

    List<Adopter> findAll();

    Adopter findById(Integer id);

    boolean delete(Adopter adopter);

    void createAdoption(Adopter adopter, Animal animal, Date date);

    Adopter login(String name, String password);
}
