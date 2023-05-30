package pro.sky.lessons.spring_boot.dto.position_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pro.sky.lessons.spring_boot.model.Position;
@Data
@AllArgsConstructor
public class PositionDTO {
    long id;
    String positionName;

    public static PositionDTO fromPosition(Position position) {
                return position == null ? null : new PositionDTO(position.getId(), position.getPositionName());
    }

    public Position toPosition() {
        return new Position()
                .setId(id)
                .setPositionName(positionName);
    }
}
