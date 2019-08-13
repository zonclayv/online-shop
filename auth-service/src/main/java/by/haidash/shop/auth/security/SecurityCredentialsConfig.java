package by.haidash.shop.auth.security;

import by.haidash.shop.security.model.JwtConfiguration;
import by.haidash.shop.security.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@EnableWebSecurity
public class SecurityCredentialsConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtConfiguration jwtConfiguration;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public SecurityCredentialsConfig(@Qualifier("basicUserDetailsService") UserDetailsService userDetailsService,
                                     JwtConfiguration jwtConfiguration,
                                     JwtTokenService jwtTokenService) {

        this.userDetailsService = userDetailsService;
        this.jwtConfiguration = jwtConfiguration;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(SC_UNAUTHORIZED))
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(),
                        jwtConfiguration,
                        jwtTokenService))
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, jwtConfiguration.getLoginUri()).permitAll()
                .antMatchers(HttpMethod.POST, jwtConfiguration.getSigninUri()).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}