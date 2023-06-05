package pro.sky.lessons.spring_boot.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import static pro.sky.lessons.spring_boot.enums.Role.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(this::customiseRequest);
        return http.build();
    }

    @SneakyThrows
    public void customiseRequest(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAnyRole(ADMIN.name())
                .requestMatchers(new AntPathRequestMatcher("/**")).hasAnyRole(USER.name(), ADMIN.name())
                .and()
                .formLogin().permitAll()
                .and()
                .logout().logoutUrl("/logout");
    }

    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}