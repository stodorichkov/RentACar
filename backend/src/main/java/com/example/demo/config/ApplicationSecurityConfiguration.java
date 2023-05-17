package com.example.demo.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class ApplicationSecurityConfiguration{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
             //   .requestMatchers("/","/users/register","/users/login").permitAll()
             //   .requestMatchers("/about","/services").permitAll()
                .requestMatchers("/**").permitAll()
                .requestMatchers("/**").permitAll()
                .and().csrf().disable();
//                and()
//                .formLogin()
//                .loginPage("/users/login")
//                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
//                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
//                .defaultSuccessUrl("/")
//                .failureForwardUrl("/users/login-error").
//                and()
//                .logout()
//                .logoutUrl("/users/logout")
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID");
        return http.build();
    }

}
