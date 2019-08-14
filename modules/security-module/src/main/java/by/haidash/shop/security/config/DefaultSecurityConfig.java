package by.haidash.shop.security.config;

import by.haidash.shop.security.filter.JwtTokenAuthenticationFilter;
import by.haidash.shop.security.properties.JwtProperties;
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

@EnableWebSecurity
public class DefaultSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProperties jwtProperties;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public DefaultSecurityConfig(JwtProperties jwtProperties, JwtTokenService jwtTokenService) {
        this.jwtProperties = jwtProperties;
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
                .antMatchers(HttpMethod.POST, jwtProperties.getLoginUri()).permitAll()
                .antMatchers(HttpMethod.POST, jwtProperties.getRegistrationUri()).permitAll()
                .antMatchers(HttpMethod.GET, "/v2/api-docs",
                        "/model/ui",
                        "/swagger-resources",
                        "/model/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .anyRequest().authenticated();
    }
}
