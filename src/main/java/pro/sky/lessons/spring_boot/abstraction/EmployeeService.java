package pro.sky.lessons.spring_boot.abstraction;


import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeInDTO;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeOutDTO;
import pro.sky.lessons.spring_boot.projection.EmployeeView;

import java.util.List;

public interface EmployeeService {
    void addEmployee(EmployeeInDTO... employees);

    void updateEmployee(long id, EmployeeInDTO employee);

    EmployeeView getEmployeeById(long id);

    void deleteEmployee(long id);

    List<EmployeeOutDTO> getEmployeesOlderThan(int age);
    List<EmployeeOutDTO> getEmployeesWithPosition(Long positionId);
    List<EmployeeOutDTO> getEmployeesWithPaging(int pageIndex, int unitsPerPage);

    List<EmployeeOutDTO> getOldestEmployee();
    List<EmployeeOutDTO> getAll();

    List<EmployeeOutDTO> getYoungestEmployee();

    List<EmployeeOutDTO> getEmployeesOlderAverage();

    Integer getAgeSum();
}
