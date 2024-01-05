package app.single_point_access;

import app.service.AdopterService;
import app.service.implementation.AdopterServiceImpl;
import app.service.performance.AppPerformanceService;
import app.service.performance.SQLProcedurePerformanceService;

public class ServiceSinglePointAccess {

    private static AdopterService adopterService;
    private static AppPerformanceService appPerformanceService;
    private static SQLProcedurePerformanceService SQLProcedurePerformanceService;

    static {
        adopterService = new AdopterServiceImpl();
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

}
