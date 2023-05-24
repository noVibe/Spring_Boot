package pro.sky.lessons.spring_boot.repository;

import org.springframework.data.repository.CrudRepository;
import pro.sky.lessons.spring_boot.model.Position;

public interface PositionRepository extends CrudRepository<Position, Long> {
}
