package com.endyary.mobsoftstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration
 */
@Configuration
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain setFilterChain(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/newapp").hasAuthority("DEVELOPER")
                .requestMatchers("/download/*", "/apprating").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable().cors().disable()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/perform_login").usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true).and()
                .logout().logoutUrl("/perform_logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").logoutSuccessUrl("/")
                .and().headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
