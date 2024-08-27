package org.tele2chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.tele2chat.security.service.CustomAuthorizationRequestResolver;
import org.tele2chat.security.service.CustomOidcUserService;
import org.tele2chat.security.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOidcUserService oidcUserService;
    private final CustomAuthorizationRequestResolver authorizationRequestResolver;

    @Autowired
    public SecurityConfig(CustomOidcUserService oidcUserService,
                          CustomAuthorizationRequestResolver authorizationRequestResolver) {
        this.oidcUserService = oidcUserService;
        this.authorizationRequestResolver = authorizationRequestResolver;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/login", "/register", "/registerUser", "/loginUser",
                                "/oauth2/**", "/resources/**", "/static/**", "/css/**", "/js/**",
                                "/images/**, /error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/chatPage", true)
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                                .authorizationRequestResolver(authorizationRequestResolver)
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserService)
                        )
                        .defaultSuccessUrl("/chatPage")
                )
                .csrf(csrf -> csrf.disable()) // Не успела настроить правильно
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }
}
