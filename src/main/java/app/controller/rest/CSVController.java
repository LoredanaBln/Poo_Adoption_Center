package app.controller.rest;

import app.model.Adopter;
import app.service.UserService;
import app.single_point_access.ServiceSinglePointAccess;
import app.util.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


// All imported/exported files are taken form resources directory ONLY
@RestController
@RequestMapping("/csv")
public class CSVController {

    private UserService userService = ServiceSinglePointAccess.getUserService();

    // TO DO
    // For project take in consideration that a csv file could have different order of columns
    // Do it for at least 2 entities - import and export
    // Take in consideration data validation or if some data already exists
    // Extract duplicate logic and improve it based on template below
    //
    // For demo - import at least 25 entities and export all entities
    //
    @PostMapping("/import_user")
    public ResponseEntity<Boolean> importUserFromCSV(@RequestBody String filename) {
        try {
            File file = FileUtil.getAndCreateFileFromResourcesDirectory(filename);

            // Read data in a buffer
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<Adopter> adopters = new ArrayList<>();
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                Adopter adopter = new Adopter();

                // The order in csv could be changed
                // This implementation is only for template purpose
                adopter.setName(values[0]);
                adopter.setPassword(values[1]);
                adopter.setPhone(values[2]);
                //adopter.setSalary(Integer.valueOf(values[3]));

                adopters.add(adopter);
            }

            for (Adopter adopterIterator : adopters) {
                userService.save(adopterIterator);
            }

            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

            // TO DO - treat exception case
        } catch (IOException e) {

            // TO DO - treat exception case
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {

            // TO DO - treat exception case
            throw new RuntimeException(e);
        }
    }

    // You can send the order of fields that must appear in csv
    // Add a new parameter for header
    @PostMapping("/export_user")
    public ResponseEntity<Boolean> exportUserToCSV(@RequestBody String filename) {

        try {
            File file = FileUtil.getAndCreateFileFromResourcesDirectory(filename);
            FileWriter fileWriter = new FileWriter(file);
            String header = "name,password\n";
            fileWriter.write(header);


            List<Adopter> adopters = userService.findAll();
            for (Adopter adopterIterator : adopters) {
                String userDetails = "" + adopterIterator.getName() + "," + adopterIterator.getPassword() + "\n";
                fileWriter.write(userDetails);
            }

            fileWriter.close();

            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (IOException ex) {

            // TO DO - treat exception case

            throw new RuntimeException(ex);
        } catch (URISyntaxException e) {

            // TO DO - treat exception case

            throw new RuntimeException(e);
        }
    }
}
