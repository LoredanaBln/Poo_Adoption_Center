package app.controller.rest;

import app.dto.AdopterDTO;
import app.dto.AnimalDTO;
import app.model.Adopter;
import app.model.Animal;
import app.service.AnimalService;
import app.single_point_access.ServiceSinglePointAccess;
import io.swagger.v3.oas.annotations.Operation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private AnimalService animalService = ServiceSinglePointAccess.getAnimalService();

    @GetMapping("/allAnimals")
    public ResponseEntity<List<Animal>> getAllAnimals() {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.findAll());
    }

    @GetMapping("/idAnimal/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.findById(id));
    }

    @PostMapping("/createAnimal")
    public ResponseEntity<Animal> createUser(@RequestBody Animal animal) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.save(animal));
    }

    @PutMapping("/updateAnimalName/{id}/{name}")
    public ResponseEntity<Animal> updateAnimalName(@PathVariable Integer id, @PathVariable String name) {
        Animal animal = animalService.findById(id);
        animal.setName(name);
        Animal animalUpadated = animalService.update(animal);
        return ResponseEntity.status(HttpStatus.OK).body(animalUpadated);
    }

    // Delete
    @DeleteMapping("/deleteAnimal/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id) {
        Animal animal = animalService.findById(id);
        if (animal != null) {
            return ResponseEntity.status(HttpStatus.OK).body(animalService.delete(animal));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @Operation(summary = "Get details (name, address, phone) from all animals")
    @GetMapping("/details_all_animal")
    public ResponseEntity<List<AnimalDTO>> getAllUserDetails() {
        try {
            List<Animal> animals = animalService.findAll();
            List<AnimalDTO> animalDTOS = new ArrayList<>();

            for (Animal animal : animals) {
                AnimalDTO animalDTO = new AnimalDTO();
                animalDTO.setName(animal.getName());
                animalDTO.setSpecies(animal.getSpecies());
                animalDTO.setAge(animal.getAge());
                animalDTO.setAvailability(animal.getAvailability());

                animalDTOS.add(animalDTO);
            }

            return ResponseEntity.ok(animalDTOS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
