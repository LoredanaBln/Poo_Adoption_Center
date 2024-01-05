package app.single_point_access;

import app.repository.*;
import app.repository.implemetation.*;

public class RepositorySinglePointAccess {

    private static AdopterRepository adopterRepository;
    private static AnimalRepository animalRepository;
    private static AdoptionRepository adoptionRepository;
    private static StaffRepository staffRepository;
    private static AddressRepository addressRepository;

    static {

        adopterRepository = new AdopterRepositoryImpl();
        animalRepository = new AnimalRepositoryImpl();
        adoptionRepository = new AdoptionRepositoryImpl();
        staffRepository = new StaffRepositoryImpl();
        addressRepository = new AddressRepositoryImpl();
    }

    public static AdopterRepository getUserRepository() {
        return adopterRepository;
    }

    public static AnimalRepository getAnimalRepository() {
        return animalRepository;
    }

    public static AdoptionRepository getAdoptionRepository(){
        return adoptionRepository;
    }
    public static StaffRepository getStaffRepository(){return staffRepository;}

    public static AddressRepository getAddressRepository(){return addressRepository;}
}
