package pro.sky.lessons.spring_boot.service.position;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pro.sky.lessons.spring_boot.exceptions.AlreadyExistsException;
import pro.sky.lessons.spring_boot.exceptions.IdNotFound;
import pro.sky.lessons.spring_boot.model.Position;
import pro.sky.lessons.spring_boot.repository.position.PositionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    public void add(String name) {
        if (positionRepository.existsPositionsByPositionName(name)) {
            throw new AlreadyExistsException();
        }
        positionRepository.save(new Position(prepareName(name)));
    }
    public void addAll(List<String> list) {
        if (positionRepository.existsByPositionNameIn(list.stream().map(this::prepareName).collect(Collectors.toSet()))) {
            throw new AlreadyExistsException();
        }
        positionRepository.saveAll(list.stream().map(Position::new).toList());

    }
    public List<Position> getAll() {
        return positionRepository.findAll();
    }
    public void delete(long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(IdNotFound::new);
        positionRepository.delete(position);
    }
    private String prepareName(String name) {
        return StringUtils.capitalize(name.toLowerCase());
    }
}
