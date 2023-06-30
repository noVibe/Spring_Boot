package pro.sky.lessons.spring_boot.service.employee;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeInDTO;
import pro.sky.lessons.spring_boot.dto.employee_dto.EmployeeOutDTO;
import pro.sky.lessons.spring_boot.exceptions.IdNotFound;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.projection.EmployeeView;
import pro.sky.lessons.spring_boot.repository.employee.EmployeeRepository;
import pro.sky.lessons.spring_boot.repository.employee.PagingEmployee;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static test_data.TestData.EMPLOYEE_LIST;
import static test_data.TestData.EMPLOYEE_OUT_DTO_LIST;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    PagingEmployee pagingEmployeeMock;
    @Mock
    EmployeeRepository employeeRepositoryMock;
    @InjectMocks
    EmployeeServiceImpl out;
    static int minAge;
    static int maxAge;

    @BeforeAll
    static void collectingInfo() {
        var ageStats = EMPLOYEE_LIST.stream().mapToInt(Employee::getAge).summaryStatistics();
        maxAge = ageStats.getMax();
        minAge = ageStats.getMin();
    }


    @Test
    void addEmployee() {
        out.addEmployee(new EmployeeInDTO[]{new EmployeeInDTO()});
        verify(employeeRepositoryMock, only()).saveAll(anyList());
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        when(employeeRepositoryMock.findEmployeeView(anyLong()))
                .thenReturn(Optional.empty());
        when(employeeRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(IdNotFound.class, () -> out.updateEmployee(anyLong(), new EmployeeInDTO()));
        assertThrows(IdNotFound.class, () -> out.getEmployeeById(anyLong()));
    }

    @Test
    void getEmployeeById() {
        Optional<EmployeeView> view = Optional.of(new EmployeeView() {
            @Override
            public String getFullName() {
                return "testName";
            }

            @Override
            public int getAge() {
                return 1;
            }

            @Override
            public String getPositionName() {
                return "testPos";
            }
        });
        when(employeeRepositoryMock.findEmployeeView(anyLong()))
                .thenReturn(view);
        assertEquals(view.get(), out.getEmployeeById(anyLong()));

    }

    @Test
    void deleteEmployee() {
        out.deleteEmployee(anyLong());
        verify(employeeRepositoryMock, only()).deleteById(anyLong());
    }

    @MethodSource("providePositionIdArguments")
    @ParameterizedTest
    void getEmployeesWithPosition(Long positionId) {
        List<Employee> list = filterByPositionId(positionId);

        lenient().when(employeeRepositoryMock.findEmployeeByPosition_Id(anyLong()))
                .thenReturn(list);
        lenient().when(employeeRepositoryMock.findAllEmployees())
                .thenReturn(EMPLOYEE_LIST);

        List<EmployeeOutDTO> expected = list.stream().map(EmployeeOutDTO::fromEmployee).toList();

        assertEquals(expected, out.getEmployeesWithPosition(positionId));
    }

    @MethodSource("providePagingTestArguments")
    @ParameterizedTest
    void getEmployeesWithPaging(int pageNumber, int size) {
        Page<Employee> page = getPageFromList(EMPLOYEE_LIST, pageNumber, size);
        when(pagingEmployeeMock.findAll(PageRequest.of(pageNumber, size)))
                .thenReturn(page);

        List<EmployeeOutDTO> actual = out.getEmployeesWithPaging(pageNumber, size);
        List<EmployeeOutDTO> expected = page.map(EmployeeOutDTO::fromEmployee).toList();
        assertEquals(expected, actual);
    }

    @Test
    void getEmployeesOlderThan() {
        int olderThan = new Random().nextInt(minAge, maxAge + 1);
        List<Employee> list = EMPLOYEE_LIST.stream()
                .filter(x -> x.getAge() > olderThan).toList();

        when(employeeRepositoryMock.findEmployeeByAgeIsAfter(olderThan))
                .thenReturn(list);

        List<EmployeeOutDTO> expected = list.stream().map(EmployeeOutDTO::fromEmployee).toList();
        assertEquals(expected, out.getEmployeesOlderThan(olderThan));
    }

    @Test
    void getOldestEmployee() {
        List<Employee> list = EMPLOYEE_LIST.stream()
                .filter(x -> x.getAge() == maxAge).toList();

        when(employeeRepositoryMock.findEmployeeWithMaxAge())
                .thenReturn(list);

        List<EmployeeOutDTO> expected = list.stream().map(EmployeeOutDTO::fromEmployee).toList();
        assertEquals(expected, out.getOldestEmployee());
    }

    @Test
    void getYoungestEmployee() {
        List<Employee> fromRep = EMPLOYEE_LIST.stream()
                .filter(x -> x.getAge() == minAge).toList();
        List<EmployeeOutDTO> expected = fromRep.stream().map(EmployeeOutDTO::fromEmployee).toList();

        when(employeeRepositoryMock.findEmployeeWithLowestAge())
                .thenReturn(fromRep);

        assertEquals(expected, out.getYoungestEmployee());
    }

    @Test
    void getEmployeesOlderAverage() {
        when(employeeRepositoryMock.findEmployeeWithAgeOverAverage())
                .thenReturn(EMPLOYEE_LIST);
        List<EmployeeOutDTO> actual = out.getEmployeesOlderAverage();
        assertEquals(EMPLOYEE_OUT_DTO_LIST, actual);
    }

    @Test
    void getAgeSum() {
        int expected = 50;
        when(employeeRepositoryMock.getSumOfAge())
                .thenReturn(expected);
        int actual = out.getAgeSum();
        assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        when(employeeRepositoryMock.findAllEmployees())
                .thenReturn(EMPLOYEE_LIST);
        assertEquals(EMPLOYEE_OUT_DTO_LIST, out.getAll());
    }

    private static Stream<Arguments> providePagingTestArguments() {
        return Stream.of(
                Arguments.of(1, 2),
                Arguments.of(1, 10),
                Arguments.of(0, 2)
        );
    }

    public static Stream<Arguments> providePositionIdArguments() {
        return Stream.of(
                Arguments.arguments(1L),
                Arguments.of(2L),
                null
        );
    }

    private static <T> Page<T> getPageFromList(List<T> col, int page, int size) {
        return new PageImpl<T>(col.stream().skip((long) page * size).limit(page).toList());
    }
    private static List<Employee> filterByPositionId(Long positionId) {
        return test_data.TestData.EMPLOYEE_LIST.stream()
                .filter(employee -> positionId == null || employee.getPosition().getId() == positionId)
                .toList();
    }

}