package pro.sky.lessons.spring_boot.service.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pro.sky.lessons.spring_boot.abstraction.ReportService;
import pro.sky.lessons.spring_boot.projection.ReportView;
import pro.sky.lessons.spring_boot.exceptions.IdNotFound;
import pro.sky.lessons.spring_boot.model.Report;
import pro.sky.lessons.spring_boot.repository.report.ReportGeneratorRepository;
import pro.sky.lessons.spring_boot.repository.report.ReportRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private ReportGeneratorRepository generatorRepository;
    private ReportRepository reportRepository;
    private ObjectMapper mapper;

    @Override
    public long generateReport() {
        Report report = new Report(getJSON());
        reportRepository.save(report);
        return report.getId();
    }

    @Override
    public Resource getReport(long id) {
        Report report = reportRepository.findById(id).orElseThrow(IdNotFound::new);
        return new ByteArrayResource(report.getData().getBytes());
    }



    @SneakyThrows
    private String getJSON() {
        List<ReportView> list = generatorRepository.getReportsList();
        return mapper.writeValueAsString(list);
    }
}
