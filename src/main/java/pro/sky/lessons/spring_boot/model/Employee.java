package pro.sky.lessons.spring_boot.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "employee")
@Data
@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "gender")
    private String gender;
    @Column(name = "age")
    private Integer age;
    @Column(name = "city_id")
    private Integer cityId;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

}
