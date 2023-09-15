package com.example.demo.config;


import com.example.demo.config.oauth.PrincipalOauth2UserService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
@RequiredArgsConstructor

public class AuthenticationConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final UserService userService;

    @Value("${jwt.token.secret")
    private String secretKey;


    @Bean
    public SecurityFilterChain configure  (HttpSecurity http) throws Exception {


        return http
                    .httpBasic().disable()
                    .csrf().disable()

                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and()

                    .authorizeRequests()
                    .antMatchers("/login", "/join").permitAll()
                    .antMatchers("/").permitAll()
                    .antMatchers("/board/save").permitAll()


                    .and()
                    .formLogin(form -> form
                            .loginPage("/login")
                            .permitAll())

                    .logout()
                    .logoutSuccessUrl("/board/paging")
                    .invalidateHttpSession(true)
                    .and()


    //                .and()
    //                .oauth2Login()
    //                .loginPage("/login")
    //                .userInfoEndpoint()
    //                .userService(principalOauth2UserService)


    //                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
