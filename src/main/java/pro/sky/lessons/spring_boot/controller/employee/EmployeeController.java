package pro.sky.lessons.spring_boot.controller.employee;

import org.springframework.web.bind.annotation.*;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeOutDTO;
import pro.sky.lessons.spring_boot.projection.EmployeeView;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/{id}")
    public EmployeeView getEmployee(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/page")
    public List<EmployeeOutDTO> getEmployeesPage(@RequestParam(name = "page") int page, @RequestParam(name = "size", defaultValue = "10") int size) {
        return employeeService.getEmployeesWithPaging(page, size);
    }

    @GetMapping()
    public List<EmployeeOutDTO> getEmployeeByPosition(@RequestParam(name = "position", required = false) Long position) {
        return employeeService.getEmployeesWithPosition(position);
    }

    @GetMapping("/olderThan")
    public List<EmployeeOutDTO> getEmployeesOlderThan(@RequestParam(name = "age") int age) {
        return employeeService.getEmployeesOlderThan(age);
    }


    @GetMapping("/youngest")
    public List<EmployeeOutDTO> getYoungestEmployee() {
        return employeeService.getYoungestEmployee();
    }

    @GetMapping("/oldest")
    public List<EmployeeOutDTO> getOldestEmployee() {
        return employeeService.getOldestEmployee();
    }

    @GetMapping("/overAverage")
    public List<EmployeeOutDTO> getEmployeesOlderThanAverage() {
        return employeeService.getEmployeesOlderAverage();
    }

    @GetMapping("/sum")
    public int getAgeSum() {
        return employeeService.getAgeSum();
    }
}
