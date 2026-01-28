package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 学習用のため一時的にCSRFを無効化
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // すべてのリクエストにログインが必要
            )
            .formLogin(withDefaults()) // 標準のログインフォームを使用
            .logout(logout -> logout.logoutSuccessUrl("/login")); // ログアウト後の遷移先
        
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // ログイン用のユーザーを固定で作成（ユーザー名: user, パスワード: password）
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}