package pro.sky.lessons.spring_boot.repository.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.projection.ReportView;

import java.util.List;

public interface ReportGeneratorRepository extends CrudRepository<Employee, Long> {
    @Query("SELECT new pro.sky.lessons.spring_boot.projection.ReportView(coalesce(p.positionName, 'no position'), count(p.positionName), max(e.age), min(e.age), avg(e.age)) " +
            "from Employee e left join Position p on e.position = p group by coalesce(p.positionName, 'no position')")
    List<ReportView> getReportsList();
}

