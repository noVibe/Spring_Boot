package pro.sky.lessons.spring_boot.service.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.lessons.spring_boot.abstraction.EmployeeService;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeInDTO;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UploadEmployeeServiceTest {
    @Mock
    EmployeeService employeeService;
    @InjectMocks
    UploadEmployeeService out;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void upload() throws JsonProcessingException {
            MultipartFile file = new MockMultipartFile("file",
                    mapper.writeValueAsBytes(new EmployeeInDTO[]{new EmployeeInDTO()}));
            out.upload(file);
            verify(employeeService, only()).addEmployee(any());
    }
}