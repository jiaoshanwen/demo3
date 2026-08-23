package com.example.demo.config;

import com.example.demo.exception.JwtAccessDeniedHandler;
import com.example.demo.exception.JwtAuthenticationEntryPoint;
import com.example.demo.config.handler.login.LoginFailureHandler;
import com.example.demo.config.handler.login.LoginSuccessHandler;
import com.example.demo.config.handler.login.username.UsernameAuthenticationFilter;
import com.example.demo.config.handler.login.username.UsernameAuthenticationProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint = new JwtAuthenticationEntryPoint();
    private final JwtAccessDeniedHandler accessDeniedHandler = new JwtAccessDeniedHandler();

    private final ApplicationContext applicationContext;

    public SecurityConfig(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
//    // 构造器注入
//    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
//                          JwtAccessDeniedHandler accessDeniedHandler) {
//        this.authenticationEntryPoint = authenticationEntryPoint;
//        this.accessDeniedHandler = accessDeniedHandler;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())           // 关闭 CSRF 过滤器
                .logout(logout -> logout.disable())     // 关闭登出过滤器
                .formLogin(form -> form.disable())      // 关闭默认登录页过滤器
                .httpBasic(basic -> basic.disable())  // 关闭 Basic 认证过滤器
                .anonymous(an -> an.disable())
                .sessionManagement(AbstractHttpConfigurer::disable)
                .requestCache(cache -> cache.requestCache(new NullRequestCache()))
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                );

        LoginSuccessHandler loginSuccessHandler=  applicationContext.getBean(LoginSuccessHandler.class);
        LoginFailureHandler LoginFailureHandler=  applicationContext.getBean(LoginFailureHandler.class);
        UsernameAuthenticationFilter usernameAuthenticationFilter = new UsernameAuthenticationFilter(new AntPathRequestMatcher("/login/login", HttpMethod.POST.name()),
                new ProviderManager(List.of(applicationContext.getBean(UsernameAuthenticationProvider.class))),loginSuccessHandler,LoginFailureHandler
                );
                http.addFilterBefore(usernameAuthenticationFilter, LogoutFilter.class)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint) // 401
                        .accessDeniedHandler(accessDeniedHandler)           // 403
                );
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

}