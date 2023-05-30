package com.example.demo.config;

import com.example.demo.security.jwt.AuthEntryPointJwt;
import com.example.demo.security.jwt.AuthTokenFilter;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration{

    private final UserDetailsService userDetailsService;

    private final AuthEntryPointJwt unauthorized;


    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private  JwtUtils jwtUtils;

    public ApplicationSecurityConfiguration(UserDetailsService userDetailsService,
                                            AuthEntryPointJwt unauthorized, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.unauthorized = unauthorized;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthTokenFilter authenticationJwtFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(this.passwordEncoder);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorized)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/","/user/register","/user/login").permitAll()
                .requestMatchers("/car/add").authenticated()
                .requestMatchers("/car/{id}/edit","/car/add","/car/{id}/delete","/car/all").hasRole("ADMIN")
                .requestMatchers("/user/profile","/user/edit","/user/add-money").authenticated()
                .requestMatchers("/user/{id}/set-admin","/user/{id}/delete").hasRole("ADMIN")
                .requestMatchers("/rentals/history","/rentals/active-history","/rentals/{carId}/add","/rentals/{rentalId}/complete").authenticated()
                .requestMatchers("/rentals/{rentalId}/summary","/rentals/{carId}/showCost").authenticated()
                .requestMatchers("/rentals/all-admin","/rentals/{id}/change-status").hasRole("ADMIN")
                .requestMatchers("/rentals/all-unique-available").permitAll();
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
