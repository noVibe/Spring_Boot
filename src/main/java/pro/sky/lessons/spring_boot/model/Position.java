package pro.sky.lessons.spring_boot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String positionName;

    public Position(String positionName) {
        this.positionName = positionName;
    }

    public Position(Long id, String positionName) {
        this.id = id;
        this.positionName = positionName;
    }

}
