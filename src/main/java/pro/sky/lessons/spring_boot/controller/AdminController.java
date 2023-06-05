package pro.sky.lessons.spring_boot.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeOutDTO;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final EmployeeService employeeService;


    @GetMapping("/all")
    public List<EmployeeOutDTO> getAllEmployees() {
        return employeeService.getAll();
    }
}
