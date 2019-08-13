package by.haidash.shop.security.config;

import by.haidash.shop.security.filter.JwtTokenAuthenticationFilter;
import by.haidash.shop.security.model.JwtConfiguration;
import by.haidash.shop.security.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Primary
@EnableWebSecurity
public class DefaultSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfiguration jwtConfiguration;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public DefaultSecurityConfig(JwtConfiguration jwtConfiguration, JwtTokenService jwtTokenService) {
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
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtTokenService),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, jwtConfiguration.getLoginUri()).permitAll()
                .antMatchers(HttpMethod.POST, jwtConfiguration.getRegistrationUri()).permitAll()
                .antMatchers(HttpMethod.GET, "/v2/api-docs",
                        "/model/ui",
                        "/swagger-resources",
                        "/model/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .anyRequest().authenticated();
    }
}
