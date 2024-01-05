package app.service;

import app.model.Adoption;
import app.model.Animal;

import java.util.Date;
import java.util.List;

public interface AdoptionService{
    public List<Adoption> getAdoptionsByDateRange(Date startDate, Date endDate);

    Adoption save(Adoption adoption);

    Adoption update(Adoption adoption);

    List<Adoption> findAll();

    Adoption findById(Integer id);

    boolean delete(Adoption adoption);
}
