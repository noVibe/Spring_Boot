package pro.sky.lessons.spring_boot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class InfoController {
    @Value("${app.env}")
    String s;
    @GetMapping("/appInfo")
    public String appInfo() {
        return s;
    }
}
