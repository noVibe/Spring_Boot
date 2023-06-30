package test_data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeOutDTO;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.model.Position;
import pro.sky.lessons.spring_boot.projection.ReportView;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class TestData {
    final static public List<Position> POSITION_LIST = List.of(
            new Position(1L, "driver"),
            new Position(2L, "manager"),
            new Position(3L, "artist"),
            new Position(3L, "sportsman")
    );
    final static public List<Employee> EMPLOYEE_LIST = List.of(
            new Employee(1L, "Bob", "First", "male", 25, 1, POSITION_LIST.get(0)),
            new Employee(2L, "Dan", "Second", "male", 45, 2, POSITION_LIST.get(1)),
            new Employee(3L, "Pam", "Third", "female", 30, 1, POSITION_LIST.get(2)),
            new Employee(4L, "Joe", "Fourth", "male", 70, 1, POSITION_LIST.get(3))
    );
    final static public List<EmployeeOutDTO> EMPLOYEE_OUT_DTO_LIST = EMPLOYEE_LIST.stream().map(EmployeeOutDTO::fromEmployee).toList();

    final static public List<ReportView> REPORT_VIEW_LIST = TestData.EMPLOYEE_LIST.stream()
            .collect(Collectors.groupingBy(Employee::getPosition))
            .entrySet().stream()
            .map(e -> {
                IntSummaryStatistics s = e.getValue().stream().mapToInt(Employee::getAge).summaryStatistics();
                return new ReportView(e.getKey().getPositionName(), s.getCount(), s.getMax(), s.getMin(), s.getAverage());
            }).toList();
    final static public String REPORT_DATA;

    static {
        try {
            REPORT_DATA = new ObjectMapper().writeValueAsString(REPORT_VIEW_LIST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
