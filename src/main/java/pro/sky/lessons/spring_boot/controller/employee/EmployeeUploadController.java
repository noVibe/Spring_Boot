package pro.sky.lessons.spring_boot.controller.employee;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.lessons.spring_boot.service.employee.UploadEmployeeService;


@RestController
@RequestMapping("employee/upload")
public class EmployeeUploadController {
    private UploadEmployeeService uploadEmployeeService;

    public EmployeeUploadController(UploadEmployeeService uploadEmployeeService) {
        this.uploadEmployeeService = uploadEmployeeService;
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadEmployees(@RequestParam("file") MultipartFile multi) {
        uploadEmployeeService.upload(multi);
    }
}
