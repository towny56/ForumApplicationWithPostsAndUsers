package com.homework.first.config;

import com.homework.first.security.RestAuthenticationEntryPoint;
import com.homework.first.security.RestSavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/info").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger*/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/users").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/**").authenticated()
                .antMatchers(HttpMethod.PUT).authenticated()
                .antMatchers(HttpMethod.DELETE).authenticated()
                .and()
                .formLogin()
                .permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout();
//                .and()
//                    .rememberMe();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("usernameAdmin").password("passwordAdmin").roles("ADMIN").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("usernameBlogger").password("passwordBlogger").roles("BLOGGER").build());

        return manager;
    }

}
