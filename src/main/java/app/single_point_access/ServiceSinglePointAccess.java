package app.single_point_access;

import app.service.UserService;
import app.service.implementation.UserServiceImpl;
import app.service.performance.AppPerformanceService;
import app.service.performance.SQLProcedurePerformanceService;

public class ServiceSinglePointAccess {

    private static UserService userService;
    private static AppPerformanceService appPerformanceService;
    private static SQLProcedurePerformanceService SQLProcedurePerformanceService;

    static {
        userService = new UserServiceImpl();
        appPerformanceService = new AppPerformanceService();
        SQLProcedurePerformanceService = new SQLProcedurePerformanceService();
    }

    public static AppPerformanceService getAppPerformanceService() {
        return appPerformanceService;
    }

    public static app.service.performance.SQLProcedurePerformanceService getSQLProcedurePerformanceService() {
        return SQLProcedurePerformanceService;
    }

    public static UserService getUserService() {
        return userService;
    }

}
