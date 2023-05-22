package pro.sky.lessons.spring_boot.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.model.Employee;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public void addNewEmployees(@RequestBody Employee... employees) {
        employeeService.addEmployee(employees);
    }

    @PutMapping("/{id}")
    public void editEmployee(@PathVariable long id, @RequestBody Employee employee) {
        employeeService.updateEmployee(id, employee);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/olderThan")
    public List<Employee> getEmployeesOlderThan(@RequestParam (name = "age") int age) {
        return employeeService.getEmployeesOlderThan(age);
    }
    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/youngest")
    public List<Employee> getYoungestEmployee() {
        return employeeService.getYoungestEmployee();
    }

    @GetMapping("/oldest")
    public List<Employee> getOldestEmployee() {
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
