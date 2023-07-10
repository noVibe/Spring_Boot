package test_data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeInDTO;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeOutDTO;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.model.Position;
import pro.sky.lessons.spring_boot.projection.ReportView;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestData {

    private static final Faker faker = new Faker();
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
    public static EmployeeInDTO generateEmployeeInDTO(int positionIdRange) {
        return new EmployeeInDTO()
                .setFirstName(faker.name().firstName())
                .setLastName(faker.name().lastName())
                .setAge(faker.random().nextInt(18,80))
                .setGender(faker.demographic().sex())
                .setPositionId((long)faker.random().nextInt(1, positionIdRange));
    }
    public static Position generatePositionWithoutId() {
        return new Position(faker.company().name());
    }

    public static List<EmployeeInDTO> generateEmployeeInDTOList(int amount, int positionIdRange) {
        return Stream.generate(() -> generateEmployeeInDTO(positionIdRange))
                .distinct()
                .limit(amount)
                .collect(Collectors.toList());

    }
    public static List<Position> generatePositionList(int amount) {
        return Stream.generate(TestData::generatePositionWithoutId)
                .filter(distinctBy(Position::getPositionName))
                .limit(amount)
                .collect(Collectors.toList());
    }

    public static List<Employee> generateEmployeeList(int employeeRange, List<Position> positions) {
        return Stream.generate(() -> generateEmployee(positions.get(faker.random().nextInt(positions.size()))))
                .distinct()
                .limit(employeeRange)
                .collect(Collectors.toList());
    }

    public static Employee generateEmployee(Position position) {
        return new Employee(null,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.demographic().sex(),
                faker.random().nextInt(18,80),
                null,
                position);
    }
    public static <T> Predicate<T> distinctBy(Function<? super  T, ?> distinct) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(distinct.apply(t));
    }

}
