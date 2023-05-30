package pro.sky.lessons.spring_boot.repository.report;

import org.springframework.data.repository.CrudRepository;
import pro.sky.lessons.spring_boot.model.Report;

public interface ReportRepository extends CrudRepository<Report, Long> {
}
