package app.dto;

import app.model.Address;
import lombok.Data;

@Data
public class UserDTO {

    private String name;
    private Address address;
    private String phone;
}
