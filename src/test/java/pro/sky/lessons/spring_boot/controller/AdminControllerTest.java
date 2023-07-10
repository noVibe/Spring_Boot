package pro.sky.lessons.spring_boot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeInDTO;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeOutDTO;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.repository.employee.EmployeeRepository;
import pro.sky.lessons.spring_boot.repository.position.PositionRepository;
import test_data.TestData;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WithMockUser(roles = {"ADMIN"})
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    EmployeeRepository employeeRepository;


    @Test
    @Transactional
    void addNewEmployeesTest() throws Exception {
        List<EmployeeInDTO> beingAdded = TestData.generateEmployeeInDTOList(10, 5);
        List<Employee> existed = employeeRepository.findAllEmployees();
        List<EmployeeOutDTO> expected = Stream.concat(existed.stream(),
                        beingAdded.stream().map(EmployeeInDTO::toEmployee))
                .map(EmployeeOutDTO::fromEmployee)
                .toList();

        mockMvc.perform(post("/admin/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beingAdded)))
                .andExpect(status().isOk());

        List<EmployeeOutDTO> actual = getAllEmployees();
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "PositionDTO.positionName")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @Transactional
    void editEmployeeTest() throws Exception {

        Employee expected = employeeRepository.findById(4L).orElseThrow().clone();

        String modified = objectMapper.writeValueAsString(TestData.generateEmployeeInDTO(5));
        mockMvc.perform(put("/admin/employee/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modified))
                .andExpect(status().isOk());

        Employee actual = employeeRepository.findById(4L).orElseThrow();

        assertThat(actual).isNotEqualTo(expected);
    }

    @Test
    @Transactional
    void deleteEmployeeByIdTest() throws Exception {
        System.out.println(employeeRepository.findById(2L).get());
        mockMvc.perform(delete("/admin/employee/{id}", 2))
                .andExpect(status().isOk());
        assertThat(employeeRepository.findById(2L).isEmpty())
                .isTrue();
    }


    @Description("This test is ran by addNewEmployeesTest")
    List<EmployeeOutDTO> getAllEmployees() throws Exception {
        List<EmployeeOutDTO> employeeOutDTOS = new ArrayList<>();
        mockMvc.perform(get("/admin/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result ->
                        employeeOutDTOS.addAll(
                                objectMapper.readValue(
                                        result.getResponse().getContentAsString(),
                                        new TypeReference<>() {
                                        })
                        )

                );
        return employeeOutDTOS;
    }

    @Test
    @Transactional
    void generateReportTest() throws Exception {
        mockMvc.perform(post("/admin/report"))
                .andExpect(status().isOk())
                .andDo(result -> getReport(result.getResponse().getContentAsString()));
    }

    void getReport(String id) throws Exception {
        mockMvc.perform(get("/admin/report/{id}", id)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse()
                        .getContentAsByteArray())
                        .isNotEmpty()
                        .isNotNull());
    }

    @Test
    @Transactional
    public void uploadEmployees() throws Exception {
        List<EmployeeInDTO> beingAdded = TestData.generateEmployeeInDTOList(3, 5);
        List<Employee> existed = employeeRepository.findAllEmployees();
        List<Employee> expected = Stream.concat(existed.stream(),
                        beingAdded.stream().map(EmployeeInDTO::toEmployee))
                .toList();

        String str = objectMapper.writeValueAsString(beingAdded);

        mockMvc.perform(multipart("/admin/upload")
                        .file("file", str.getBytes(StandardCharsets.UTF_8))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk());
        List<Employee> actual = employeeRepository.findAllEmployees();
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "PositionDTO.positionName")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }
}