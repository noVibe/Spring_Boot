package pro.sky.lessons.spring_boot.controller.report;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.lessons.spring_boot.abstraction.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }



    @GetMapping(value = "/{id}")
    public ResponseEntity<Resource> getReport(@PathVariable(name = "id") long id) {
        String fileName = "employee.json";
        Resource resource = reportService.getReport(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }


}
