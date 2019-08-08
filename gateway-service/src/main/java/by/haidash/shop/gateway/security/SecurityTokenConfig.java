package by.haidash.shop.gateway.security;

import javax.servlet.http.HttpServletResponse;

import by.haidash.shop.security.configuration.JwtConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfiguration jwtConfiguration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfiguration), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, jwtConfiguration.getLoginUri()).permitAll()
                .antMatchers(HttpMethod.POST, jwtConfiguration.getSigninUri()).permitAll()
                .antMatchers(HttpMethod.GET, "/**/v2/api-docs",
                        "/**/configuration/ui",
                        "/**/swagger-resources",
                        "/**/configuration/security",
                        "/**/swagger-ui.html",
                        "/**/webjars/**").permitAll()
                .anyRequest().authenticated();
    }
}
