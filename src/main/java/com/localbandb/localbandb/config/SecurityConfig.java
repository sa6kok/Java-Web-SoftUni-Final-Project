package com.localbandb.localbandb.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
        .cors().disable()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/js/**", "/css/**", "/vendor/**").permitAll()
        .antMatchers("/", "/user/register/**", "/user/login/**",
            "/host/register", "/host/login", "/user/api/register/**", "/user/api/registerWithEmail/**").anonymous()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/user/login")
        .usernameParameter("username")
        .passwordParameter("password")
        .failureUrl("/user/login?error=true")
        .defaultSuccessUrl("/property/show/all")
        .and()
        .logout()
        .logoutUrl("/user/logout")
        .deleteCookies("JSESSIONID")
        .logoutSuccessUrl("/")
        .and()
        .exceptionHandling()
        .accessDeniedPage("/");
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
