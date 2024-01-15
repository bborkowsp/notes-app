package com.notesapp.notesapp.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.notesapp.notesapp.repository.UserRepository;
import com.notesapp.notesapp.service.AuthUseCases;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SecurityConfig {

    private final CustomWebAuthenticationDetailsSource authenticationDetailsSource;
    private final UserRepository userRepository;
    private final AuthUseCases authUseCases;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final Cache<String, Integer> failedLoginAttemptsCache;


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider())
                .build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, List<AuthenticationProvider> providers) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .requiresChannel(requiresChannel -> requiresChannel
                        .anyRequest().requiresSecure())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/css/**", "/js/**", "/register", "/qrcode/{username}", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationManager(customProviderManager(providers))
                .formLogin((formLogin) -> formLogin.loginPage("/login")
                        .authenticationDetailsSource(authenticationDetailsSource)
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/notes/my-notes", true)
                        .failureUrl("/login?error=true")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .build();
    }

    private AuthenticationManager customProviderManager(List<AuthenticationProvider> providers) {
        return new ProviderManager(providers);
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider(userRepository, authUseCases,
                failedLoginAttemptsCache);
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

}
