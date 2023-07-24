package pro.sky.lessons.spring_boot.controller.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeOutDTO;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.repository.employee.EmployeeRepository;
import pro.sky.lessons.spring_boot.repository.employee.PagingEmployee;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PagingEmployee pagingEmployee;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Transactional
    void getEmployee() throws Exception {
        long id = 1;
        Employee employee = employeeRepository.findById(id).orElseThrow();
        mockMvc.perform(get("/employee/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(employee.getFirstName() + " " + employee.getLastName()))
                .andExpect(jsonPath("$.age").value(employee.getAge()))
                .andExpect(jsonPath("$.positionName").value(employee.getPosition().getPositionName()));
    }

    @Test
    @Transactional
    void getEmployeesPage() throws Exception {
        List<EmployeeOutDTO> expected = pagingEmployee.findAll(
                PageRequest.of(1, 10))
                .map(EmployeeOutDTO::fromEmployee)
                .toList();
        similarPart(expected, "/employee/page", "page", "1");
    }

    @Test
    @Transactional
    void getEmployeeByPosition() throws Exception {
        long posId = 3;
        List<EmployeeOutDTO> expected = employeeRepository.findEmployeeByPosition_Id(posId).stream()
                .map(EmployeeOutDTO::fromEmployee)
                .toList();
        similarPart(expected, "/employee", "position", String.valueOf(posId));
    }

    @Test
    @Transactional
    void getEmployeesOlderThan() throws Exception {
        int ageBoundary = 3;
        List<EmployeeOutDTO> expected = employeeRepository.findEmployeeByAgeIsAfter(ageBoundary).stream()
                .map(EmployeeOutDTO::fromEmployee)
                .toList();
        similarPart(expected, "/employee/olderThan", "age", String.valueOf(ageBoundary));
    }

    @Test
    @Transactional
    void getYoungestEmployee() throws Exception {
        List<EmployeeOutDTO> expected = employeeRepository.findEmployeeWithLowestAge().stream()
                .map(EmployeeOutDTO::fromEmployee)
                .toList();
        similarPart(expected,"/employee/youngest", null, null);
    }

    @Test
    @Transactional
    void getOldestEmployee() throws Exception {
            List<EmployeeOutDTO> expected = employeeRepository.findEmployeeWithMaxAge().stream()
                    .map(EmployeeOutDTO::fromEmployee)
                    .toList();
            similarPart(expected,"/employee/oldest", null, null);
    }

    @Test
    @Transactional
    void getEmployeesOlderThanAverage() throws Exception {
        List<EmployeeOutDTO> expected = employeeRepository.findEmployeeWithAgeOverAverage().stream()
                .map(EmployeeOutDTO::fromEmployee)
                .toList();
        similarPart(expected,"/employee/overAverage", null, null);
    }

    @Test
    @Transactional
    void getAgeSum() throws Exception {
        String expected = String.valueOf(employeeRepository.getSumOfAge());
        mockMvc.perform(get("/employee/sum")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result ->
                        assertThat(result.getResponse().getContentAsString())
                                .isEqualTo(expected));
    }

    private void similarPart(List<EmployeeOutDTO> expected, String urlTemplate, String param, String value) throws Exception {
        List<EmployeeOutDTO> actual = new ArrayList<>();
        urlTemplate = urlTemplate.concat(param != null ? "?" + param + "=" + value : "");
        mockMvc.perform(get(urlTemplate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> actual.addAll(objectMapper.readValue(
                        result.getResponse().getContentAsString(), new TypeReference<>() {
                        }
                )));
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }
}