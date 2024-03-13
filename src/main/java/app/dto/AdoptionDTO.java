package app.dto;

import app.model.Address;
import app.model.Animal;
import lombok.Data;

import java.util.Date;

@Data
public class AdoptionDTO {
    private Integer staffId;
    private Integer adopterId;
    private Integer animalId;
    private Date date;
}
