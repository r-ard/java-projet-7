package com.nnk.springboot.security;

import com.nnk.springboot.services.CustomUserDetailsService;
import com.nnk.springboot.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * SecurityConfig for SpringSecurity, defines route handling, password hash method and user mapping.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Defines the security filter chain for the application.
     *
     * <p>Security rules include:</p>
     * <ul>
     *   <li>Public access to static resources, authentication pages and users pages (login, register).</li>
     *   <li>Authentication required for all other requests.</li>
     *   <li>Custom login form with success and failure redirections.</li>
     *   <li>Custom logout behavior with cookie deletion.</li>
     *   <li>Session management to limit simultaneous sessions per user.</li>
     * </ul>
     *
     * @param http The {@link HttpSecurity} object used to configure security.
     * @return The configured {@link SecurityFilterChain}.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(auth ->
                        auth
                        .requestMatchers("/css/**", "/login", "/register", "/", "/user/**")
                                .permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/bidList/list", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/app-logout")
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                // Session management
                .sessionManagement(sessionManagement -> sessionManagement.maximumSessions(1)
                        .expiredUrl("/login?expired=true"))
                .build();
    }

    /**
     * Defines a password encoder using BCrypt.
     *
     * @return A {@link PasswordEncoder} instance of BCryptEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication provider using a custom user details service.
     *
     * <p>Make the application's authentication provider use our own UserDetailsService {@link CustomUserDetailsService}, and encodes passwords with {@link BCryptPasswordEncoder}.</p>
     *
     * @return An instance of {@link DaoAuthenticationProvider}.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
