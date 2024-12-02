package com.example.mywebservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailsService userService;

    // 1. 스프링 시큐리티 기능 비활성화 : 스프링 시큐리티의 모든 기능을 사용하지 않게 설정
    // 인증, 인가 서비스를 모든 곳에 적용하지는 않음.
    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers("/static/**");
    }

    // 2. 특정 HTTP 요청에 대한 웹 기반 보안 구성
    //    인증/인가, 로그인/로그아웃 관련 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth // 3. 인증, 인가 설정(authorizeRequests -> authorizeHttpRequests)
                        .requestMatchers("/login", "/signup", "/user").permitAll()
                        .anyRequest().authenticated())  // 모든 요청에 대한 인증 요구
                .formLogin(form -> form // 4. 폼 기반 로그인 설정
                        .loginPage("/login") // 커스텀 로그인 페이지 설정
                        .defaultSuccessUrl("/posts", true)
                        .permitAll()
                )// 로그인 성공시 이동할 페이지
                .logout(logout -> logout // 5. 로그아웃 설정
                        .logoutUrl("/login") // 로그아웃 후 로그인 페이지로 이동
                        .invalidateHttpSession(true)) // 로그아웃 시 세션 무료화
                .csrf(AbstractHttpConfigurer::disable) // 6. CSRF 비활성화
                .build();
    }

    // 7. 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserDetailsService userDetailsService
    ) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // 8. 시용자 정보 서비스 설정
        authProvider.setPasswordEncoder(bCryptPasswordEncoder); // 비밀번호 인코더 설정
        return  new ProviderManager(authProvider); // 인증 관리자 설정
    }

    // 9. 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
