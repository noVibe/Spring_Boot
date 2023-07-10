package pro.sky.lessons.spring_boot.controller.position;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pro.sky.lessons.spring_boot.model.Position;
import pro.sky.lessons.spring_boot.service.position.PositionService;

import java.util.List;

@RestController
@RequestMapping("/position")
@AllArgsConstructor
public class PositionController {
    private final PositionService positionService;

    @GetMapping("/all")
    public List<Position> getAll() {
        return positionService.getAll();
    }

    @PostMapping("addBatch")
    public void addAll(@RequestBody List<String> list) {
        positionService.addAll(list);
    }

    @DeleteMapping
    public void delete(@RequestParam(name = "id") long id) {
        positionService.delete(id);
    }

}
