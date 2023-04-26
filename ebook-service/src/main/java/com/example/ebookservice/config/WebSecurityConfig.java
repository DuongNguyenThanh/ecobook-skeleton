package com.example.ebookservice.config;

import com.example.api.filter.AuthTokenFilter;
import com.example.api.jwt.AuthEntryPointJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthTokenFilter authTokenFilter;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(GET, "/api/ebook/**").permitAll()
                .antMatchers(POST, "/api/ebook/**").hasAnyAuthority("ADMIN")
                .antMatchers(PUT, "/api/ebook/**").hasAnyAuthority("ADMIN")
                .antMatchers(DELETE, "/api/ebook/**").hasAnyAuthority("ADMIN")

                .antMatchers(GET, "/api/category/**").permitAll()
                .antMatchers(POST, "/api/category/**").hasAnyAuthority("ADMIN")
                .antMatchers(PUT, "/api/category/**").hasAnyAuthority("ADMIN")
                .antMatchers(DELETE, "/api/category/**").hasAnyAuthority("ADMIN")

                .antMatchers(GET, "/api/image/**").permitAll()
                .antMatchers(POST, "/api/image/**").hasAnyAuthority("ADMIN")
                .antMatchers(PUT, "/api/image/**").hasAnyAuthority("ADMIN")
                .antMatchers(DELETE, "/api/image/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated().and()
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}