package app.controller.rest;

import app.dto.StaffDTO;
import app.model.Adopter;
import app.model.Staff;
import app.service.StaffService;
import app.single_point_access.ServiceSinglePointAccess;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/staff")
public class StaffControler {

    private StaffService staffService = ServiceSinglePointAccess.getStaffService();

    // Map http://localhost:8080/staff/all
    // Get - to take data from server
    @GetMapping("/allStaff")
    public ResponseEntity<List<Staff>> getAllStaff() {

         //Return a Response which has a status and a body (data)
        return ResponseEntity.status(HttpStatus.OK).body(staffService.findAll());
    }

    // {id} - is a value sent by url and is named path variable
    // {id} - will be taken from path - Path Variable
    // Attention - GET doesn't have a body
    @GetMapping("/idStaff/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.findById(id));
    }

    // Post - create data
    // RequestBody - is the data sent to server through request
    // For POST, PUT, DELETE we can send information both : Path Variable & RequestBody
    @PostMapping("/createStaff")
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.save(staff));
    }

    // Put - update data
    // Put with path variable
    @PutMapping("/updateStaffName/{id}/{name}")
    public ResponseEntity<Staff> updateStaffName(@PathVariable Integer id, @PathVariable String name) {
        Staff staff = staffService.findById(id);
        staff.setName(name);
        Staff staffUpdated = staffService.update(staff);
        return ResponseEntity.status(HttpStatus.OK).body(staffUpdated);
    }


    // Delete
    @DeleteMapping("/deleteStaff/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id) {
        Staff staff = staffService.findById(id);
        if (staff != null) {
            return ResponseEntity.status(HttpStatus.OK).body(staffService.delete(staff));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @Operation(summary = "Get details (name, address, phone) from all staff members")
    @GetMapping("/details_staff_all")
    public ResponseEntity<List<StaffDTO>> getAllStaffDetails() {

        List<Staff> staff = staffService.findAll();
        List<StaffDTO> staffDTOS = new ArrayList<>();

        for (Staff staff1 : staff) {
            StaffDTO staffDTO = new StaffDTO();
            staffDTO.setAddress(staff1.getAddress());
            staffDTO.setPhone(staff1.getPhone());
            staffDTO.setName(staff1.getName());

            staffDTOS.add(staffDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(staffDTOS);
    }

}
