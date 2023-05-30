package pro.sky.lessons.spring_boot.abstraction;


import pro.sky.lessons.spring_boot.model.Employee;

import java.util.List;

public interface EmployeeService {
    void addEmployee(Employee... employees);

    void updateEmployee(long id, Employee employee);

    Employee getEmployeeById(long id);

    void deleteEmployee(long id);

    List<Employee> getEmployeesOlderThan(int age);

    List<Employee> getOldestEmployee();

    List<Employee> getYoungestEmployee();

    List<Employee> getEmployeesOlderAverage();

    Integer getAgeSum();
}
