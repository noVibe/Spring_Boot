package pro.sky.lessons.spring_boot.dto.employee_dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pro.sky.lessons.spring_boot.dto.position_dto.PositionDTO;
import pro.sky.lessons.spring_boot.model.Employee;
@Data
@Accessors(chain = true)
public class EmployeeOutDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private Integer cityId;
    private PositionDTO positionDTO;

    public static EmployeeOutDTO fromEmployee(Employee employee) {
        return new EmployeeOutDTO()
                .setId(employee.getId())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setGender(employee.getGender())
                .setAge(employee.getAge())
                .setCityId(employee.getCityId())
                .setPositionDTO(PositionDTO.fromPosition(employee.getPosition()));
    }

    public Employee toEmployee() {
        return new Employee()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setGender(gender)
                .setAge(age)
                .setCityId(cityId)
                .setPosition(positionDTO.toPosition());
    }
}
