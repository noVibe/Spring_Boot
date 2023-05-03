package pro.sky.lessons.spring_boot.service;

import org.springframework.stereotype.Service;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.repository.Repository;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Repository repository;

    public EmployeeServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Employee getOldestEmployee() {
        return repository.findEmployeeWithMaxAge();
    }

    @Override
    public Employee getYoungestEmployee() {
        return repository.findEmployeeWithLowestAge();
    }

    @Override
    public List<Employee> getEmployeesOlderAverage() {
        return repository.findEmployeeWithAgeOverAverage();
    }

    @Override
    public Integer getAgeSum() {
        return repository.getSumOfAge();
    }
}
