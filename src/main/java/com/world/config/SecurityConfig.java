package com.world.config;


import com.world.config.entrypoint.CustomAuthenticationEntryPoint;
import com.world.exception.handler.AccessDeniedHandlerImpl;
import com.world.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exceptionHandlingCustomizer())
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(HttpMethod.GET,"/api/articles","/api/tags","/api/articles/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users","/api/users/login").permitAll() // 确保这个配置在其他拦截规则之前
                        .anyRequest().authenticated() // 其他所有请求都需要认证
                ).addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:8082")); // 允许访问的源，可以根据需要添加或修改
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 允许的HTTP方法
        config.setAllowedHeaders(Arrays.asList("*")); // 允许的HTTP头部，使用"*"表示允许所有头部
        config.setAllowCredentials(true); // 是否允许发送凭证（如Cookies和HTTP认证信息）

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 对所有路径应用上述CORS配置
        return source;
    }

    private Customizer<ExceptionHandlingConfigurer<HttpSecurity>> exceptionHandlingCustomizer() {
        return (configurer) -> configurer
                .authenticationEntryPoint(new BasicAuthenticationEntryPoint())
                .accessDeniedHandler(new AccessDeniedHandlerImpl());
    }

}