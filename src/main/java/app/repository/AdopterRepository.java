package app.repository;

import app.model.Adopter;

public interface AdopterRepository extends CRUDRepository<Adopter, Integer> {

    Adopter findByName(String name);

    Adopter findByNameAndPassword(String name, String password);
}
