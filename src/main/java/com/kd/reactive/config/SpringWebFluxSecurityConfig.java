package com.kd.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SpringWebFluxSecurityConfig {

    // default username -> user and pwd is generated in logs

    @Bean
    public MapReactiveUserDetailsService userDetailsService(){
        UserDetails build = User.withUsername("kunal")
                .password(passwordEncoder().encode("lawand"))
                .roles("admin")
                .build();

        return  new MapReactiveUserDetailsService(build);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityWebFilterChain  webFilterChain(ServerHttpSecurity httpSecurity){
        httpSecurity
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/student/**")
                .hasRole("admin")
                .pathMatchers("/**")
                .hasRole("admin")
                .and().httpBasic();
         return  httpSecurity.build();

    }

}
