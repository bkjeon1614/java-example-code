package com.example.bkjeon.base.config;

import com.example.bkjeon.base.api.v1.controller.security.jwt.JwtAccessDeniedHandler;
import com.example.bkjeon.base.api.v1.controller.security.jwt.JwtAuthenticationEntryPoint;
import com.example.bkjeon.base.api.v1.controller.security.jwt.JwtSecurityConfig;
import com.example.bkjeon.base.api.v1.controller.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // @PreAuthorize 애노테이션을 메소드단위로 추가하기 위하여 적용
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_EXCEPTION_LIST = {
        "/",
        "/v1/security/auth/login", // 로그인
        "/v1/security/auth/signUp", // 회원가입
        "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**",   // swagger
        "/h2-console/**",
        "/favicon.ico"
    };

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
        TokenProvider tokenProvider,
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
        JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()   // 토큰을 사용하므로 CSRF 설정을 비활성화

            // Handling Custom Setting
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            // x-frame-options 동일 출처일경우만(H2 DB Console Exception)
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            // 세션을 사용하지 않으므로 세션설정을 STATELESS로 설정
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()    // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
            .antMatchers(AUTH_EXCEPTION_LIST)
            .permitAll()  // v1/security/auth에 대한 요청은 인증없이 접근을 허용
            .anyRequest()
            .authenticated()  // 나머지 요청들은 모두 인증되어야 한다

            // Custom JwtSecurityConfig Class Setting
            .and()
            .apply(new JwtSecurityConfig(tokenProvider));
    }

}
