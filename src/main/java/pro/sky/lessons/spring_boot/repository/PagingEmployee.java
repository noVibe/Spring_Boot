package pro.sky.lessons.spring_boot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pro.sky.lessons.spring_boot.model.Employee;

public interface PagingEmployee extends PagingAndSortingRepository<Employee, Long> {
}
