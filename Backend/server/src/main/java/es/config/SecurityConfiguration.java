package es.config;

import javax.inject.Inject;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import es.security.Http401UnauthorizedEntryPoint;
import es.security.jwt.JWTConfigurer;
import es.security.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Inject private UserDetailsService userDetailsService;

  @Inject private Http401UnauthorizedEntryPoint authenticationEntryPoint;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Inject private TokenProvider tokenProvider;

  @Inject
  public void configureGlobal(AuthenticationManagerBuilder auth) {
    try {
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    } catch (Exception e) {
      throw new BeanInitializationException("Security configuration failed", e);
    }
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .headers()
        .frameOptions()
        .disable()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/api/authenticate")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/api/users/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/api/account/reset_password/init")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/api/account/reset_password/finish")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/api/register")
        .permitAll()
        .antMatchers("/**")
        .permitAll()
        .and()
        .apply(securityConfigurerAdapter());
  }

  @Bean
  public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
    return new SecurityEvaluationContextExtension();
  }

  @Inject
  public void configureAuth(AuthenticationManagerBuilder auth) {
    try {
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    } catch (Exception e) {
      throw new BeanInitializationException("SecurityConfiguration.configureAuth failed", e);
    }
  }

  private JWTConfigurer securityConfigurerAdapter() {
    return new JWTConfigurer(tokenProvider);
  }
}
