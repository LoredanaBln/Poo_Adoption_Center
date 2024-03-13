package app.single_point_access;

import app.service.*;
import app.service.implementation.*;
import app.service.performance.AppPerformanceService;
import app.service.performance.SQLProcedurePerformanceService;
import lombok.Getter;

public class ServiceSinglePointAccess {

    private static AdopterService adopterService;
    private static AdoptionService adoptionService;
    private static AnimalService animalService;
    @Getter
    private static StaffService staffService;
    private static AddressService addressService;
    private static AppPerformanceService appPerformanceService;
    private static SQLProcedurePerformanceService SQLProcedurePerformanceService;

    static {
        adopterService = new AdopterServiceImpl();
        adoptionService = new AdoptionServiceImpl();
        animalService = new AnimalServiceImpl();
        staffService = new StaffServiceImpl();
        addressService = new AddressServiceImpl();
        appPerformanceService = new AppPerformanceService();
        SQLProcedurePerformanceService = new SQLProcedurePerformanceService();
    }

    public static AppPerformanceService getAppPerformanceService() {
        return appPerformanceService;
    }

    public static app.service.performance.SQLProcedurePerformanceService getSQLProcedurePerformanceService() {
        return SQLProcedurePerformanceService;
    }

    public static AdopterService getAdopterService() {
        return adopterService;
    }

    public static AdoptionService getAdoptionService() {
        return adoptionService;
    }

    public static AnimalService getAnimalService() {
        return animalService;
    }

    public static AddressService getAddressService(){
        return addressService;
    }

    public static StaffService getStaffService(){
        return staffService;
    }
}
