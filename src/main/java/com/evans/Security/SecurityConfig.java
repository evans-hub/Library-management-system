package com.evans.Security;

import com.evans.filter.CustomAuthenticationFilter;
import com.evans.filter.CustomAuthorizationFiller;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor @Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
      //  http.authorizeHttpRequests().anyRequest().permitAll();



        http.authorizeHttpRequests().antMatchers("/login/**").permitAll();
        http.authorizeHttpRequests().antMatchers(POST,"/api/saveuser/**").hasAnyAuthority("admin");
        http.authorizeHttpRequests().antMatchers(GET,"/api/allusers/**").hasAnyAuthority("admin");
        http.authorizeHttpRequests().antMatchers(GET,"/api/allbooks/**").hasAnyAuthority("staff");
        http.authorizeHttpRequests().antMatchers(POST,"/api/savebook/**").hasAnyAuthority("admin");
        http.authorizeHttpRequests().antMatchers(POST,"/api/addroletouser/**").hasAnyAuthority("admin");
        http.authorizeHttpRequests().antMatchers(POST,"/api/addbooktouser/**").hasAnyAuthority("admin");


        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFiller(), UsernamePasswordAuthenticationFilter.class);



    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
