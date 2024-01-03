package app.single_point_access;

import app.repository.AnimalRepository;
import app.repository.AdopterRepository;
import app.repository.implemetation.AnimalRepositoryImpl;
import app.repository.implemetation.AdopterRepositoryImpl;

public class RepositorySinglePointAccess {

    private static AdopterRepository adopterRepository;
    private static AnimalRepository animalRepository;

    static {

        adopterRepository = new AdopterRepositoryImpl();
        animalRepository = new AnimalRepositoryImpl();
    }

    public static AdopterRepository getUserRepository() {
        return adopterRepository;
    }

    public static AnimalRepository getAnimalRepository() {
        return animalRepository;
    }
}
