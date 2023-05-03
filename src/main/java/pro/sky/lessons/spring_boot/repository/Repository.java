package pro.sky.lessons.spring_boot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pro.sky.lessons.spring_boot.model.Employee;

import java.util.List;


public interface Repository extends CrudRepository<Employee, Long> {
    @Query("select e from Employee e where e.age = (select max(age) from Employee)")
    Employee findEmployeeWithMaxAge();

    @Query("select e from Employee e where e.age = (select min(age) from Employee)")
    Employee findEmployeeWithLowestAge();

    @Query("select sum(age) from Employee ")
    Integer getSumOfAge();

    @Query("select e from Employee e where e.age > (select avg(age) from Employee)")
    List<Employee> findEmployeeWithAgeOverAverage();


}
