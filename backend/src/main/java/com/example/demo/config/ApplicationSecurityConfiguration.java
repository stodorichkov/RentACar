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
        return new AuthTokenFilter(jwtUtils, userDetailsServiceImpl);
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


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                .requestMatchers("/","/user/register","/user/login").permitAll()
//                .requestMatchers("/**").permitAll()
//                //add this when connection with front end is ready
//                //.antMatchers("/**").authenticated()
//                .and()
//                .csrf().disable()
//                .cors().configurationSource(request -> {
//                    CorsConfiguration corsConfig = new CorsConfiguration();
//                    corsConfig.addAllowedOrigin("http://localhost:3000");
//                    corsConfig.addAllowedMethod("*");
//                    corsConfig.addAllowedHeader("*");
//                    return corsConfig;
//                }).and()
//                .formLogin()
//                .loginProcessingUrl("/user/login")
//            //    .usernameParameter("username")
//            //    .passwordParameter("password")
//                .defaultSuccessUrl("/user/login-success")
//                .failureForwardUrl("/user/login-error")
//                .and()
//                .logout()
//                .logoutUrl("/user/logout")
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID");
//        return http.build();
//    }

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
                .requestMatchers("/**").permitAll();
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception{
//        return authenticationManagerBean();
//    }

}





























//
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class ApplicationSecurityConfiguration{
//    private final JwtTokenFilter jwtAuthenticationFilter;
//    private final UserDetailsService userDetailsService;
//    private final DaoAuthenticationProvider daoAuthenticationProvider;
//
//    private final PasswordEncoder passwordEncoder;
//
//    public ApplicationSecurityConfiguration(JwtTokenFilter jwtAuthenticationFilter,
//                                            UserDetailsService userDetailsService,
//                                            DaoAuthenticationProvider daoAuthenticationProvider,
//                                            PasswordEncoder passwordEncoder) {
//
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//        this.userDetailsService = userDetailsService;
//        this.daoAuthenticationProvider = daoAuthenticationProvider;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
//        return daoAuthenticationProvider;
//    }
//
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity.headers().frameOptions().disable();
//
//        httpSecurity.cors().and().csrf().disable();
//        httpSecurity
//                .authorizeRequests()
//               // .requestMatchers("/","/user/register","/user/login").permitAll()
//                .requestMatchers("/**").permitAll()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, authException)
//                                -> response.sendError(
//                                HttpServletResponse.SC_UNAUTHORIZED,
//                                authException.getLocalizedMessage()
//                        )
//                )
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return httpSecurity.build();
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//




















































//package com.example.demo.config;
//
//
//import com.example.demo.security.jwt.AuthEntryPointJwt;
//import com.example.demo.security.jwt.AuthTokenFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class ApplicationSecurityConfiguration{
//
//    private final UserDetailsService userDetailsService;
//
//    private final AuthEntryPointJwt unauthorized;
//
//
//    private final PasswordEncoder passwordEncoder;
//
//    public ApplicationSecurityConfiguration(UserDetailsService userDetailsService,
//                                            AuthEntryPointJwt unauthorized, PasswordEncoder passwordEncoder) {
//        this.userDetailsService = userDetailsService;
//        this.unauthorized = unauthorized;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Bean
//    public AuthTokenFilter authenticationJwtFilter(){
//        return new AuthTokenFilter();
//    }
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
////                .authorizeRequests()
////                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
////                .requestMatchers("/","/user/register","/user/login").permitAll()
////                .requestMatchers("/**").permitAll()
////                //add this when connection with front end is ready
////                //.antMatchers("/**").authenticated()
////                .and()
////                .csrf().disable()
////                .cors().configurationSource(request -> {
////                    CorsConfiguration corsConfig = new CorsConfiguration();
////                    corsConfig.addAllowedOrigin("http://localhost:3000");
////                    corsConfig.addAllowedMethod("*");
////                    corsConfig.addAllowedHeader("*");
////                    return corsConfig;
////                }).and()
////                .formLogin()
////                .loginProcessingUrl("/user/login")
////            //    .usernameParameter("username")
////            //    .passwordParameter("password")
////                .defaultSuccessUrl("/user/login-success")
////                .failureForwardUrl("/user/login-error")
////                .and()
////                .logout()
////                .logoutUrl("/user/logout")
////                .logoutSuccessUrl("/")
////                .invalidateHttpSession(true)
////                .deleteCookies("JSESSIONID");
////        return http.build();
////    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(unauthorized)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                .requestMatchers("/","/user/register","/user/login").permitAll()
//                .requestMatchers("/**").permitAll();
//        http.addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//
//
//    @Autowired
//    public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }
//
////    @Bean
////    public AuthenticationManager authenticationManagerBean() throws Exception{
////        return authenticationManagerBean();
////    }
//
//}
