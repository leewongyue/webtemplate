package com.template.webtemplate.config;

import com.template.webtemplate.Handler.LoginFailfulHandler;
import com.template.webtemplate.entity.Member;
import com.template.webtemplate.service.MemberDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig {

//    @Autowired
//    private MemberDetailService memberService;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception
    {
//        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        builder.userDetailsService(memberDetailService());
//        authenticationManager = builder.build();


        return http.authorizeRequests().mvcMatchers("/css/**","/scripts/**","/fonts/**","/imgs/**").permitAll()
                .antMatchers("/member/*").permitAll()
                .antMatchers("/admin").hasRole("ADMIN").and().build();
//        http.formLogin().loginPage("/login").permitAll().and().oauth2Login();
//        http.csrf().disable();
//        http.logout();
//        http.oauth2Login();

    }
    @Bean
    public MemberDetailService memberDetailService()
    {
        return new MemberDetailService();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider()
//    {
//        DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider();
//        authprovider.setUserDetailsService(memberDetailService());
//        authprovider.setPasswordEncoder(passwordEncoder());
//        return authprovider;
//    }
//    @Bean
//    public UserDetailsManager users(DataSource dataSource) throws Exception
//    {
//        UserDetails details = User.withUsername("user")
//                .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
//                .roles("USER")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(details);
//        return users;
//
//    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
//    @Autowired
    public void authManager(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(memberDetailService()).passwordEncoder(passwordEncoder());
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer()
//    {
//        return (web) -> web.ignoring().antMatchers("/static/css/","/static/js/","/static/imgs/");
//    }
}
