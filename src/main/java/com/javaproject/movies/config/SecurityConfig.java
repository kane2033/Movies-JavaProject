package com.javaproject.movies.config;

import com.javaproject.movies.security.jwt.JwtTokenProvider;
import com.javaproject.movies.security.jwt.JwtConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_ENDPOINT = "/api/admin/**";
    private static final String AUTH_ENDPOINT = "/api/auth/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //настройка cors политики
        http.cors().configurationSource(request -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("http://localhost:3000"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        })
                .and()
                .httpBasic().disable()
                .csrf().disable() //отключение защиты от csrf (хакерской атаки)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //сессии не создаются
                .and()
                .authorizeRequests() //настройка авторизации запросов
                .antMatchers(AUTH_ENDPOINT).permitAll() //логин и регистрация доступны всем
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN") //разрешен только админам
                //.anyRequest().authenticated() //все остальные запросы должны быть аунтефицированы (любая роль)
                .and()
                .apply(new JwtConfigure(jwtTokenProvider)); //проверка каждого запроса на сервер
    }
}
