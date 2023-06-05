package pro.sky.lessons.spring_boot.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pro.sky.lessons.spring_boot.dto.UserDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
@Component
public class UserDetailsImpl implements UserDetails {
    private UserDTO userDTO;

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(userDTO)
                .map(UserDTO::getRole)
                .map(SimpleGrantedAuthority::new)
                .map(Collections::singleton)
                .orElseGet(Collections::emptySet);
    }

    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDTO.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
