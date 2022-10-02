package ar.edu.unq.desapp.grupof.backendcriptop2papi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public JWTAuthorizationFilter jwtTokenFilter(){
        return new JWTAuthorizationFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(documentationAndUtilitiesRoutes()).permitAll().and()
                .authorizeRequests().antMatchers(nonProtectedRoutes()).permitAll()
                .antMatchers("/**").authenticated();

        httpSecurity.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    private String[] nonProtectedRoutes() {
        return new String[]{
                "/api/ping",
                "/api/users/register",
                "/api/users/login"
        };
    }

    private String[] documentationAndUtilitiesRoutes() {
            return new String[]{"/v2/api-docs",
                    "/v3/api-docs",
                    "/swagger-resources/**",
                    "/swagger-ui/**",
                    "/",
                    "/h2-console/**"
            };
    }

}