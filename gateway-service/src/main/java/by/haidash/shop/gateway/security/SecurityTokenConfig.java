package by.haidash.shop.gateway.security;

import by.haidash.shop.jwt.configuration.JwtConfiguration;
import by.haidash.shop.jwt.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfiguration jwtConfiguration;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityTokenConfig(JwtConfiguration jwtConfiguration, JwtTokenProvider jwtTokenProvider) {
        this.jwtConfiguration = jwtConfiguration;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfiguration, jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, jwtConfiguration.getLoginUri()).permitAll()
                .antMatchers(HttpMethod.POST, jwtConfiguration.getSigninUri()).permitAll()
                .antMatchers(HttpMethod.GET, "/**/v2/api-docs",
                        "/**/configuration/ui",
                        "/**/swagger-resources",
                        "/**/configuration/jwt",
                        "/**/swagger-ui.html",
                        "/**/webjars/**").permitAll()
                .anyRequest().authenticated();
    }
}
