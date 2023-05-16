package pro.sky.lessons.spring_boot.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.repository.EmployeeRepository;

import java.util.Arrays;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void addEmployee(Employee[] employees) {
        employeeRepository.saveAll(Arrays.asList(employees));
    }

    @Override
    @SneakyThrows
    public void updateEmployee(long id, Employee employee) {
        employeeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        employee.setId(id);
        employeeRepository.save(employee);
    }

    @Override
    @SneakyThrows
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> getEmployeesOlderThan(int age) {
        return employeeRepository.findEmployeeByAgeIsAfter(age);
    }

    @Override
    public List<Employee> getOldestEmployee() {
        return employeeRepository.findEmployeeWithMaxAge();
    }

    @Override
    public List<Employee>  getYoungestEmployee() {
        return employeeRepository.findEmployeeWithLowestAge();
    }

    @Override
    public List<Employee> getEmployeesOlderAverage() {
        return employeeRepository.findEmployeeWithAgeOverAverage();
    }

    @Override
    public Integer getAgeSum() {
        return employeeRepository.getSumOfAge();
    }
}
