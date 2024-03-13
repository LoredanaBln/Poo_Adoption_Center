package app.controller.rest;

import app.dto.AdoptionDTO;
import app.model.Adopter;
import app.model.Adoption;
import app.service.AdopterService;
import app.service.AdoptionService;
import app.single_point_access.ServiceSinglePointAccess;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adoption")
public class AdoptionController {
    private AdoptionService adoptionService = ServiceSinglePointAccess.getAdoptionService();
    private AdopterService adopterService = ServiceSinglePointAccess.getAdopterService();

    // Map http://localhost:8080/adopter/all
    // Get - to take data from server
    @GetMapping("/allAdoptions")
    public ResponseEntity<List<Adoption>> getAllAdoptions() {

        // Return a Response which has a status and a body (data)
        return ResponseEntity.status(HttpStatus.OK).body(adoptionService.findAll());
    }

    // {id} - is a value sent by url and is named path variable
    // {id} - will be taken from path - Path Variable
    // Attention - GET doesn't have a body
    @GetMapping("/idAdoption/{id}")
    public ResponseEntity<Adoption> getAdoptionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(adoptionService.findById(id));
    }

    // Post - create data
    // RequestBody - is the data sent to server through request
    // For POST, PUT, DELETE we can send information both : Path Variable & RequestBody
//    @PostMapping("/createAdoption")
//    public ResponseEntity<Adoption> createAdoption(@RequestBody Adoption adoption) {
//        return ResponseEntity.status(HttpStatus.OK).body(adoptionService.save(adoption));
//    }

    // Put - update data
    // Put with path variable

    // Delete
    @DeleteMapping("/deleteAdoption/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id) {
        Adoption adoption = adoptionService.findById(id);
        if (adoption != null) {
            return ResponseEntity.status(HttpStatus.OK).body(adoptionService.delete(adoption));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @Operation(summary = "Get details (name, address, phone) from all users")
    @GetMapping("/details_all_adoption")
    public ResponseEntity<List<AdoptionDTO>> getAllAdoptionDetails() {

        List<Adoption> adoptions = adoptionService.findAll();
        List<AdoptionDTO> adoptionDTOS = new ArrayList<>();

        for (Adoption adoption : adoptions) {
            AdoptionDTO adoptionDTO = new AdoptionDTO();
            adoptionDTO.setAdopterId(adoption.getAdopter().getId());
            adoptionDTO.setAnimalId(adoption.getAnimal().getId());
            adoptionDTO.setStaffId(adoption.getStaff().getId());
            adoptionDTO.setDate(adoption.getDate());
            adoptionDTOS.add(adoptionDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(adoptionDTOS);
    }
}
