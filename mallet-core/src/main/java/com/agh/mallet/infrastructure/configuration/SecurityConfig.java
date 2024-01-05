package com.agh.mallet.infrastructure.configuration;

import com.agh.mallet.domain.user.user.control.service.UserService;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    private final Pbkdf2PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, Pbkdf2PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.httpBasic(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(getRequestsCustomizer())
                .formLogin(AbstractHttpConfigurer::disable)
                .build();
    }

    private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> getRequestsCustomizer() {
        return auth -> auth.requestMatchers("/user/login").permitAll()
                .requestMatchers("/user/registration/**").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserJPAEntity user = userService.getByEmail(username);
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getEnabled(),
                    true,
                    true,
                    true,
                    Collections.emptySet());
        };
    }


}
