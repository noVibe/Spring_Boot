package pro.sky.lessons.spring_boot.dto.employee_dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pro.sky.lessons.spring_boot.model.Employee;
import pro.sky.lessons.spring_boot.model.Position;
@Data
@Accessors(chain = true)
public class EmployeeInDTO {
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private Integer cityId;
    private Long positionId;

    public static EmployeeInDTO fromEmployee(Employee employee) {
        return new EmployeeInDTO()
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setGender(employee.getGender())
                .setAge(employee.getAge())
                .setCityId(employee.getCityId())
                .setPositionId(employee.getPosition().getId());
    }

    public Employee toEmployee() {
        return new Employee()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setGender(gender)
                .setAge(age)
                .setCityId(cityId)
                .setPosition(new Position().setId(positionId));
    }
}
