package app.service;

import app.model.Adoption;
import app.model.Animal;
import app.model.Staff;

import java.util.List;

public interface StaffService {
    Staff save(Staff staff);

    Staff update(Staff staff);

    List<Staff> findAll();

    Staff findById(Integer id);

    boolean delete(Staff staff);

    public Staff getStaffMemberWithMaximumNumberOfAdoptions(Staff staff);
    public List<Adoption> getAdoptionsMadeByStaff(Integer staffID);
}
