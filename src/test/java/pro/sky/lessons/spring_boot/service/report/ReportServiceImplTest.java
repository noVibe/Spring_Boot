package pro.sky.lessons.spring_boot.service.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import pro.sky.lessons.spring_boot.model.Report;
import pro.sky.lessons.spring_boot.repository.report.ReportGeneratorRepository;
import pro.sky.lessons.spring_boot.repository.report.ReportRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static test_data.TestData.REPORT_DATA;
import static test_data.TestData.REPORT_VIEW_LIST;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {
    @Mock
    ReportGeneratorRepository generatorRepository;
    @Mock
    ReportRepository reportRepository;
    @Mock
    ObjectMapper mapper;
    @InjectMocks
    ReportServiceImpl out;


    @Test
    void generateReport() throws JsonProcessingException {
        when(generatorRepository.getReportsList())
                .thenReturn(REPORT_VIEW_LIST);
        when(mapper.writeValueAsString(REPORT_VIEW_LIST))
                .thenReturn(REPORT_DATA);
        when(reportRepository.save(any()))
                .thenReturn(new Report(REPORT_DATA));
        assertEquals(0, out.generateReport());
    }

    @Test
    void getReport() {
        Report report = new Report(REPORT_DATA);
        when(reportRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Report()));
       var actual = out.getReport(anyLong());
       var expected = new ByteArrayResource(report.getData().getBytes());
       assertEquals(expected, actual);
    }
}