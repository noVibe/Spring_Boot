package pro.sky.lessons.spring_boot.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.abstraction.ReportService;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeInDTO;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeOutDTO;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final EmployeeService employeeService;
    private final ReportService reportService;
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

}
