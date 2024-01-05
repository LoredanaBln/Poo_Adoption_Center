package app.service.implementation;

import app.model.Adopter;
import app.model.Adoption;
import app.model.Animal;
import app.model.Staff;
import app.repository.StaffRepository;
import app.service.StaffService;
import app.single_point_access.RepositorySinglePointAccess;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StaffServiceImpl implements StaffService {
    private StaffRepository staffRepository = RepositorySinglePointAccess.getStaffRepository();

    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public Staff update(Staff staff) {
        return staffRepository.update(staff);
    }

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Staff findById(Integer id) {
        return staffRepository.findById(id);
    }

    @Override
    public boolean delete(Staff staff) {
        return staffRepository.delete(staff);
    }

    @Override
    public Staff getStaffMemberWithMaximumNumberOfAdoptions(Staff staff) {
        List<Staff> allStaff = findAll();

        if (allStaff.isEmpty()) {
            return null;
        }

        Staff staffWithMaxAdoptions = allStaff.stream()
                .max(Comparator.comparingInt(staff1 -> staff.getAdoptions().size()))
                .orElse(null);

        return staffWithMaxAdoptions;
    }

    @Override
    public List<Adoption> getAdoptionsMadeByStaff(Integer staffID) {
        Staff staff = staffRepository.findById(staffID);

        if (staff == null) {
            throw new RuntimeException("Adopter not found");
        }
        List<Adoption> adoptions = staff.getAdoptions();

        List<Animal> adoptedAnimals = new ArrayList<>();
        for (Adoption adoption : adoptions) {
            adoptedAnimals.add(adoption.getAnimal());
        }

        return adoptions;
    }
}

