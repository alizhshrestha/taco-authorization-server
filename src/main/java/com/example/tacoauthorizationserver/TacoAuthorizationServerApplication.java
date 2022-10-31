package com.example.tacoauthorizationserver;

import com.example.tacoauthorizationserver.entity.User;
import com.example.tacoauthorizationserver.repository.UserRepository;
import com.example.tacoauthorizationserver.service.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TacoAuthorizationServerApplication {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    public static void main(String[] args) {
        SpringApplication.run(TacoAuthorizationServerApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(
            UserRepository repo,
            PasswordEncoder encoder
    ) {
        return args -> {
            repo.save(User.builder()
                    .username("habuma")
                    .password(encoder.encode("password"))
                    .role("ROLE_ADMIN")
                    .build());

            repo.save(User.builder()
                    .username("tacochef")
                    .password(encoder.encode("password"))
                    .role("ROLE_ADMIN")
                    .build());
        };
    }


    @Autowired
    public void bindAuthenticationProvider(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder
                .authenticationProvider(customAuthenticationProvider);
    }

}
