package pro.sky.lessons.spring_boot.repository.position;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.lessons.spring_boot.model.Position;

import java.util.Collection;

public interface PositionRepository extends JpaRepository<Position, Long> {
    boolean existsPositionsByPositionName(String name);
    boolean existsByPositionNameIn(Collection<String> positionName);
}
