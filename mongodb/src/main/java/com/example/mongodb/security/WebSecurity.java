package com.example.mongodb.security;
import com.example.mongodb.entity.Role;
import static com.example.mongodb.security.SecurityConstants.LOGIN_URL;
import static com.example.mongodb.security.SecurityConstants.SIGN_UP_URL;
import com.example.mongodb.service.impl.JwtUserDetailsService;
import com.example.mongodb.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final JwtUserDetailsService userDetailsService;
    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
                .antMatchers(HttpMethod.GET, "/book/list").authenticated()
                .antMatchers(HttpMethod.GET, "/book/**").authenticated()
                .antMatchers(HttpMethod.POST, "/book/save").hasAuthority("admin")
                .antMatchers(HttpMethod.DELETE, "/book/delete/**").hasAuthority("admin")
                .antMatchers(HttpMethod.PUT,"/book/rate").hasAuthority("user")


                .and()
                .addFilter(new JWTAuthenticationFilter(userService,userDetailsService,authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(),userDetailsService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}