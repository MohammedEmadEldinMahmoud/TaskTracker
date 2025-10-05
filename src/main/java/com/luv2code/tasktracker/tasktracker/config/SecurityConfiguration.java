package com.luv2code.tasktracker.tasktracker.config;

import com.luv2code.tasktracker.tasktracker.enums.RoleName;
import com.luv2code.tasktracker.tasktracker.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private  JwtAuthenticationFilter jwtAuthenticationFilter;
    private  UserService userService;




    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Auth endpoints
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        // Users
                        .requestMatchers(HttpMethod.GET, "/api/users/**")
                        .hasAnyAuthority(RoleName.USER.name(), RoleName.ADMIN.name())

                        // Tasks - shared access
                        .requestMatchers(HttpMethod.POST, "/api/tasks/**")
                        .hasAnyAuthority(RoleName.USER.name(), RoleName.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/tasks/**")
                        .hasAnyAuthority(RoleName.USER.name(),RoleName.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/tasks/**")
                        .hasAnyAuthority(RoleName.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, "/api/tasks/**")
                        .hasAnyAuthority(RoleName.USER.name(), RoleName.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/tasks/**")
                        .hasAuthority(RoleName.ADMIN.name())

                        // Anything else → must be authenticated
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
