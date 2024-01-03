package app.service.performance;

import app.model.Adoption;
import app.model.Animal;
import app.model.Adopter;
import app.repository.AnimalRepository;
import app.service.UserService;
import app.single_point_access.RepositorySinglePointAccess;
import app.single_point_access.ServiceSinglePointAccess;

import java.util.List;

public class AppPerformanceService implements PerformanceService {

    private UserService userService = ServiceSinglePointAccess.getUserService();

    private AnimalRepository animalRepository = RepositorySinglePointAccess.getAnimalRepository();

    @Override
    public void applyLogicOnUsers() {
        List<Adopter> adopters = userService.findAll();
        for (Adopter adopter : adopters) {
            if (adopter.getName().length() < 5) {

                //adopter.setSalary(adopter.getSalary() * 2);
                if (adopter.getAdoptions() != null) {
                    for (Adoption adoption : adopter.getAdoptions()) {
                        Animal animal = new Animal();
                        animal.setName(adoption.getAnimal().getName());
                        //animal.setPrice(adoption.getAnimal().getPrice() * 2);
                        animalRepository.save(animal);
                    }
                }


                if (adopter.getId() % 2 == 1) {
                    if (adopter.getAddress() != null) {
                        adopter.getAddress().setNumber(adopter.getAddress().getNumber() + 1);
                    }
                }

                userService.update(adopter);

            }
        }
    }
}
