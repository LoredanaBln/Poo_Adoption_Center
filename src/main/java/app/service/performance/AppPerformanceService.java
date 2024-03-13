package app.service.performance;

import app.model.Adoption;
import app.model.Animal;
import app.model.Adopter;
import app.model.Staff;
import app.repository.AnimalRepository;
import app.service.AdopterService;
import app.service.AnimalService;
import app.service.StaffService;
import app.single_point_access.RepositorySinglePointAccess;
import app.single_point_access.ServiceSinglePointAccess;

import java.util.List;

public class AppPerformanceService implements PerformanceService {

    private AdopterService adopterService = ServiceSinglePointAccess.getAdopterService();

    private AnimalRepository animalRepository = RepositorySinglePointAccess.getAnimalRepository();
    private AnimalService animalService = ServiceSinglePointAccess.getAnimalService();
    private StaffService staffService = ServiceSinglePointAccess.getStaffService();

    @Override
    public void applyLogicOnAdopters() {
        List<Adopter> adopters = adopterService.findAll();
        for (Adopter adopter : adopters) {
            if (adopter.getName().length() < 7) {

                adopter.setPassword(adopter.getPassword() + "longStringToBeAddedToThePassworddddd");
                if (adopter.getAdoptions() != null) {
                    for (Adoption adoption : adopter.getAdoptions()) {
                        Animal animal = new Animal();
                        animal.setName(adoption.getAnimal().getName());
                        animal.setAge(adoption.getAnimal().getAge() * 5);
                        animalRepository.save(animal);
                    }
                }

                if (adopter.getId() % 2 == 1) {
                    if (adopter.getAddress() != null) {
                        adopter.getAddress().setNumber(adopter.getAddress().getNumber() + 1);
                    }
                }

                adopterService.update(adopter);

            }
        }
    }

    @Override
    public void applyLogicOnStaff() {
        List<Staff> staff = staffService.findAll();
        for (Staff staff1 : staff) {
            if (staff1.getPhone().contains("3")) {
                staff1.setPhone(staff1.getPhone() + "100");
                if (staff1.getAdoptions() != null) {
                    for (Adoption adoption : staff1.getAdoptions()) {
                        Animal animal = new Animal();
                        animal.setName(adoption.getAnimal().getName());
                        animal.setSpecies(adoption.getAnimal().getSpecies() + "StringForPerformancetest");
                        animalRepository.save(animal);
                    }
                }
            }

            if (staff1.getName().contains("a")) {
                if (staff1.getAddress() != null) {
                    staff1.getAddress().setStreet(staff1.getAddress().getStreet() + "StringStreet");
                }
            }
            staffService.update(staff1);
        }
    }
}