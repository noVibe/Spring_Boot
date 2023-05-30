package pro.sky.lessons.spring_boot.service.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeInDTO;

@Service
public class UploadEmployeeService {
    EmployeeService employeeService;

    public UploadEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @SneakyThrows
    public void upload(MultipartFile multi) {
        EmployeeInDTO[] arr = new ObjectMapper().readValue(multi.getInputStream(), EmployeeInDTO[].class);
        employeeService.addEmployee(arr);
    }
}
