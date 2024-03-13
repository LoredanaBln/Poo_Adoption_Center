package app.dto;

import app.model.Address;
import lombok.Data;

@Data
public class AdopterDTO {

    private String name;
    private Address address;
    private String phone;
}
