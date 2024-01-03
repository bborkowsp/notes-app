package com.notesapp.notesapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfigurationSource corsConfigurationSource;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource))
//                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/css/**").permitAll()
//                                .requestMatchers("/register").permitAll()
//                                .requestMatchers("/qrcode/{username}").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .formLogin(form ->
//                        form.loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .defaultSuccessUrl("/{username}/notes")
//                                .permitAll()
//                )
//                .logout(logout ->
//                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .permitAll()
//                )
//                .authenticationProvider(authenticationProvider())
//                .httpBasic(AbstractHttpConfigurer::disable);
//
//        return httpSecurity.build();
//    }

    @Bean
    public SecurityFilterChain filterSecurity(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/qrcode/{username}").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form.loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler((request, response, authentication) -> {
                                    String targetUrl = "/user/" + authentication.getName() + "/notes";
                                    new DefaultRedirectStrategy().sendRedirect(request, response, targetUrl);
                                })
                                .permitAll()
                )
                .logout(logout ->
                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        final var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }
}

