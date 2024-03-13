package app.controller.rest;

import app.service.performance.AppPerformanceService;
import app.service.performance.SQLProcedurePerformanceService;
import app.single_point_access.ServiceSinglePointAccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/peformanceStaff")
public class PerformanceControllerStaff {

    private AppPerformanceService appPerformanceService = ServiceSinglePointAccess.getAppPerformanceService();
    private SQLProcedurePerformanceService sqlProcedurePerformanceService = ServiceSinglePointAccess.getSQLProcedurePerformanceService();

    @GetMapping("/java_logic_staff")
    public ResponseEntity<Long> applyJavaLogic() {
        long timeStart = System.nanoTime();
        appPerformanceService.applyLogicOnStaff();
        long timeEnd = System.nanoTime();

        return ResponseEntity.status(HttpStatus.OK).body(timeEnd - timeStart);
    }

    @GetMapping("/sql_logic_staff")
    public ResponseEntity<Long> applySQLLogic() {
        long timeStart = System.nanoTime();
        sqlProcedurePerformanceService.applyLogicOnStaff();
        long timeEnd = System.nanoTime();

        return ResponseEntity.status(HttpStatus.OK).body(timeEnd - timeStart);
    }
}
