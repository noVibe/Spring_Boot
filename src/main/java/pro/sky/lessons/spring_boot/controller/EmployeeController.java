package pro.sky.lessons.spring_boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.model.Employee;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/youngest")
    public Employee getYoungestEmployee() {
        return employeeService.getYoungestEmployee();
    }

    @GetMapping("/oldest")
    public Employee getOldestEmployee() {
        return employeeService.getOldestEmployee();
    }

    @GetMapping("/overAverage")
    public List<Employee> getEmployeesOlderThanAverage() {
        return employeeService.getEmployeesOlderAverage();
    }

    @GetMapping("/sum")
    public int getAgeSum() {
        return employeeService.getAgeSum();
    }
}
