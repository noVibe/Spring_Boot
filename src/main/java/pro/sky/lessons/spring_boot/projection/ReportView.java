package pro.sky.lessons.spring_boot.projection;


import lombok.Data;

import java.io.Serializable;
@Data
public class ReportView implements Serializable {
    private String position;
    private Long amount;
    private Integer maxAge;
    private Integer minAge;
    private Double avgAge;

    public ReportView(String position, Long amount, Integer maxAge, Integer minAge, Double avgAge) {
        this.position = position;
        this.amount = amount;
        this.maxAge = maxAge;
        this.minAge = minAge;
        this.avgAge = avgAge;
    }
}
