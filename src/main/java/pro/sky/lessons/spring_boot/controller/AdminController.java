package pro.sky.lessons.spring_boot.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.abstraction.ReportService;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeInDTO;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeOutDTO;
import pro.sky.lessons.spring_boot.service.employee.UploadEmployeeService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final EmployeeService employeeService;
    private final ReportService reportService;
    private final UploadEmployeeService uploadEmployeeService;

    @PutMapping("/employee/{id}")
    public void editEmployee(@PathVariable long id, @RequestBody EmployeeInDTO employee) {
        employeeService.updateEmployee(id, employee);
    }

    @PostMapping("/employee/add")
    public void addNewEmployees(@RequestBody EmployeeInDTO... employees) {
        employeeService.addEmployee(employees);
    }
    @DeleteMapping("/employee/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployee(id);
    }
    @GetMapping("/all")
    public List<EmployeeOutDTO> getAllEmployees() {
        return employeeService.getAll();
    }
    @PostMapping(value = "/report")
    public long generateReport() {
        return reportService.generateReport();
    }
    @GetMapping(value = "/report/{id}")
    public ResponseEntity<Resource> getReport(@PathVariable(name = "id") long id) {
        String fileName = "employee.json";
        Resource resource = reportService.getReport(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadEmployees(@RequestParam("file") MultipartFile multi) {
        uploadEmployeeService.upload(multi);
    }

}
