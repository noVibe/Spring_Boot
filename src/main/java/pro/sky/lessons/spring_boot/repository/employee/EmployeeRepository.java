package pro.sky.lessons.spring_boot.repository.employee;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.projection.EmployeeView;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Query(value = "select e from Employee e where e.id = ?1")
    Optional<EmployeeView> findEmployeeView(Long id);

    @Query("select e from Employee e where e.age = (select max(age) from Employee)")
    List<Employee> findEmployeeWithMaxAge();

    @Query("select e from Employee e where e.age = (select min(age) from Employee)")
    List<Employee> findEmployeeWithLowestAge();

    List<Employee> findEmployeeByPosition_Id(long positionId);

    @Query("from Employee")
    List<Employee> findAllEmployees();

    @Query("select sum(age) from Employee ")
    Integer getSumOfAge();

    @Query("select e from Employee e where e.age > (select avg(age) from Employee)")
    List<Employee> findEmployeeWithAgeOverAverage();

    List<Employee> findEmployeeByAgeIsAfter(int age);

}
