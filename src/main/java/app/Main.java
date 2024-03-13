package app;

import app.controller.gui.LoginController;
import app.model.Address;
import app.model.Adopter;
import app.model.Animal;
import app.model.Staff;
import app.repository.AnimalRepository;
import app.repository.StaffRepository;
import app.service.*;
import app.single_point_access.RepositorySinglePointAccess;
import app.single_point_access.ServiceSinglePointAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;
import java.util.SimpleTimeZone;


@SpringBootApplication
public class Main {

    private static final String APPLICATION_CONFIGURATION_FILE = "app.configuration.properties";

    public static void main(String[] args) throws ParseException {

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
        AdopterService adopterService = ServiceSinglePointAccess.getAdopterService();
        AdoptionService adoptionService = ServiceSinglePointAccess.getAdoptionService();

        Adopter foundAdopter = adopterService.findById(150);
        if (foundAdopter != null) {
            System.out.println("Found Adopter: " + foundAdopter);
        } else {
            System.out.println("Adopter not found");
        }

        AnimalRepository animalRepository = RepositorySinglePointAccess.getAnimalRepository();
        Animal savedAnimal = animalRepository.findById(124);
        Animal animal = animalRepository.findById(4);
        System.out.println(animal);

        StaffRepository staffRepository = RepositorySinglePointAccess.getStaffRepository();
        Staff savedStaff = staffRepository.findById(1);
        adopterService.createAdoption(savedStaff, foundAdopter, savedAnimal, new Date());

        System.out.println(adopterService.findAll());
    }
}
