package com.pavel.newsweb.Configuration;

import com.pavel.newsweb.Filter.JwtFilter;
import com.pavel.newsweb.Point.CustomAuthenticationEntryPoint;
import com.pavel.newsweb.Service.impl.UsersDetailService;
import com.pavel.newsweb.handler.CustomAccessDeniedHandler;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UsersDetailService usersDetailService;

    private final JwtFilter jwtFilter;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfiguration(UsersDetailService usersDetailService, JwtFilter jwtFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.usersDetailService = usersDetailService;
        this.jwtFilter = jwtFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/auth")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/{user_id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/news").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/news/{news_id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/category").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/category/{category_id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/activate/**").permitAll()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic().authenticationEntryPoint(customAuthenticationEntryPoint);
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersDetailService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
