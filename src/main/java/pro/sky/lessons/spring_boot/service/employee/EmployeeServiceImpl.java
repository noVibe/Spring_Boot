package pro.sky.lessons.spring_boot.service.employee;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeInDTO;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeOutDTO;
import pro.sky.lessons.spring_boot.exceptions.IdNotFound;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.projection.EmployeeView;
import pro.sky.lessons.spring_boot.repository.employee.EmployeeRepository;
import pro.sky.lessons.spring_boot.repository.employee.PagingEmployee;
import pro.sky.lessons.spring_boot.repository.position.PositionRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PagingEmployee employeePaging;
    private final PositionRepository positionRepository;

    @Override
    public void addEmployee(EmployeeInDTO[] employees) {
        employeeRepository.saveAll(Arrays.stream(employees).map(EmployeeInDTO::toEmployee).toList());
    }

    @Override
    @Transactional
    public void updateEmployee(long id, EmployeeInDTO dto) {
        Employee employee = employeeRepository.findById(id).orElseThrow(IdNotFound::new);
        employeeRepository.save(employee
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setGender(dto.getGender())
                .setAge(dto.getAge())
                .setCityId(dto.getCityId())
                .setPosition(positionRepository.findById(dto.getPositionId()).orElseThrow(IdNotFound::new)));
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

    @Override
    public List<EmployeeOutDTO> getAll() {
        return fromEmployeeToDTOList(employeeRepository.findAllEmployees());
    }

    private List<EmployeeOutDTO> fromEmployeeToDTOList(List<Employee> list) {
        return list.stream()
                .map(EmployeeOutDTO::fromEmployee)
                .collect(Collectors.toList());
    }
}

