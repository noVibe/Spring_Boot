package pro.sky.lessons.spring_boot.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.dto.EmployeeInDTO;
import pro.sky.lessons.spring_boot.dto.EmployeeOutDTO;
import pro.sky.lessons.spring_boot.exceptions.IdNotFound;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.projection.EmployeeView;
import pro.sky.lessons.spring_boot.repository.EmployeeRepository;
import pro.sky.lessons.spring_boot.repository.PagingEmployee;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PagingEmployee employeePaging;

    @Override
    public void addEmployee(EmployeeInDTO[] employees) {
        employeeRepository.saveAll(Arrays.stream(employees).map(EmployeeInDTO::toEmployee).toList());
    }

    @Override
    public void updateEmployee(long id, EmployeeInDTO employee) {
        employeeRepository.findById(id).orElseThrow(IdNotFound::new);
        employeeRepository.save(employee.toEmployee().setId(id));
    }

    @Override
    public EmployeeView getEmployeeById(long id) {
        return employeeRepository.findEmployeeView(id).orElseThrow(IdNotFound::new);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeOutDTO> getEmployeesWithPosition(Long positionId) {
        if (positionId == null) {
            return fromEmployeeToDTOList(employeeRepository.findAllEmployees());
        } else {
            return fromEmployeeToDTOList(employeeRepository.findEmployeeByPosition_Id(positionId));
        }
    }

    @Override
    public List<EmployeeOutDTO> getEmployeesWithPaging(int pageIndex, int unitsPerPage) {
        Pageable employeesOfPage = PageRequest.of(pageIndex, unitsPerPage);
        return employeePaging.findAll(employeesOfPage).map(EmployeeOutDTO::fromEmployee).toList();
    }

    private List<EmployeeOutDTO> fromEmployeeToDTOList(List<Employee> list) {
        return list.stream()
                .map(EmployeeOutDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeOutDTO> getEmployeesOlderThan(int age) {
        return fromEmployeeToDTOList(employeeRepository.findEmployeeByAgeIsAfter(age));
    }

    @Override
    public List<EmployeeOutDTO> getOldestEmployee() {
        return fromEmployeeToDTOList(employeeRepository.findEmployeeWithMaxAge());
    }

    @Override
    public List<EmployeeOutDTO> getYoungestEmployee() {
        return fromEmployeeToDTOList(employeeRepository.findEmployeeWithLowestAge());
    }

    @Override
    public List<EmployeeOutDTO> getEmployeesOlderAverage() {
        return fromEmployeeToDTOList(employeeRepository.findEmployeeWithAgeOverAverage());
    }

    @Override
    public Integer getAgeSum() {
        return employeeRepository.getSumOfAge();
    }
}
