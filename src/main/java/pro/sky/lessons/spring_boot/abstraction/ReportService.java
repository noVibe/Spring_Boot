package pro.sky.lessons.spring_boot.abstraction;

import org.springframework.core.io.Resource;

public interface ReportService {
    long generateReport();

    Resource getReport(long id);
}
