package app.controller.rest;

import app.model.Adopter;
import app.model.Animal;
import app.service.AdopterService;
import app.service.AnimalService;
import app.single_point_access.ServiceSinglePointAccess;
import app.util.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// All imported/exported files are taken form resources directory ONLY
@RestController
@RequestMapping("/csv")
public class CSVController {

    private AdopterService adopterService = ServiceSinglePointAccess.getAdopterService();
    private AnimalService animalService = ServiceSinglePointAccess.getAnimalService();

    @PostMapping("/import_adopter")
    public ResponseEntity<Boolean> importUserFromCSV(@RequestBody String filename) {
        try {
            File file = FileUtil.getAndCreateFileFromResourcesDirectory(filename);
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<Adopter> adopters = new ArrayList<>();
            String line;
            boolean firstLine = true;
            List<String> headersAdopter = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] valuesAdopter = line.split(",");
                if (firstLine) {
                    firstLine = false;
                    headersAdopter = Arrays.asList(valuesAdopter);
                    continue;
                }
                Adopter adopter = createAdopterFromCSVLine(valuesAdopter, headersAdopter);
                adopters.add(adopter);
            }
            for (Adopter adopterIterator : adopters) {
                adopterService.save(adopterIterator);
            }
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Adopter createAdopterFromCSVLine(String[] values, List<String> headers) {
        Adopter adopter = new Adopter();

        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            String value = values[i];
            switch (header.toLowerCase()) {
                case "name":
                    adopter.setName(value);
                    break;
                case "password":
                    adopter.setPassword(value);
                    break;
                case "phone":
                    adopter.setPhone(value);
                    break;
            }
        }
        return adopter;
    }


    // You can send the order of fields that must appear in csv
    // Add a new parameter for header
    @PostMapping("/export_adopter")
    public ResponseEntity<Boolean> exportUserToCSV(
            @RequestBody String filename,
            @RequestParam(defaultValue = "password,name") String header) {
        try {
            File file = FileUtil.getAndCreateFileFromResourcesDirectory(filename);
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(header + "\n");

                List<Adopter> adopters = adopterService.findAll();
                for (Adopter adopterIterator : adopters) {
                    String[] headerColumns = header.split(",");
                    StringBuilder userDetails = new StringBuilder();
                    for (String column : headerColumns) {
                        try {
                            Field field = Adopter.class.getDeclaredField(column);
                            field.setAccessible(true);
                            Object value = field.get(adopterIterator);
                            userDetails.append(value != null ? value.toString() : "");
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();

                        }
                        userDetails.append(",");
                    }
                    userDetails.deleteCharAt(userDetails.length() - 1);
                    userDetails.append("\n");
                    fileWriter.write(userDetails.toString());
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }













    @PostMapping("/import_animals")
    public ResponseEntity<Boolean> importAnimalFromCSV(@RequestBody String filename) {
        try {
            File file = FileUtil.getAndCreateFileFromResourcesDirectory(filename);

            // Read data in a buffer
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<Animal> animals = new ArrayList<>();
            String line;
            boolean firstLine = true;
            List<String> headersAnimals = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] valuesAnimals = line.split(",");

                if (firstLine) {
                    firstLine = false;
                    headersAnimals = Arrays.asList(valuesAnimals);
                    continue;
                }

                Animal animal = createAnimalFromCSVLine(valuesAnimals, headersAnimals);
                animals.add(animal);
            }

            for (Animal animalIterator : animals) {
                animalService.save(animalIterator);
            }

            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Animal createAnimalFromCSVLine(String[] values, List<String> headers) {
        Animal animal = new Animal();

        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            String value = values[i];

            switch (header.toLowerCase()) {
                case "name":
                    animal.setName(value);
                    break;
                case "species":
                    animal.setSpecies(value);
                    break;
                case "age":
                    animal.setAge(Integer.valueOf(value));
                    break;
            }
        }

        return animal;
    }


    // You can send the order of fields that must appear in csv
    // Add a new parameter for header
    @PostMapping("/export_animals")
    public ResponseEntity<Boolean> exportAnimalToCSV(
            @RequestBody String filename,
            @RequestParam(defaultValue = "species,name,age") String header) {

        try {
            File file = FileUtil.getAndCreateFileFromResourcesDirectory(filename);
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(header + "\n"); // Use the provided header

                List<Animal> animals = animalService.findAll();
                for (Animal animalIterator : animals) {
                    String[] headerColumns = header.split(",");
                    StringBuilder userDetails = new StringBuilder();

                    for (String column : headerColumns) {
                        try {
                            // Use reflection to get the value of the specified field
                            Field field = Animal.class.getDeclaredField(column);
                            field.setAccessible(true);
                            Object value = field.get(animalIterator);

                            userDetails.append(value != null ? value.toString() : "");
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                            // Handle the exception as needed
                        }

                        userDetails.append(",");
                    }

                    // Remove the trailing comma and add a newline
                    userDetails.deleteCharAt(userDetails.length() - 1);
                    userDetails.append("\n");

                    fileWriter.write(userDetails.toString());
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

}

