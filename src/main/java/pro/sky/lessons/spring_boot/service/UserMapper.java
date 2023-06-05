package pro.sky.lessons.spring_boot.service;

import org.springframework.stereotype.Component;
import pro.sky.lessons.spring_boot.dto.UserDTO;
import pro.sky.lessons.spring_boot.model.User;
@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        UserDTO userDTO =  new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole().name());
        return userDTO;
    }
}
