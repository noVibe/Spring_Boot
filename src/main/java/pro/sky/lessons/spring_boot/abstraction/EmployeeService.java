package pro.sky.lessons.spring_boot.abstraction;


import pro.sky.lessons.spring_boot.model.Employee;

import java.util.List;

public interface EmployeeService {
     Employee getOldestEmployee();

     Employee getYoungestEmployee();

     List<Employee> getEmployeesOlderAverage();

     Integer getAgeSum();
}
