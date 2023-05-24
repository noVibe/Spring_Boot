package pro.sky.lessons.spring_boot.projection;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeView {
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
    int getAge();
    @Value("#{target.position.positionName}")
    String getPositionName();

}
