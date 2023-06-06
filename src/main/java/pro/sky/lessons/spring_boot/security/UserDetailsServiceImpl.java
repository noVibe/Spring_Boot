package pro.sky.lessons.spring_boot.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pro.sky.lessons.spring_boot.dto.UserDTO;
import pro.sky.lessons.spring_boot.repository.UserRepository;
import pro.sky.lessons.spring_boot.service.UserMapper;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDetailsImpl userDetails;
    private final UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDto = userRepository
                .findByLogin(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UsernameNotFoundException("No user with login " + username));
        userDetails.setUserDTO(userDto);
        return userDetails;
    }
}
