package com.CMS.Content.Management.System.config;

import com.CMS.Content.Management.System.security.user.userDetailServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // This annotation will now correctly inject the final userDetailServiceImp
public class webSecurityConfiguration {

    // Make userDetailServiceImp final so Lombok's @RequiredArgsConstructor injects it via the constructor.
    private final userDetailServiceImp userDetailServiceImp;

    // The globalConfiguration method is typically not needed when you define a DaoAuthenticationProvider bean.
    // Spring Security will automatically pick up the DaoAuthenticationProvider bean.
    /*
    @Autowired
    public void globalConfiguration( AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        // This line would cause a NullPointerException because userDetailServiceImp was not injected into this class.
        authenticationManagerBuilder.userDetailsService(userDetailServiceImp);
    }
    */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        // Now, userDetailServiceImp will be correctly injected by Lombok's constructor.
        authenticationProvider.setUserDetailsService(userDetailServiceImp);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simpler development (consider enabling for production)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").anonymous() // Allow anonymous access to the login page
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/file/**", "/uploads/**").permitAll() // Allow static resources
                        .requestMatchers("/users/**").hasRole("USER") // Require USER role for /users/**
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Require ADMIN role for /admin/**
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(form -> form
                        .loginPage("/login") // Specify custom login page
                        .loginProcessingUrl("/loginProcess") // URL to process login form submission
                        .defaultSuccessUrl("/posts", true) // Redirect to /posts after successful login
                        .failureForwardUrl("/login?errorPoppedUp=true") // Forward to login page with error param on failure
                )
                .rememberMe(remember -> remember
                        .key("unique") // Key used to hash the cookie
                        .rememberMeCookieName("remember-me") // Name of the remember-me cookie
                        .rememberMeParameter("remember-me") // Parameter name for remember-me checkbox
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) // Cookie validity for 21 days
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL to trigger logout
                        .logoutSuccessUrl("/login?logout=success") // Redirect after successful logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // Allow GET for logout (for simplicity, POST is more secure)
                        .invalidateHttpSession(true) // Invalidate HTTP session
                        .deleteCookies("JSESSIONID", "remember-me") // Delete cookies on logout
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // Allow only one session per user
                        .expiredUrl("/login?sessionExpired=true") // Redirect if session expires due to multiple logins
                );

        return httpSecurity.build();
    }
}

