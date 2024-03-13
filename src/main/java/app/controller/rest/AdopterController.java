    package app.controller.rest;

    import app.dto.AdopterDTO;
    import app.model.Adopter;
    import app.model.Adoption;
    import app.service.AdopterService;
    import app.service.AdoptionService;
    import app.service.StaffService;
    import app.single_point_access.ServiceSinglePointAccess;
    import io.swagger.v3.oas.annotations.Operation;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.ArrayList;
    import java.util.List;

    @RestController

    // every request to http://localhost:8080/user will enter on this controller
    @RequestMapping("/adopter")
    public class AdopterController {

        private AdopterService adopterService = ServiceSinglePointAccess.getAdopterService();
        private AdoptionService adoptionService = ServiceSinglePointAccess.getAdoptionService();
        private StaffService staffService = ServiceSinglePointAccess.getStaffService();

        // Map http://localhost:8080/adopter/all
        // Get - to take data from server
        @GetMapping("/all")
        public ResponseEntity<List<Adopter>> getAllUsers() {

            // Return a Response which has a status and a body (data)
            return ResponseEntity.status(HttpStatus.OK).body(adopterService.findAll());
        }

        // {id} - is a value sent by url and is named path variable
        // {id} - will be taken from path - Path Variable
        // Attention - GET doesn't have a body
        @GetMapping("/id/{id}")
        public ResponseEntity<Adopter> getUserById(@PathVariable Integer id) {
            return ResponseEntity.status(HttpStatus.OK).body(adopterService.findById(id));
        }

        // Post - create data
        // RequestBody - is the data sent to server through request
        // For POST, PUT, DELETE we can send information both : Path Variable & RequestBody
        @PostMapping("/create")
        public ResponseEntity<Adopter> createUser(@RequestBody Adopter adopter) {
            return ResponseEntity.status(HttpStatus.OK).body(adopterService.save(adopter));
        }

        // Put - update data
        // Put with path variable
        @PutMapping("/updateName/{id}/{name}")
        public ResponseEntity<Adopter> updateUserName(@PathVariable Integer id, @PathVariable String name) {
            Adopter adopter = adopterService.findById(id);
            adopter.setName(name);
            Adopter adopterUpdated = adopterService.update(adopter);
            return ResponseEntity.status(HttpStatus.OK).body(adopterUpdated);
        }

        @PutMapping("/update")
        public ResponseEntity<Adopter> update(@RequestBody Adopter adopter) {
            Adopter adopterFromDB = adopterService.findById(adopter.getId());
            adopterFromDB.setName(adopter.getName());
            adopterFromDB.setPassword(adopter.getPassword());
            Adopter adopterUpdated = adopterService.update(adopterFromDB);
            return ResponseEntity.status(HttpStatus.OK).body(adopterUpdated);
        }

        // Delete
        // Delete
        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id) {
            Adopter adopter = adopterService.findById(id);
            if (adopter != null) {
                return ResponseEntity.status(HttpStatus.OK).body(adopterService.delete(adopter));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
            }
        }



        @GetMapping("/details_all")
        public ResponseEntity<List<AdopterDTO>> getAllUserDetails() {
            try {
                List<Adopter> adopters = adopterService.findAll();
                List<AdopterDTO> adopterDTOS = new ArrayList<>();

                for (Adopter adopter : adopters) {
                    AdopterDTO adopterDTO = new AdopterDTO();
                    adopterDTO.setAddress(adopter.getAddress());
                    adopterDTO.setPhone(adopter.getPhone());
                    adopterDTO.setName(adopter.getName());

                    adopterDTOS.add(adopterDTO);
                }

                return ResponseEntity.ok(adopterDTOS);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @PostMapping("/adopter/createAdoptions")
        public ResponseEntity<Object> createAdoptions(@RequestBody Adoption adoptionRequest) {
            try {
                if (adoptionRequest == null) {
                    throw new IllegalArgumentException("Adoption request cannot be null");
                }
                adopterService.createAdoption(
                        adoptionRequest.getStaff(),
                        adoptionRequest.getAdopter(),
                        adoptionRequest.getAnimal(),
                        adoptionRequest.getDate()
                );
                return ResponseEntity.status(HttpStatus.OK).body("Adoption created successfully");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
            }
        }

    }
