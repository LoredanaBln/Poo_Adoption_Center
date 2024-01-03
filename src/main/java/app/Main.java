package app;

import app.controller.gui.LoginController;
import app.model.Address;
import app.model.Adopter;
import app.model.Animal;
import app.repository.AnimalRepository;
import app.service.UserService;
import app.single_point_access.RepositorySinglePointAccess;
import app.single_point_access.ServiceSinglePointAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;


@SpringBootApplication
public class Main {

    private static final String APPLICATION_CONFIGURATION_FILE = "app.configuration.properties";

    public static void main(String[] args) {

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream(APPLICATION_CONFIGURATION_FILE)) {
            Properties properties = new Properties();
            properties.load(input);

            // Decide app mode from file
            String mode = properties.getProperty("mode");

            if (mode.equals("web")) {
                SpringApplication.run(Main.class, args);
            } else {
                LoginController loginController = new LoginController();
                loginController.startLogic();
            }
        } catch (Exception ex) {
            System.out.println("Error at starting application...");
            ex.printStackTrace();
        }

        //Test implementation
        Adopter adopter = new Adopter();
        adopter.setName("Denisa");
        adopter.setPassword("1234");
        Address addressAdopter = new Address();
        addressAdopter.setCity("Cluj-Napoca");
        addressAdopter.setStreet("Observator");
        addressAdopter.setNumber(12);
        adopter.setAddress(addressAdopter);

        UserService userService = ServiceSinglePointAccess.getUserService();
        Adopter savedAdopter = userService.save(adopter);
//        savedAdopter.setName("Loredana_new");
//        userService.update(savedAdopter);
        userService.delete(savedAdopter);
        Adopter foundAdopter = userService.findById(savedAdopter.getId());
        if (foundAdopter != null) {
            System.out.println("Found Adopter: " + foundAdopter);
        } else {
            System.out.println("Adopter not found");
        }


        Animal animal = new Animal();
        animal.setName("Daisy");
        animal.setAge(5);
        animal.setSpecies("Dachshund");
        animal.setAvailability(true);


        AnimalRepository animalRepository = RepositorySinglePointAccess.getAnimalRepository();
        Animal savedMovie = animalRepository.save(animal);

        userService.createAdoption(savedAdopter, savedMovie, new Date());
        System.out.println(userService.findById(savedAdopter.getId()));
    }
}
