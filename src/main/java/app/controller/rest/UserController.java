package app.controller.rest;

import app.dto.UserDTO;
import app.model.Adopter;
import app.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    private UserService userService = ServiceSinglePointAccess.getUserService();

    // Map http://localhost:8080/user/all
    // Get - to take data from server
    @GetMapping("/all")
    public ResponseEntity<List<Adopter>> getAllUsers() {

        // Return a Response which has a status and a body (data)
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    // {id} - is a value sent by url and is named path variable
    // {id} - will be taken from path - Path Variable
    // Attention - GET doesn't have a body
    @GetMapping("/id/{id}")
    public ResponseEntity<Adopter> getUserById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    // Post - create data
    // RequestBody - is the data sent to server through request
    // For POST, PUT, DELETE we can send information both : Path Variable & RequestBody
    @PostMapping("/create")
    public ResponseEntity<Adopter> createUser(@RequestBody Adopter adopter) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(adopter));
    }

    // Put - update data
    // Put with path variable
    @PutMapping("/updateName/{id}/{name}")
    public ResponseEntity<Adopter> updateUserName(@PathVariable Integer id, @PathVariable String name) {
        Adopter adopter = userService.findById(id);
        adopter.setName(name);
        Adopter adopterUpdated = userService.update(adopter);
        return ResponseEntity.status(HttpStatus.OK).body(adopterUpdated);
    }

    @PutMapping("/update")
    public ResponseEntity<Adopter> update(@RequestBody Adopter adopter) {
        Adopter adopterFromDB = userService.findById(adopter.getId());
        adopterFromDB.setName(adopter.getName());
        adopterFromDB.setPassword(adopter.getPassword());
        Adopter adopterUpdated = userService.update(adopterFromDB);
        return ResponseEntity.status(HttpStatus.OK).body(adopterUpdated);
    }

    // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteById(@RequestBody Integer id) {
        Adopter adopter = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userService.delete(adopter));
    }

    @Operation(summary = "Get details (name, address, phone) from all users")
    @GetMapping("/details_all")
    public ResponseEntity<List<UserDTO>> getAllUserDetails() {

        List<Adopter> adopters = userService.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (Adopter adopter : adopters) {
            UserDTO userDTO = new UserDTO();
            userDTO.setAddress(adopter.getAddress());
            userDTO.setPhone(adopter.getPhone());
            userDTO.setName(adopter.getName());

            userDTOS.add(userDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
    }

}
